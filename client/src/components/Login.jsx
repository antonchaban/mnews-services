import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import axios from 'axios'; // Assuming axios is imported from 'axios' package
import {useCookies} from 'react-cookie'; // Assuming useCookies is imported from 'react-cookie' package
import {Avatar, Box, Button, Container, Grid, Link, TextField, Typography} from '@mui/material'; // Assuming MUI components are used
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';

const Login = () => {
    const [cookies, setCookie, removeCookie] = useCookies();
    const [loggedIn, setLoggedIn] = useState(false);
    const navigate = useNavigate();

    // useEffect(() => {
    //     const accessToken = cookies[Cookies.ACCESS_TOKEN]
    //     if (accessToken) {
    //         setLoggedIn(true);
    //     }
    // }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const formData = new FormData(event.currentTarget);
        const username = formData.get('username');
        const password = formData.get('password');

        try {
            const user = {
                username,
                password
            };

            const res = await axios.post('http://localhost/login', user); // Change the endpoint accordingly

            if (res.status === 200) {
                const login = res.data;

                setCookie('USER_ID', login.id, {
                    maxAge: 604800,
                    sameSite: 'strict'
                });
                setCookie('ACCESS_TOKEN', login.accessToken, {
                    maxAge: 604800,
                    sameSite: 'strict'
                });
                setCookie('REFRESH_TOKEN', login.refreshToken, {
                    maxAge: 604800,
                    sameSite: 'strict'
                });

                setLoggedIn(true);
            }
        } catch (error) {
            console.error('Error processing login request', error);
        }
    };

    useEffect(() => {
        if (loggedIn) {
            navigate('/articles'); // Change the route accordingly
        }
    }, [loggedIn, navigate]);

    return (
        <Container component="main" maxWidth="xs">
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center'
                }}
            >
                <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                    <LockOutlinedIcon color="primary"/>
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign in
                </Typography>
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="username"
                        label="Username"
                        name="username"
                        autoComplete="username"
                        autoFocus
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                    />
                    <Button type="submit" fullWidth variant="contained" sx={{mt: 3, mb: 2}}>
                        Login
                    </Button>
                    <Grid container>
                        <Grid item xs>
                            <Link href="https://www.youtube.com/watch?v=dQw4w9WgXcQ" variant="body2">
                                Forgot password?
                            </Link>
                        </Grid>
                        <Grid item>
                            <Link href="/signup" variant="body2">
                                {"Don't have an account? Sign Up"}
                            </Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </Container>
    );
};

export default Login;
