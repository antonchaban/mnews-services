// App.js
import React, { useState } from 'react';
import Header from './components/Header';
import ArticleList from './components/ArticleList';
import Login from './components/Login'; // Import the Login component
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Update the import

const App = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState('');

    const handleLogin = (username) => {
        setIsLoggedIn(true);
        setUsername(username);
    };

    const handleLogout = () => {
        setIsLoggedIn(false);
        setUsername('');
    };

    return (
        <Router>
            <div className="container">
                <Header isLoggedIn={isLoggedIn} />
                <main>
                    <Routes> {/* Update to Routes */}
                        <Route path="/" element={<ArticleList />} /> {/* Update Route */}
                        <Route path="/auth" element={<Login onLogin={handleLogin} />} /> {/* Update Route */}
                        {/*todo login page not opening, need to solve*/}
                        {/*<Route path="/auth" element={<Login />} />*/}
                    </Routes> {/* Update to Routes */}
                </main>
                <div className="text-right mt-3">
                    {isLoggedIn ? (
                        <button onClick={handleLogout} className="btn btn-danger">Logout</button>
                    ) : null}
                </div>
            </div>
        </Router>
    );
};

export default App;
