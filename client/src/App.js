// App.js
import React, { useState } from 'react';
import Header from './components/Header';
import ArticleList from './components/ArticleList';
import Login from './components/Login'; // Import the Login component
import 'bootstrap/dist/css/bootstrap.min.css';

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
        <div className="container">
            <Header isLoggedIn={isLoggedIn} />
            <main>
                <ArticleList />
                {!isLoggedIn && <Login onLogin={handleLogin} />} {/* Show Login only if not logged in */}
            </main>
            <div className="text-right mt-3">
                {isLoggedIn ? (
                    <button onClick={handleLogout} className="btn btn-danger">Logout</button>
                ) : null}
            </div>
        </div>
    );
};

export default App;
