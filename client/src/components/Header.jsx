// Header.jsx
import React from 'react';
import { useLocation, Link } from 'react-router-dom';
import AuthComponent from "./AuthComponent";
import LanguageSwitcher from "../LanguageSwitcher"; // Import useLocation hook

const Header = ({ isLoggedIn }) => {
    const location = useLocation();

    // Check if the current location is the login page
    const isLoginPage = location.pathname === '/auth';

    return (
        <header className="d-flex justify-content-between align-items-center">
            <div>
                <LanguageSwitcher /> {/* Language switcher component */}
            </div>
            <div className="d-flex align-items-center">
                {/* Render AuthComponent only if not on the login page */}
                {!isLoginPage && <AuthComponent isLoggedIn={isLoggedIn} />}
                {/* Render Create Article button only if the user is logged in */}
                {isLoggedIn && (
                    <Link to="/articles/create" className="btn btn-primary ml-3">
                        Create Article
                    </Link>
                )}
            </div>
        </header>
    );
};

export default Header;
