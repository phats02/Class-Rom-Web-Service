import { Button } from "@mui/material";
import React from "react";
import { useAppDispatch } from "../../redux-toolkit/store";
import { logoutUser } from "../../redux-toolkit/slice/auth.slice";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const Setting = () => {
  const storeDispatch = useAppDispatch();
  const navigate = useNavigate();
  const handleLogout = () => {
    storeDispatch(logoutUser({}));
    toast.success("Log out successfully");
    navigate("/login");
  };
  return (
    <Button
      onClick={() => {
        handleLogout();
      }}
      variant={"contained"}
    >
      Log Out
    </Button>
  );
};

export default Setting;
