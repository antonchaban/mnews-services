import React from 'react';
import { Link } from 'react-router-dom';

const AuthComponent = ({ isLoggedIn }) => {
    if (isLoggedIn) {
        return (
            <div>
                <p>Welcome, User!</p>
                {/* Add link to user profile */}
                <p><Link to="/profile">Profile</Link></p>
            </div>
        );
    } else {
        return (
            <div>
                <p>Please log in to view your profile.</p>
                {/* Add link to login page */}
                <p><Link to="/auth">Login</Link></p>
            </div>
        );
    }
};

export default AuthComponent;
