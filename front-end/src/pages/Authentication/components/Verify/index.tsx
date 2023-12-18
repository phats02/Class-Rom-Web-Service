import React, { useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { Link, useSearchParams } from "react-router-dom";
import { toast } from "react-toastify";
import { VerifyAPI } from "../../../../api/verify";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import { Checkmark, Cross, Alert } from "../../../Common/Sign";

enum VerifyResult {
  VERIFYING = "VERIFYING",
  SUCCESS = "SUCCESS",
  FAIL = "FAIL",
}

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
});

const VerifyPage = () => {
  const classes = useStyle();
  const [searchParams] = useSearchParams();
  const [verifyResult, setVerifyResult] = useState<VerifyResult>(
    VerifyResult.SUCCESS
  );

  useEffect(() => {
    const verifyCode = async () => {
      const token = searchParams.get("code");
      if (!token) {
        setVerifyResult(VerifyResult.FAIL);
        return;
      }
      try {
        setVerifyResult(VerifyResult.VERIFYING);
        await VerifyAPI.verifyToken(token);
        setVerifyResult(VerifyResult.SUCCESS);
      } catch (err) {
        toast.error("Send request fail with error: " + err);
        setVerifyResult(VerifyResult.FAIL);
      }
    };
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
        <Button className={classes.buttonLogin} variant="contained">
          <Link className={classes.text} to={"/login"}>
            Back to Login
          </Link>
        </Button>
      </Box>
    </>
  );
};

export default VerifyPage;
