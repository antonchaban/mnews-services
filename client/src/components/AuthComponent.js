import React from 'react';

const AuthComponent = ({ isLoggedIn }) => {
    if (isLoggedIn) {
        return (
            <div>
                <p>Welcome, User!</p>
                {/* Add link to user profile */}
                <a href="/profile">Profile</a>
            </div>
        );
    } else {
        return (
            <div>
                <p>Please log in to view your profile.</p>
                {/* Add link to login page */}
                <a href="/login">Login</a>
            </div>
        );
    }
};

export default AuthComponent;
