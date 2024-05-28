import React, {useEffect, useState} from 'react';
import Header from './components/Header';
import ArticleList from './components/ArticleList';
import Login from './components/Login';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter as Router, Navigate, Route, Routes} from 'react-router-dom';
import {useCookies} from "react-cookie";
import Cookies from './enums/cookies';
import SignUp from './components/SignUp';
import Profile from "./components/Profile";
import ArticleEdit from "./components/ArticleEdit";
import ArticleCreate from "./components/ArticleCreate";
import AdminComponent from "./components/AdminComponent";
import ArticleView from "./components/ArticleView";

const App = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState('');
    const [cookies, setCookie, removeCookie] = useCookies();

    useEffect(() => {
        const accessToken = cookies[Cookies.ACCESS_TOKEN];
        if (accessToken) {
            setIsLoggedIn(true);
        }
    }, []);

    const handleLogin = (username) => {
        setIsLoggedIn(true);
        setUsername(username);
    };

    return (
        <Router>
            <div className="container-fluid" style={{paddingLeft: 0, paddingRight: 0}}>
                <Header isLoggedIn={isLoggedIn} username={username}/>
                <main>
                    <Routes>
                        <Route path="/" element={<Navigate to="/articles"/>}/>
                        <Route path="/articles" element={<ArticleList/>}/>
                        <Route path="/auth" element={<Login onLogin={handleLogin}/>}/>
                        <Route path="/signup" element={<SignUp/>}/>
                        <Route path="/articles/:id/edit" element={<ArticleEdit/>}/>
                        <Route path="/articles/create" element={<ArticleCreate/>}/>
                        <Route path="/admin" element={<AdminComponent/>}/>
                        <Route path="/articles/:id" element={<ArticleView/>}/>
                        <Route path="/profile/:id" element={<Profile/>}/>
                    </Routes>
                </main>
            </div>
        </Router>
    );
};

export default App;
