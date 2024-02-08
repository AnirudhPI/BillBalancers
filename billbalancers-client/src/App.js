import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from './components/NavBar'
import LoginRegister from './components/LoginRegister';
import Profile from './components/Profile';

function App() {
    return (
        <Router>
            <NavBar />
            <Routes>
                <Route path="/loginregister" element={<LoginRegister/>} />
                <Route path="/profile" element={<Profile/>} />
            </Routes>
        </Router>

    );
}

export default App;

