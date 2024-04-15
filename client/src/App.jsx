// App.jsx
import React, {useEffect, useState} from 'react';
import Header from './components/Header';
import ArticleList from './components/ArticleList';
import Login from './components/Login';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import {useCookies} from "react-cookie"; // Updated import
import Cookies from './enums/cookies';


const App = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState('');
    const [cookies, setCookie, removeCookie] = useCookies();

    useEffect(() => {
        const accessToken = cookies[Cookies.ACCESS_TOKEN];
        console.log(accessToken);
        if (accessToken) {
            setIsLoggedIn(true);
        }
    }, []);


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
                <Header isLoggedIn={isLoggedIn} username={username} />
                <main>
                    <Routes>
                        {/* Redirect from "/" to "/articles" */}
                        <Route path="/" element={<Navigate to="/articles" />} />

                        {/* Route to display the ArticleList component */}
                        <Route path="/articles" element={<ArticleList />} />

                        {/* Route to display the Login component */}
                        <Route path="/auth" element={<Login onLogin={handleLogin} />} />
                    </Routes>
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
