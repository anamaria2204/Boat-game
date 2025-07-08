import React, { useEffect, useState, useRef } from "react";
import axios from "axios";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

function Dashboard() {
  const [jucator, setJucator] = useState(null);
  const [joc, setJoc] = useState(null);
  const [configuratie, setConfiguratie] = useState(null);
  const [tabla, setTabla] = useState([]);
  const [numarMutari, setNumarMutari] = useState(0);
  const [jocFinalizat, setJocFinalizat] = useState(false);
  const [pozitiiGasite, setPozitiiGasite] = useState([]);
  const initDone = useRef(false);
  const [clasament, setClasament] = useState([]);


  const getNumeFromURL = () => {
    const params = new URLSearchParams(window.location.search);
    return params.get("user");
  };

  const incarcaMutari = async (jocId) => {
    try {
      const resp = await axios.get(`http://localhost:8080/api/mutare?jocId=${jocId}`);
      const mutari = resp.data;

      const tablaNoua = Array(5).fill(null).map(() => Array(5).fill(""));
      const pozitiiGasiteTemp = [];

      mutari.forEach((mutare) => {
        const r = mutare.linie - 1;
        const c = mutare.coloana - 1;

        console.log("Mutare incarcată:", mutare);

        if (mutare.ghicit) {
          tablaNoua[r][c] = "B";
          pozitiiGasiteTemp.push(`${mutare.linie},${mutare.coloana}`);
        } else {
          tablaNoua[r][c] = "1"; // sau altă distanță
        }
      });

      setTabla(tablaNoua);
      setPozitiiGasite(pozitiiGasiteTemp);
      setNumarMutari(mutari.length);
      if (mutari.length >= 3) setJocFinalizat(true);
    } catch (err) {
      console.error("Eroare la încărcarea mutărilor:", err);
    }
  };

  const handleClick = async (rowIndex, colIndex) => {
    if (!joc || !jucator || !configuratie) return;
    if (tabla[rowIndex][colIndex] !== "") return;
    if (jocFinalizat) return;

    const pozitii = [
      [parseInt(configuratie.pozitie11), parseInt(configuratie.pozitie12)],
      [parseInt(configuratie.pozitie21), parseInt(configuratie.pozitie22)],
      [parseInt(configuratie.pozitie31), parseInt(configuratie.pozitie32)],
    ];

    const linie = rowIndex + 1;
    const coloana = colIndex + 1;

    console.log("Click pe:", linie, coloana);
    console.log("Poziții barcă:", pozitii);

    const isBarca = pozitii.some(([r, c]) => r === linie && c === coloana);
    console.log("Este barcă:", isBarca);

    const value = isBarca ? "B" : "1"; // simplificare pentru test
    const scorDelta = isBarca ? 5 : -3;

    const newTabla = [...tabla];
    newTabla[rowIndex][colIndex] = value;
    setTabla(newTabla);

    if (isBarca) {
      setPozitiiGasite((prev) => [...prev, `${linie},${coloana}`]);
    }

    const mutarePayload = {
      jucatorId: jucator.id,
      jocId: joc.id,
      linie,
      coloana,
      ghicit: isBarca,
    };

    console.log("Trimit mutare:", mutarePayload);

    try {
      await axios.post("http://localhost:8080/api/mutare", mutarePayload);
      await axios.put(`http://localhost:8080/api/joc/${joc.id}/scor`, {
        scor: scorDelta,
      });
      if (isBarca) {
        await axios.put(`http://localhost:8080/api/joc/${joc.id}/ghicit`, {
          ghicit: true,
        });
      }

      setJoc((prev) => ({
        ...prev,
        scor: prev.scor + scorDelta,
      }));

      const mutari = numarMutari + 1;
      setNumarMutari(mutari);
      if (mutari >= 3) setJocFinalizat(true);
    } catch (err) {
      console.error("Eroare la mutare:", err);
    }
  };

  useEffect(() => {
    const initJoc = async () => {
      if (initDone.current) return;
      initDone.current = true;

      const nume = getNumeFromURL();
      if (!nume) return;

      try {
        const jucatorResp = await axios.get(`http://localhost:8080/api/jucator/nume/${nume}`);
        setJucator(jucatorResp.data);

        const jocResp = await axios.post(
          `http://localhost:8080/api/joc/start?numeJucator=${encodeURIComponent(nume)}`
        );
        setJoc(jocResp.data);
        setConfiguratie(jocResp.data.configuratie);

        console.log("Configurație primită:", jocResp.data.configuratie);

        await incarcaMutari(jocResp.data.id);
      } catch (err) {
        console.error("Eroare inițializare joc:", err);
      }
    };

    initJoc();
  }, []);

  useEffect(() => {
  const socket = new SockJS("http://localhost:8080/ws");
  const stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000,
    onConnect: () => {
      stompClient.subscribe("/topic/clasament", (message) => {
        const updatedClasament = JSON.parse(message.body);
        setClasament(updatedClasament);
      });
    },
  });

  stompClient.activate();

  return () => {
    stompClient.deactivate();
  };
}, []);


  return (
    <div style={{ padding: "20px" }}>
      {jucator && <p><strong>Jucator:</strong> {jucator.nume}</p>}
      {jocFinalizat && (
      <div>
        <p style={{ color: "green", fontWeight: "bold" }}>
          Joc încheiat! Ai făcut cele 3 mutări.
        </p>

        {configuratie && (
          <p>
            Pozițiile ocupate de barcă sunt:{" "}
            <strong>
              ({configuratie.pozitie11},{configuratie.pozitie12}),{" "}
              ({configuratie.pozitie21},{configuratie.pozitie22}),{" "}
              ({configuratie.pozitie31},{configuratie.pozitie32})
            </strong>
          </p>
        )}
      </div>
    )}

      <div style={{
        display: "grid",
        gridTemplateColumns: "repeat(5, 60px)",
        gap: "5px",
        marginTop: "20px"
      }}>
        {tabla.map((row, rowIndex) =>
          row.map((cell, colIndex) => {
            const linie = rowIndex + 1;
            const coloana = colIndex + 1;
            const isGasita = pozitiiGasite.includes(`${linie},${coloana}`);

            return (
              <div
                key={`${rowIndex}-${colIndex}`}
                onClick={() => handleClick(rowIndex, colIndex)}
                style={{
                  width: "60px",
                  height: "60px",
                  border: "1px solid black",
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  background: "#f0f0f0",
                  cursor: jocFinalizat ? "not-allowed" : "pointer",
                  fontWeight: isGasita ? "bold" : "normal",
                }}
              >
                {isGasita ? "B" : cell}
              </div>
            );
          })
        )}
      </div>
{clasament.length > 0 && (
      <div style={{ marginTop: "40px" }}>
        <h3>Clasament</h3>
        <table border="1" cellPadding="8">
          <thead>
            <tr>
              <th>Jucător</th>
              <th>Start</th>
              <th>Scor</th>
              <th>Ghicite</th>
            </tr>
          </thead>
          <tbody>
            {clasament.map((entry, index) => (
              <tr key={index}>
                <td>{entry.alias}</td>
                <td>{new Date(entry.start_time).toLocaleString("ro-RO")}</td>
                <td>{entry.scor}</td>
                <td>{entry.ghicite}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    )}
    </div>
  );
}

export default Dashboard;
