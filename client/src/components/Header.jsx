import React from 'react';
import {Link, useLocation} from 'react-router-dom';
import AuthComponent from "./AuthComponent";
import LanguageSwitcher from "../LanguageSwitcher";
import {Button} from "@mui/material";
import Cookies from "../enums/cookies";
import {useCookies} from "react-cookie";

const Header = ({isLoggedIn}) => {
    const location = useLocation();
    const [cookies, setCookie, removeCookie] = useCookies();
    const userId = cookies['USER_ID'];

    const handleLogout = () => {
        removeCookie(Cookies.USER_ID);
        removeCookie(Cookies.ACCESS_TOKEN);
        removeCookie(Cookies.REFRESH_TOKEN);
        window.location.href = '/articles';
    };

    // Check if the current location is the login page
    const isLoginPage = location.pathname === '/auth';

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
            <div className="container-fluid">
                <Link className="navbar-brand" to="/articles">
                    <img src="https://tse2.mm.bing.net/th/id/OIG3.0vMky7KhhRNgzUUg1OYO?pid=ImgGn" alt="Multi News Logo"
                         style={{width: '100px', marginRight: '20px'}}/>
                </Link>
                <LanguageSwitcher/>
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav ms-auto">
                        {!isLoginPage && (
                            <li className="nav-item">
                                <AuthComponent isLoggedIn={isLoggedIn}/>
                            </li>
                        )}
                        {isLoggedIn && (
                            <li className="nav-item">
                                <Link to="/articles/create" className="btn btn-outline-light">
                                    Create Article
                                </Link>
                            </li>
                        )}
                        {isLoggedIn && (
                            <li className="nav-item">
                                <Link to={`/profile/${userId}`} className="btn btn-outline-light">
                                    Profile
                                </Link>
                            </li>
                        )}
                        {isLoggedIn && (
                            <li className="nav-item">
                                <Button onClick={handleLogout} variant="contained" color="secondary">
                                    Logout
                                </Button>
                            </li>
                        )}
                        {!isLoggedIn && (
                            <li className="nav-item">
                                <Link to="/auth" className="btn btn-outline-light">
                                    Login
                                </Link>
                            </li>
                        )}
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default Header;
