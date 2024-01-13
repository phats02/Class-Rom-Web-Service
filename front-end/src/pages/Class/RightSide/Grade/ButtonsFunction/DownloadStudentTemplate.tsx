import { Button } from "@mui/material";
import React from "react";
import GetAppIcon from "@mui/icons-material/GetApp";

const DownloadStudentTemplate = () => {
  return (
    <Button variant={"contained"} startIcon={<GetAppIcon />}>
      <a
        href={"/list_student_template.csv"}
        style={{ color: "inherit", textDecoration: "none" }}
      >
        Download Student Template
      </a>
    </Button>
  );
};

export default DownloadStudentTemplate;
