import { Box, Button, TextField, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import { VerifyResult } from "../Authentication/components/Verify";
import { Link, Navigate, useNavigate, useParams } from "react-router-dom";
import { VerifyAPI } from "../../api/verify";
import { toast } from "react-toastify";
import { Checkmark, Cross, Alert } from "../Common/Sign";
import { createUseStyles } from "react-jss";
import { LoadingButton } from "@mui/lab";
import { AuthApi } from "../../api/auth";

const useStyle = createUseStyles({
  container: {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    marginTop: "12%",
  },
  text: {
    color: "white",
    textDecoration: "none",
  },
  buttonLogin: {
    marginTop: "20px",
  },
  buttonRows: {
    display: "flex",
    width: "100%",
    gap: 10,
    alignItems: "flex-end",
    justifyContent: "center",
  },
});

const ForgotPassword = () => {
  const classes = useStyle();
  const { token } = useParams();
  const [verifyResult, setVerifyResult] = useState<VerifyResult>(
    VerifyResult.VERIFYING
  );
  const navigate = useNavigate();

  const [email, setEmail] = useState<string>("");
  const [newPassword, setNewPassword] = useState<string>("");
  const [isUpdating, setIsUpdating] = useState<boolean>(false);

  const handleChangeMyPassword = async () => {
    try {
      setIsUpdating(true);
      if (!token) return;
      const data = await AuthApi.resetPassword(token, {
        email,
        password: newPassword,
      });
      if (data.success) {
        toast.success(data.message);
        navigate("/login");
      } else {
        toast.error(data.message);
      }
    } catch (err) {
    } finally {
      setIsUpdating(false);
    }
  };

  useEffect(() => {
    const verifyCode = async () => {
      if (!token) {
        setVerifyResult(VerifyResult.FAIL);
        return;
      }
      try {
        setVerifyResult(VerifyResult.VERIFYING);
        const data = await VerifyAPI.verifyForgotPasswordToken(token);
        if (data.success) {
          toast.success("Verify code successfully");
          setVerifyResult(VerifyResult.SUCCESS);
          setEmail(data.email);
        } else {
          setVerifyResult(VerifyResult.FAIL);
          toast.error(data.message);
        }
      } catch (err) {
        toast.error("Send request fail with error: " + err);
        setVerifyResult(VerifyResult.FAIL);
      }
    };
    verifyCode();
  }, []);
  return (
    <>
      <Box className={classes.container}>
        {verifyResult === VerifyResult.VERIFYING && <Alert />}
        {verifyResult === VerifyResult.SUCCESS && <Checkmark />}
        {verifyResult === VerifyResult.FAIL && <Cross />}
        <Typography variant="h4" component="h4">
          {verifyResult}
        </Typography>
        {verifyResult === VerifyResult.VERIFYING && (
          <Typography>Email is being verified</Typography>
        )}
        {verifyResult === VerifyResult.SUCCESS && (
          <Typography>You have verified your email</Typography>
        )}
        {verifyResult === VerifyResult.FAIL && (
          <Typography>Email verification failed</Typography>
        )}
        {verifyResult === VerifyResult.SUCCESS && (
          <>
            <div>
              Email:{" "}
              <span style={{ fontWeight: "bold", fontStyle: "italic" }}>
                {email}
              </span>
            </div>
            <TextField
              margin="normal"
              required
              name="password"
              label="New password"
              type="password"
              id="password"
              autoComplete="current-password"
              value={newPassword}
              onChange={(event) => setNewPassword(event.currentTarget.value)}
            />
          </>
        )}
        <div className={classes.buttonRows}>
          <Button className={classes.buttonLogin} variant="contained">
            <Link className={classes.text} to={"/login"}>
              Back to Login
            </Link>
          </Button>
          {verifyResult === VerifyResult.SUCCESS && (
            <LoadingButton
              className={classes.buttonLogin}
              variant="contained"
              color={"success"}
              loading={isUpdating}
              onClick={handleChangeMyPassword}
            >
              Change my password
            </LoadingButton>
          )}
        </div>
      </Box>
    </>
  );
};

export default ForgotPassword;
