import React from "react";
import { ReactElement } from "react";
import { Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Home from "./pages/Home";
type RouterProp = {
  path: string;
  component: ReactElement;
};

const privateRoutes: RouterProp[] = [
  {
    path: "/",
    component: <Navigate to="/home" />,
  },
  {
    path: "/home",
    component: <Home />,
  },
];

const publicRoutes: RouterProp[] = [
  {
    path: "/login",
    component: <Login />,
  },
];

export { privateRoutes, publicRoutes };
