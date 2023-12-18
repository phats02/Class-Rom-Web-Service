import { Box, Button, Modal, TextField } from "@mui/material";
import { useState } from "react";
import { createUseStyles } from "react-jss";
import { ClassRoomApi } from "../../api/classroom";
import { toast } from "react-toastify";
import { useDispatch } from "react-redux";
import { addClass } from "../../redux-toolkit/slice/classroom.slice";
import { LoadingButton } from "@mui/lab";

type TProps = {
  isOpen: boolean;
  handleClose: () => void;
};

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
const JoinClassByCodeModal = ({ isOpen, handleClose }: TProps) => {
  const classes = useStyle();

  const storeDispatch = useDispatch();

  const [code, setCode] = useState<string>("");
  const [nameError, setNameError] = useState("");
  const [isCreating, setIsCreating] = useState<boolean>(false);

  const validateClassName = (classname: string) => {
    const strings = classname.trim();
    if (!!strings) return true;
    return false;
  };
  const handleCreateClass = async (e: any) => {
    e.preventDefault();
    if (!validateClassName(code)) {
      setNameError("Please enter value");
      return;
    }
    setNameError("");
    try {
      setIsCreating(true);
      const res = await ClassRoomApi.joinClassWithCode(code);
      if (!res?.success) throw res?.message || "Cannot send your request";
      storeDispatch(addClass(res.course));
      toast.success("Join a " + res.course.name + " class successfully");
      handleClose();
    } catch (error) {
      toast.warning(error as string);
    } finally {
      setIsCreating(false);
    }
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
          Join class
        </h1>

        <Box className={classes.body}>
          <TextField
            id="class-name"
            label="Class code"
            variant="outlined"
            fullWidth
            value={code}
            onChange={(e) => {
              setCode(e.currentTarget.value);
            }}
            error={!!nameError}
            helperText={nameError}
            autoFocus
          />

          <div className={classes.buttonsBottom}>
            <Button onClick={handleClose}>Cancel</Button>
            <LoadingButton
              type="submit"
              onClick={handleCreateClass}
              variant="contained"
              loading={isCreating}
            >
              Join
            </LoadingButton>
          </div>
        </Box>
      </div>
    </Modal>
  );
};

export default JoinClassByCodeModal;
