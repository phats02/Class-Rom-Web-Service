import React from "react";
import Papa from "papaparse";
import { ClassRoom } from "../../../../../types/Classroom.type";
import { Button } from "@mui/material";
import SystemUpdateAltIcon from "@mui/icons-material/SystemUpdateAlt";

type Props = {
  classRoom: ClassRoom;
};

const ExportGradeButton = ({ classRoom }: Props) => {
  const assignments = classRoom.assignments.map(
    (assignment) => assignment.name
  );
  const header = ["StudentId", ...assignments];
  const data = classRoom.studentIds.map((studentId) => {
    const grades = classRoom.assignments.map(
      (assignment) =>
        assignment.grades.find((grade) => grade.id === studentId)?.grade ?? 0
    );
    return [studentId, ...grades];
  });

  const handleExport = (e: any) => {
    e.preventDefault();
    const csvObject = {
      fields: header,
      data,
    };
    const csv = Papa.unparse(csvObject);
    var BOM = new Uint8Array([0xef, 0xbb, 0xbf]);
    const blob = new Blob([BOM, csv], { type: "text/csv;charset=utf-8;" });
    const link = document.createElement("a");
    link.href = window.URL.createObjectURL(blob);
    link.download = `${classRoom.name}.csv`;
    link.click();
  };

  return (
    <Button
      onClick={handleExport}
      variant={"contained"}
      startIcon={<SystemUpdateAltIcon />}
    >
      Export Grade Board
    </Button>
  );
};

export default ExportGradeButton;
