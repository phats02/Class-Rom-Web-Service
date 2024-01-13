import {
  DataGrid,
  GridCellEditStopParams,
  GridCellEditStopReasons,
  GridCellParams,
  GridColDef,
  GridColumnMenuColumnsItem,
  GridColumnMenuFilterItem,
  GridColumnMenuItemProps,
  GridColumnMenuProps,
  GridColumnMenuSortItem,
} from "@mui/x-data-grid";
import {
  Box,
  Button,
  Divider,
  IconButton,
  Popover,
  Stack,
} from "@mui/material";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../../redux-toolkit/store";
import { Assignment } from "../../../../types/Classroom.type";
import _, { reduce } from "lodash";
import { useMemo, useState } from "react";
import { createUseStyles } from "react-jss";
import { toast } from "react-toastify";
import { AssignmentGradeAPI } from "../../../../api/classroom";
import { updateGrade } from "../../../../redux-toolkit/slice/classroom.slice";
import ButtonsFunction from "./ButtonsFunction";
import DownloadGradeTemplateButton from "./ButtonsFunction/DownloadGradeTemplateButton";

const useStyle = createUseStyles({
  finalizedItem: {
    color: "green",
    fontSize: 20,
    fontWeight: 900,
  },
});

type PopoverButtonProps = {
  params: GridCellParams<any>;
};
const PopoverButton = ({ params }: PopoverButtonProps) => {
  const storeDispatch = useDispatch();
  const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null);

  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const currentClassRoom = useSelector(
    (item: RootState) => item.classroomReducer.currentClassRoom
  );

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleMakeFinalizeData = async () => {
    try {
      const assignmentId = params.field;
      const classSlug = currentClassRoom.slug;
      const data = await AssignmentGradeAPI.finalizeGrade(
        classSlug,
        assignmentId,
        { studentId: params.id as string }
      );
      if (!data.success) {
        toast.error(data.message);
        return;
      }
      const updatedGrade = await AssignmentGradeAPI.getGrade(
        classSlug,
        assignmentId
      );
      if (updatedGrade.success)
        storeDispatch(
          updateGrade({
            assignmentId,
            grades: updatedGrade.grade,
          })
        );
    } catch (err) {
      console.log("🚀 ~ file: index.tsx:103 ~ handelEditCellStop ~ err:", err);
      toast.error("Send request update grade point failed!");
    }
  };

  const open = Boolean(anchorEl);
  return (
    <div>
      <IconButton onClick={handleClick}>
        <MoreVertIcon></MoreVertIcon>
      </IconButton>
      <Popover
        open={open}
        anchorEl={anchorEl}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "bottom",
          horizontal: "left",
        }}
      >
        <Button
          onClick={() => {
            handleMakeFinalizeData();
          }}
        >
          Make finalized
        </Button>
      </Popover>
    </div>
  );
};
const generateColumns = (
  assignments: Assignment[],
  finalizedItemClassName: string
): GridColDef[] => {
  return [
    { field: "id", headerName: "Student Id", width: 90 },
    { field: "overall", headerName: "Overall Grade", width: 90 },
    ...assignments.map((item) => ({
      field: item._id,
      headerName: item.name + "-" + item.point,
      type: "number",
      editable: true,
      renderCell: (params: GridCellParams<any>) => {
        const element = item.grades.find((item) => item.id === params.id);
        return (
          <>
            <span
              className={
                element && !element.draft ? finalizedItemClassName : ""
              }
            >
              {(params.value as string) || 0}
            </span>
            <PopoverButton params={params} />
          </>
        );
      },
    })),
  ];
};

const generateRows = (studentIds: string[], assignments: Assignment[]) => {
  let rowList: any[] = [];
  assignments.forEach((item) => {
    rowList = rowList.concat(
      _.flatten(
        item.grades.map((grade) => ({
          id: grade.id,
          [item._id]: grade.grade,
        }))
      )
    );
  });
  const rowData = _(rowList)
    .groupBy("id")
    .map((objs) =>
      _.assignWith({}, ...objs, (val1: any, val2: any) => val1 || val2)
    )
    .value();

  const studentPoint = studentIds.map((studentId) => {
    const rowStudentData = rowData.find((item: any) => item.id === studentId);
    return rowStudentData ? rowStudentData : { id: studentId };
  });
  const calculateOverallGrade = (
    studentId: string,
    assignments: Assignment[]
  ) => {
    let pointData: any[] = [];
    assignments.forEach((item) => {
      const gradePoint = item.grades.find((grade) => grade.id === studentId);
      if (gradePoint)
        pointData.push({
          ration: item.point,
          point: gradePoint.grade,
        });
    });
    const sumRation = pointData.reduce(
      (partialSum, a) => partialSum + a.ration,
      0
    );
    const result = pointData.reduce(
      (partialSum, a) => partialSum + (a.point * a.ration) / sumRation,
      0
    );
    return result;
  };
  return studentPoint.map((item) => ({
    ...item,
    overall: calculateOverallGrade((item as any).id, assignments),
  }));
};
const Grade = () => {
  const storeDispatch = useDispatch();

  const currentClassRoom = useSelector(
    (item: RootState) => item.classroomReducer.currentClassRoom
  );

  const classes = useStyle();
  const assignments = useMemo(
    () => currentClassRoom?.assignments || [],
    [currentClassRoom]
  );

  const handelEditCellStop = async (
    params: GridCellEditStopParams,
    event: any
  ) => {
    try {
      if (params.reason === GridCellEditStopReasons.cellFocusOut) {
        event.defaultMuiPrevented = true;
        return;
      }
      const assignmentId = params.field;
      const classSlug = currentClassRoom.slug;
      const data = await AssignmentGradeAPI.setGradeAssignment(
        classSlug,
        assignmentId,
        {
          grade: event.target.value as string,
          studentId: params.id as string,
        }
      );
      if (!data.success) {
        toast.error(data.message);
        return;
      }
      const updatedGrade = await AssignmentGradeAPI.getGrade(
        classSlug,
        assignmentId
      );
      if (updatedGrade.success)
        storeDispatch(
          updateGrade({
            assignmentId,
            grades: updatedGrade.grade,
          })
        );
    } catch (err) {
      console.log("🚀 ~ file: index.tsx:103 ~ handelEditCellStop ~ err:", err);
      toast.error("Send request update grade point failed!");
    }
  };

  const rows = useMemo(
    () => generateRows(currentClassRoom.studentIds, assignments) as any,
    [assignments]
  );
  const columns = useMemo(
    () => generateColumns(assignments, classes.finalizedItem),
    [assignments]
  );

  return (
    <Box sx={{ height: 400, width: "100%" }}>
      <ButtonsFunction />
      <DataGrid
        rows={rows}
        columns={columns}
        disableRowSelectionOnClick
        onCellEditStop={handelEditCellStop}
      />
    </Box>
  );
};

export default Grade;
