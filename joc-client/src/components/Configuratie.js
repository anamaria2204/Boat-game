import React, { useState } from 'react';
import axios from 'axios';

function Configuratie() {
  const [formData, setFormData] = useState({
    pozitie11: '',
    pozitie12: '',
    pozitie21: '',
    pozitie22: '',
    pozitie31: '',
    pozitie32: ''
  });

  const [status, setStatus] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    const numericValue = parseInt(value, 10);

    if (value === '') {
      setFormData({ ...formData, [name]: '' });
    } else if (!isNaN(numericValue)) {
      setFormData({ ...formData, [name]: numericValue });
    }
  };

  const isValid = () => {
    return Object.values(formData).every((val) =>
      typeof val === 'number' && val >= 1 && val <= 5
    );
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setStatus(null);

    if (!isValid()) {
      setStatus({
        type: 'error',
        message: 'Toate pozițiile trebuie să fie numere între 1 și 5.',
      });
      return;
    }

    try {
      const response = await axios.post('http://localhost:8080/api/configuratie', formData);
      setStatus({
        type: 'success',
        message: 'Configurația a fost salvată cu succes.',
      });
    } catch (error) {
      if (error.response && error.response.status === 400) {
        setStatus({ type: 'error', message: error.response.data });
      } else {
        setStatus({ type: 'error', message: 'Eroare la salvare.' });
      }
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Adaugă Configurație Nouă</h2>
      <form onSubmit={handleSubmit}>
        <div style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(2, 1fr)',
          gap: '10px',
          maxWidth: '300px'
        }}>
          {[
            'pozitie11', 'pozitie12',
            'pozitie21', 'pozitie22',
            'pozitie31', 'pozitie32'
          ].map((field) => (
            <input
              key={field}
              name={field}
              type="number"
              placeholder={field}
              value={formData[field]}
              min={1}
              max={5}
              required
              onChange={handleChange}
            />
          ))}
        </div>
        <button type="submit" style={{ marginTop: '15px' }}>
          Adaugă Configurație
        </button>
      </form>

      {status && (
        <p style={{ marginTop: '10px', color: status.type === 'success' ? 'green' : 'red' }}>
          {status.message}
        </p>
      )}
    </div>
  );
}

export default Configuratie;
