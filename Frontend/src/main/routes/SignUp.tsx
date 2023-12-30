import * as React from 'react';
import {Link, Grid, Box, Avatar, Button, TextField, Typography, Container} from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Routes from "../routes";
import axios from "../api/client"
import UserDTO from "../dto/UserDTO";
import Endpoints from "../api/endpoints";
import {useNavigate} from "react-router-dom";
import {useCookies} from "react-cookie";
import routes from "../routes";
import Cookies from "../cookies";

export default function SignUp() {

    const [cookies, setCookie, removeCookie] = useCookies();
    const navigate = useNavigate();

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        let data = new FormData(event.currentTarget);

        let username = data.get("username");
        let password = data.get("password");
        let confirmPassword = data.get("confirm_password");

        if (typeof username !== "string" ||
            typeof password !== "string" ||
            typeof confirmPassword !== "string") {
            //Maybe handle as error in future
            console.error("Form params are not strings");
            return;
        }

        if (password !== confirmPassword) {
            console.info("passwords do not match");
            return;
        }

        if (!username.match("[a-zA-z]")) {
            console.info("Username does not match");
            return;
        }

        let user: UserDTO = {
            username: username,
            password: password
        }

        axios.post<UserDTO>(Endpoints.PATH_USERS, null, {
            params: {
                username: user.username,
                password: user.password
            }
        })
            .then(res => {
                if (res.status !== 200) {
                    return;
                }
                handleUserCreated();
            })
            .catch(error => {
                console.error("Error creating user", error);
            })
    };

    const handleUserCreated = () => {
        removeCookie(Cookies.USER_ID);
        removeCookie(Cookies.ACCESS_TOKEN);
        removeCookie(Cookies.REFRESH_TOKEN);

        return navigate(routes.LOGIN);
    }

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
                    <LockOutlinedIcon/>
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign up
                </Typography>
                <Box component="form" noValidate onSubmit={handleSubmit} sx={{mt: 3}}>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                id="username"
                                label="Username"
                                name="username"
                                autoComplete="username"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                autoComplete="new-password"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                name="confirm_password"
                                label="Confirm Password"
                                type="password"
                                id="confirm_password"
                                autoComplete="new-password"
                            />
                        </Grid>
                    </Grid>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{mt: 3, mb: 2}}
                    >
                        Sign Up
                    </Button>
                    <Grid container justifyContent="flex-end">
                        <Grid item>
                            <Link href={Routes.LOGIN} variant="body2">
                                Already have an account? Sign in
                            </Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </Container>
    );
}