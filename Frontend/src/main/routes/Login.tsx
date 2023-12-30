import * as React from 'react';
import {useEffect, useState} from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import {useCookies} from "react-cookie";
import {useNavigate} from "react-router-dom";
import Routes from "../routes";
import axios from "../api/client";
import LoginDTO from "../dto/LoginDTO";
import Cookies from "../cookies";
import Endpoints from "../api/endpoints";

export default function Login() {

    const [cookies, setCookie, removeCookie] = useCookies();
    const [loggedIn, setLoggedIn] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const data = new FormData(event.currentTarget);

        let username = data.get("username");
        let password = data.get("password");

        if (typeof username !== "string" || typeof password !== "string") {
            console.error("Username or password is not string");
            return;
        }

        axios.post<LoginDTO>(
            Endpoints.PATH_LOGIN, null,
            {
                params: {
                    "username": username,
                    "password": password
                }
            })
            .then(res => {
                if (res.status !== 200) {
                    return;
                }
                let login = res.data;

                setCookie(Cookies.USER_ID, login.id,
                    {
                        maxAge: 604800,
                        sameSite: "strict"
                    });
                setCookie(Cookies.ACCESS_TOKEN, login.accessToken,
                    {
                        maxAge: 604800,
                        sameSite: "strict"
                    });
                setCookie(Cookies.REFRESH_TOKEN, login.refreshToken,
                    {
                        maxAge: 604800,
                        sameSite: "strict"
                    });

                setLoggedIn(true);
            })
            .catch(error => {
                console.error("Error processing login request", error);
            })
    };

    useEffect(() => {
        if (loggedIn) {
            return navigate(Routes.SHORTENER);
        }
    }, [loggedIn])

    return (
        <Container component="main" maxWidth="xs">
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                    <LockOutlinedIcon
                        color="primary"
                    />
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
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{mt: 3, mb: 2}}
                    >
                        Login
                    </Button>
                    <Grid container>
                        <Grid item xs>
                            <Link href="https://www.youtube.com/watch?v=dQw4w9WgXcQ" variant="body2">
                                Forgot password? F
                            </Link>
                        </Grid>
                        <Grid item>
                            <Link href={Routes.SIGN_UP} variant="body2">
                                {"Don't have an account? Sign Up"}
                            </Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </Container>
    );
}