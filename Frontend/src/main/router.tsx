import {createBrowserRouter} from "react-router-dom";
import App from "./routes/App";
import Login from "./routes/Login";
import NotFound from "./routes/NotFound";
import Shortener from "./routes/Shortener";
import SignUp from "./routes/SignUp";
import React from "react";
import Account from "./routes/Account";
import Routes from "./routes";
import ApplicationError from "./routes/ApplicationError";

const router = createBrowserRouter([
    {
        path: "*",
        element: <NotFound />
    },
    {
        path: Routes.APP,
        element: <App />,
    },
    {
        path:  Routes.LOGIN,
        element: <Login />
    },
    {
        path: Routes.SIGN_UP,
        element: <SignUp />
    },
    {
        path: Routes.SHORTENER,
        element: <Shortener />
    },
    {
        path: Routes.ACCOUNT,
        element: <Account />
    },
    {
        path: Routes.APPLICATION_ERROR,
        element: <ApplicationError />
    }
])

export default router;