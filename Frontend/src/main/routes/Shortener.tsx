import TextField from "@mui/material/TextField";
import * as React from "react";
import {useEffect, useState} from "react";
import {Avatar, Button, Container, Link, Typography} from "@mui/material";
import {DirectionsRun} from "@mui/icons-material";
import Routes from "../routes";
import {useCookies} from "react-cookie";
import Cookies from "../cookies";
import {useNavigate} from "react-router-dom";
import routes from "../routes";
import axios from "../api/client";
import Endpoints from "../api/endpoints";
import LinkDTO from "../dto/LinkDTO";

export default function Shortener() {

    const [urlCode, setUrlCode] = useState<string>();
    const [cookies, setCookie, removeCookie] = useCookies();
    const navigate = useNavigate();

    const userId = cookies[Cookies.USER_ID] as number;

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        let form = new FormData(event.currentTarget);
        let url = form.get("url");
        if (typeof url !== "string" || url.length == 0) {
            return;
        }

        let link: LinkDTO = {
            url: url,
            userId: userId
        }

        axios.post<LinkDTO>(Endpoints.PATH_LINKS, link)
            .then(res => {
                if (res.status !== 200) {
                    return;
                }
                setUrlCode(res.data.code);
            })
            .catch(error => {
                console.log("Error posting url", error);
            })
    };

    const handleNewUrl = () => {
        setUrlCode(undefined);
    }

    //init
    useEffect(() => {
        if (!cookies[Cookies.USER_ID] || !cookies[Cookies.ACCESS_TOKEN]) {
            return navigate(routes.LOGIN);
        }
    }, []);

    return (
        <Container maxWidth="xl" sx={{}}>
            <Container component="main" maxWidth="md" sx={{
                marginTop: 8,
                display: "flex",
                flexDirection: "column",
                alignItems: "center"
            }}
            >
                <Link href={Routes.ACCOUNT}>
                    <Avatar sx={{m: 1, bgcolor: 'secondary.main'}} variant="square">
                        <DirectionsRun
                            color="primary"
                            fontSize="small"
                        />
                    </Avatar>
                </Link>
                <Typography
                    component="h1"
                    variant="h5"
                >
                    Shorten the URL
                </Typography>
                {!urlCode ?
                    <Container maxWidth="xl">
                        <Container
                            component="form"
                            onSubmit={handleSubmit}
                            maxWidth="xl"
                        >
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                name="url"
                                label="URL"
                                type="url"
                                id="url"
                            />
                            <Button
                                type="submit"
                                variant="outlined"
                                color="secondary"
                                fullWidth
                            >
                                Shorten
                            </Button>
                        </Container>
                    </Container>
                    :
                    <Container maxWidth="xl">
                        <Link href={Endpoints.SHORTENER_URL + '/' + urlCode} target="_blank">
                            <TextField
                                value={Endpoints.SHORTENER_URL + '/' + urlCode}
                                margin="normal"
                                fullWidth
                                disabled/>
                        </Link>
                        <Button
                            onClick={handleNewUrl}
                            variant="outlined"
                            color="secondary"
                            fullWidth
                        >
                            New URL
                        </Button>
                    </Container>
                }
            </Container>
        </Container>
    );
}