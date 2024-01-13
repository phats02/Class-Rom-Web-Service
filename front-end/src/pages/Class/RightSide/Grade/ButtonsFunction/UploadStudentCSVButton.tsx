import { Button } from "@mui/material";
import React from "react";
import Papa from "papaparse";
import { toast } from "react-toastify";
import { ClassRoomApi } from "../../../../../api/classroom";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../../../../redux-toolkit/store";
import BackupIcon from "@mui/icons-material/Backup";

const UploadStudentCSVButton = () => {
  const navigate = useNavigate();
  const currentClassRoom = useSelector(
    (state: RootState) => state.classroomReducer.currentClassRoom
  );
  const handleSubmitFile = (event: any) => {
    event.preventDefault();
    const selectedFile = event.target?.files[0];
    if (!selectedFile) {
      return;
    }
    // setLoading(true);
    Papa.parse(selectedFile, {
      complete: async (result) => {
        const data = result.data;
        const studentIds = data.map((student: any) => {
          return student.StudentId.toString();
        });
        // ignore empty rows
        const filteredStudentIds = studentIds.filter((studentId) => {
          return studentId !== "";
        });

        await ClassRoomApi.uploadStudentID(currentClassRoom.slug, {
          studentIds: filteredStudentIds,
        });

        toast.success("Upload successfully!");
        navigate(0);
      },
      header: true,
    });
  };
  return (
    <>
      <input
        style={{ display: "none" }}
        id="contained-button-file"
        type="file"
        accept=".csv"
        onChange={handleSubmitFile}
      />
      <label htmlFor="contained-button-file">
        <Button
          variant="contained"
          color="secondary"
          component="span"
          startIcon={<BackupIcon />}
        >
          Upload Student Template
        </Button>
      </label>
    </>
  );
};

export default UploadStudentCSVButton;
