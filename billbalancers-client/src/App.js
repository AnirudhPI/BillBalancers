import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from './components/NavBar'
import LoginRegister from './components/LoginRegister';

function App() {
    return (
        <Router>
            <NavBar />
            <Routes>
                <Route path="/loginregister" element={<LoginRegister/>} />
            </Routes>
        </Router>

    );
}

export default App;

