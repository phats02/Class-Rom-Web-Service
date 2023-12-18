import { LoadingButton } from "@mui/lab";
import { Box, Button, Modal, TextField } from "@mui/material";
import { useState } from "react";
import { createUseStyles } from "react-jss";

type TProps = {
  isOpen: boolean;
  handleClose: () => void;
  handleInviteUser: (email: string) => void;
};

const useStyle = createUseStyles({
  container: {
    position: "absolute" as "absolute",
    top: "40%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: "60vw",
    maxWidth: 600,
    backgroundColor: "white",
    boxShadow: "rgba(0, 0, 0, 0.24) 0px 3px 8px;",
    borderRadius: 4,
    padding: "16px 24px",
    display: "flex",
    flexDirection: "column",
    rowGap: 16,
  },
  body: {
    border: "solid 3px #c4c4c4",
    padding: 10,
    display: "flex",
    borderRadius: 16,
    flexDirection: "column",
    rowGap: 16,
    marginTop: -31,
    paddingTop: 30,
  },
  buttonsBottom: {
    display: "flex",
    justifyContent: "flex-end",
    gap: 10,
  },
});
const AddClassmate = ({ isOpen, handleClose, handleInviteUser }: TProps) => {
  const classes = useStyle();
  const [email, setEmail] = useState<string>("");
  const [emailError, setEmailError] = useState("");
  const validateEmail = (email: string) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    return emailRegex.test(email);
  };
  const handleInviteClassmate = async (e: any) => {
    e.preventDefault();
    if (!validateEmail(email)) {
      setEmailError("Please enter a valid email address");
      return;
    }

    handleInviteUser(email);
  };
  return (
    <Modal
      open={isOpen}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <div className={classes.container}>
        <h1
          style={{
            textAlign: "center",
            width: "35%",
            margin: "0px auto",
            zIndex: 1,
            backgroundColor: "white",
            fontSize: "1.5em",
          }}
        >
          Invite User
        </h1>

        <Box className={classes.body}>
          <TextField
            id="email"
            label="Email"
            variant="outlined"
            fullWidth
            value={email}
            onChange={(e) => {
              setEmail(e.currentTarget.value);
            }}
            error={emailError !== ""}
            helperText={emailError}
            autoFocus
          />

          <div className={classes.buttonsBottom}>
            <Button onClick={handleClose}>Cancel</Button>
            <LoadingButton
              type="submit"
              onClick={handleInviteClassmate}
              variant="contained"
            >
              Invite
            </LoadingButton>
          </div>
        </Box>
      </div>
    </Modal>
  );
};

export default AddClassmate;
