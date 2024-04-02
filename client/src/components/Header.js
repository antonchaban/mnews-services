// Header.js
import React from 'react';
import LanguageSwitcher from '../LanguageSwitcher';
import AuthComponent from './AuthComponent'; // Import the AuthComponent

const Header = ({ isLoggedIn }) => { // Receive isLoggedIn prop
    return (
        <header className="d-flex justify-content-between align-items-center">
            <div>
                <LanguageSwitcher /> {/* Language switcher component */}
            </div>
            <div>
                <AuthComponent isLoggedIn={isLoggedIn} /> {/* Auth component */}
            </div>
        </header>
    );
};

export default Header;
