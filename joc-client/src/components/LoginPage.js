import React, { useState } from 'react';
import axios from 'axios';

function LoginPage() {
  const [nume, setNume] = useState("");

  const handleLogin = async (e) => {
  e.preventDefault();

  if (nume.trim() === "") {
    alert("Te rog introdu un nume.");
    return;
  }

  try {
    const response = await axios.get(`http://localhost:8080/api/jucator/nume/${nume}`);
    const jucator = response.data;

    window.open(`/dashboard?user=${encodeURIComponent(jucator.nume)}`, "_blank");
    window.open(`/configuratie`, "_blank");
  } catch (error) {
    alert("Jucatorul nu a fost gasit în sistem.");
  }
};


  return (
    <div style={{ padding: "20px" }}>
      <h2>Login Jucător</h2>
      <form onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="Introdu numele"
          value={nume}
          onChange={(e) => setNume(e.target.value)}
          required
        />
        <button type="submit" style={{ marginLeft: "10px" }}>Login</button>
      </form>
    </div>
  );
}

export default LoginPage;
