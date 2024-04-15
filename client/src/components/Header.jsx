// Header.js
import React from 'react';
import {useLocation} from 'react-router-dom';
import AuthComponent from "./AuthComponent";
import LanguageSwitcher from "../LanguageSwitcher"; // Import useLocation hook

const Header = ({isLoggedIn}) => {
    const location = useLocation();

    // Check if the current location is the login page
    const isLoginPage = location.pathname === '/auth';

    return (
        <header className="d-flex justify-content-between align-items-center">
            <div>
                <LanguageSwitcher/> {/* Language switcher component */}
            </div>
            <div>
                {/* Render AuthComponent only if not on the login page */}
                {!isLoginPage && <AuthComponent isLoggedIn={isLoggedIn}/>}
            </div>
        </header>
    );
};

export default Header;
