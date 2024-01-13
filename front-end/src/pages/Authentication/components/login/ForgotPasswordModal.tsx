import { Button, Modal, TextField, Typography } from "@mui/material";
import React, { useMemo, useState } from "react";
import { createUseStyles } from "react-jss";
import { AuthApi } from "../../../../api/auth";
import { toast } from "react-toastify";
import { LoadingButton } from "@mui/lab";

const useStyle = createUseStyles({
  container: {
    position: "absolute" as "absolute",
    top: "40%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: "60vw",
    maxWidth: 500,
    backgroundColor: "white",
    boxShadow: "rgba(0, 0, 0, 0.24) 0px 3px 8px;",
    borderRadius: 4,
    padding: "30px 40px",
    display: "flex",
    flexDirection: "column",
    rowGap: 35,
  },
  buttonsBottom: {
    display: "flex",
    justifyContent: "flex-end",
    gap: 10,
  },
});

type Props = {
  isOpen: boolean;
  onClose: () => void;
};

const ForgotPasswordModal = ({ isOpen, onClose }: Props) => {
  const classes = useStyle();
  const [email, setEmail] = useState<string>("");
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const validateEmail = useMemo(
    () =>
      email.match(
        /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      ),
    [email]
  );

  const handleSubmit = async () => {
    try {
      setIsLoading(true);
      const res = await AuthApi.forgotPassword(email);
      if (!res.success) toast.error(res.message);
      else {
        toast.success(res.message);
        onClose();
      }
    } catch (err) {
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Modal
      open={isOpen}
      onClose={onClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <div className={classes.container}>
        <Typography component="h1" variant="h5">
          Forgot password with your email
        </Typography>
        <TextField
          margin="normal"
          required
          fullWidth
          name="email"
          label="Email"
          type="email"
          id="email"
          autoComplete="email"
          value={email}
          onChange={(e) => {
            setEmail(e.currentTarget.value);
          }}
        />
        <div className={classes.buttonsBottom}>
          <Button onClick={onClose}>Cancel</Button>
          <LoadingButton
            onClick={handleSubmit}
            variant={"contained"}
            color={"primary"}
            disabled={!validateEmail}
            loading={isLoading}
          >
            Send reset link
          </LoadingButton>
        </div>
      </div>
    </Modal>
  );
};

export default ForgotPasswordModal;
