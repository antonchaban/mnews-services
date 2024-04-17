import React from 'react';
import { Link } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { jwtDecode } from "jwt-decode";

const AuthComponent = () => {
    const [cookies] = useCookies(['ACCESS_TOKEN']);
    const token = cookies['ACCESS_TOKEN'];
    let isLoggedIn = false;
    let username = '';

    if (token) {
        const decodedToken = jwtDecode(token); // Use jwtDecode instead of jwt_decode
        username = decodedToken.sub;
        isLoggedIn = true;
    }

    if (isLoggedIn) {
        return (
            <div>
                <p>Welcome, {username}!</p>
                <p><Link to="/profile">Profile</Link></p>
            </div>
        );
    } else {
        return (
            <div>
                <p>Please log in to view your profile.</p>
                <p><Link to="/auth">Login</Link></p>
            </div>
        );
    }
};

export default AuthComponent;
