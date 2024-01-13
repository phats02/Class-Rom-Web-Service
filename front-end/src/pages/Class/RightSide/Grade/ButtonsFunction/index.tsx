import { useSelector } from "react-redux";
import { RootState } from "../../../../../redux-toolkit/store";
import ExportGradeButton from "./ExportGradeButton";
import DownloadGradeTemplateButton from "./DownloadGradeTemplateButton";
import DownloadStudentTemplate from "./DownloadStudentTemplate";
import UploadStudentCSVButton from "./UploadStudentCSVButton";

const ButtonsFunction = () => {
  const currentClassRoom = useSelector(
    (state: RootState) => state.classroomReducer.currentClassRoom
  );
  return (
    <div style={{ display: "flex", gap: 10, marginBottom: 10 }}>
      <ExportGradeButton classRoom={currentClassRoom} />
      <DownloadStudentTemplate />
      <UploadStudentCSVButton />
    </div>
  );
};

export default ButtonsFunction;
