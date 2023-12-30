import React from "react";
import ReactDOM from "react-dom/client";
import "./main/style/index.css";
import {RouterProvider} from "react-router-dom";
import router from "./main/router";
import {createTheme, ThemeProvider} from "@mui/material/styles";
import {MUIThemeOptions} from "./main/style/MUIThemeOptions";
import CssBaseline from "@mui/material/CssBaseline";
import {CookiesProvider} from "react-cookie";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

const theme = createTheme(MUIThemeOptions);

root.render(
    <React.StrictMode>
        <CookiesProvider>
            <ThemeProvider theme={theme}>
                <CssBaseline/>
                <RouterProvider router={router}/>
            </ThemeProvider>
        </CookiesProvider>
    </React.StrictMode>
);
