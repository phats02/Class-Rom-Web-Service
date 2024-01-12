import { useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../../redux-toolkit/store";
import AddClassmate from "../../../Common/AddClassmate";
import { ClassRoomApi, ClassRoomRole } from "../../../../api/classroom";
import { toast } from "react-toastify";
import UserInfoRow from "./UserInfoRow";

const People = () => {
  const currentClassRoom = useSelector(
    (state: RootState) => state.classroomReducer.currentClassRoom
  );
  const currentUser = useSelector(
    (state: RootState) => state.userReducer.currentUser
  );
  const [openAddClassmate, setOpenAddClassmate] = useState<boolean>(false);

  const isTeacher =
    (currentUser && currentUser._id === currentClassRoom.owner._id) ||
    JSON.stringify(currentClassRoom.teachers).includes(currentClassRoom._id);

  const [addUserRole, setAddUserRole] = useState<ClassRoomRole>(
    ClassRoomRole.STUDENT
  );

  const handleInviteUser = async (email: string) => {
    try {
      const res = await ClassRoomApi.inviteUser({
        courseId: currentClassRoom?._id as any,
        email,
        type: addUserRole,
      });
      if (!res?.success) throw res?.message || "Cannot send your request";

      toast.success("Sended invitation link to " + email);
      setOpenAddClassmate(false);
    } catch (err) {
      toast.warning(err as any);
    }
  };
  return (
    <>
      {openAddClassmate && (
        <AddClassmate
          isOpen={openAddClassmate}
          handleClose={() => {
            setOpenAddClassmate(false);
          }}
          handleInviteUser={handleInviteUser}
        />
      )}
      <UserInfoRow
        users={currentClassRoom.teachers}
        role={"Teacher"}
        isCanAdd={isTeacher}
        onAddClick={() => {
          setOpenAddClassmate(true);
          setAddUserRole(ClassRoomRole.TEACHER);
        }}
      ></UserInfoRow>
      <UserInfoRow
        users={currentClassRoom.students}
        role={"Student"}
        isCanAdd={isTeacher}
        onAddClick={() => {
          setOpenAddClassmate(true);
          setAddUserRole(ClassRoomRole.STUDENT);
        }}
      ></UserInfoRow>
    </>
  );
};

export default People;
