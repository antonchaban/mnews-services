import React, {useEffect} from 'react';
import {useCookies} from "react-cookie";
import {useNavigate} from "react-router-dom";
import Routes from "../routes";
import Cookies from "../cookies";

export default function App() {

    const [cookies, setCookie, removeCookie] = useCookies();
    const navigate = useNavigate();

    let accessToken = cookies[Cookies.ACCESS_TOKEN];
    let userId = cookies[Cookies.USER_ID];

    useEffect(() => {
        if (accessToken && userId) {
            return navigate(Routes.SHORTENER);
        }
        return navigate(Routes.LOGIN);
    }, [])

    return (
     <div/>
    );

}
