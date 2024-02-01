// import logo from './logo.svg';
// import './App.css';
// import LoginForm from './components/LoginForm';
// import NavBar from './components/NavBar';

// function App() {

//   return (
//     <div className="App">
//       {/* <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//          {name}
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header> */}
//       {/* <SignupForm/> */}
//       {/* <LoginForm/> */}
//       <NavBar/>

//     </div>
//   );
// }

// export default App;
// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavBar from './components/NavBar'
import LoginForm from './components/LoginForm';
import LoginRegister from './components/LoginRegister';

function App() {
    return (
        <Router>
            <NavBar />
            <Routes>
                <Route path="/loginregister" element={<LoginRegister/>} />
                {/* <Route path="/signup" element={<SignupForm/>} /> */}
            </Routes>
        </Router>

    );
}

export default App;

