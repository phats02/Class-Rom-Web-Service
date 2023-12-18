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
const CreateClassModal = ({ isOpen, handleClose }: TProps) => {
  const classes = useStyle();

  const storeDispatch = useDispatch();

  const [name, setName] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [nameError, setNameError] = useState("");
  const [isCreating, setIsCreating] = useState<boolean>(false);

  const validateClassName = (classname: string) => {
    const strings = classname.trim();
    if (!!strings) return true;
    return false;
  };
  const handleCreateClass = async (e: any) => {
    e.preventDefault();
    if (!validateClassName(name)) {
      setNameError("Please enter value");
      return;
    }
    setNameError("");
    try {
      setIsCreating(true);
      const res = await ClassRoomApi.createClass({ name, description });
      if (!res?.success) throw res?.message || "Cannot send your request";
      storeDispatch(addClass(res.course));
      toast.success("Create a " + res.course.name + " class successfully");
      handleClose();
    } catch (error) {
      console.log("🚀 ~ file: register.tsx:43 ~ handleSubmit ~ error:", error);
      toast.warning("Your request fail with error:" + error);
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
          Create a class
        </h1>

        <Box className={classes.body}>
          <TextField
            id="class-name"
            label="Class name"
            variant="outlined"
            fullWidth
            value={name}
            onChange={(e) => {
              setName(e.currentTarget.value);
            }}
            error={!!nameError}
            helperText={nameError}
            autoFocus
          />
          <TextField
            id="class-description"
            label="Description"
            variant="outlined"
            rows={5}
            value={description}
            onChange={(e) => {
              setDescription(e.currentTarget.value);
            }}
            disabled={isCreating}
            fullWidth
            multiline
          />

          <div className={classes.buttonsBottom}>
            <Button onClick={handleClose}>Cancel</Button>
            <LoadingButton
              type="submit"
              onClick={handleCreateClass}
              variant="contained"
              loading={isCreating}
            >
              Create
            </LoadingButton>
          </div>
        </Box>
      </div>
    </Modal>
  );
};

export default CreateClassModal;
