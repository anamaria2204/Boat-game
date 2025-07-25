import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import Dashboard from './components/Dashboard';
import Configuratie from "./components/Configuratie";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<LoginPage/>}/>
                <Route path="/dashboard" element={<Dashboard/>}/>
                <Route path="/configuratie" element={<Configuratie/>}/>
            </Routes>
        </Router>
    );
}
export default App;
