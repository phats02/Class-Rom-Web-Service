import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../redux-toolkit/store";
import { Button, TextField } from "@mui/material";
import { logoutUser } from "../../redux-toolkit/slice/auth.slice";
import "./profile.css";
import { useState } from "react";
import { UserApi } from "../../api/user";

const ProfilePage = () => {
  const storeDispatch = useDispatch();
  const currentUser = useSelector(
    (state: RootState) => state.userReducer.currentUser
  );

  const [studentId, setStudentId] = useState<string>(
    currentUser?.student || ""
  );

  const logoutHandle = () => {
    storeDispatch(logoutUser({}));
  };

  const handleUpdateUserInfo = async () => {
    try {
      const data = await UserApi.updateUserInfo(currentUser?._id as string, {
        name: currentUser?.name || "",
        student: studentId,
      });
      console.log(
        "🚀 ~ file: index.tsx:29 ~ handleUpdateUserInfo ~ data:",
        data
      );
    } catch (err) {
      console.log(err);
    }
  };
  return (
    <>
      <div className="profile-container">
        <div className="container">
          <div className="profile-info">
            <div className="profile-avatar">
              <div className="text-avatar">
                <span>{currentUser?.name && currentUser?.name[0]}</span>
              </div>
            </div>
            <div className="profile-name">
              <h2>{currentUser?.name}</h2>
              <p className="profile-email">{currentUser?.email}</p>
            </div>
          </div>
          <TextField
            id="student-id"
            label="Student Id"
            variant="outlined"
            value={studentId}
            onChange={(e) => {
              setStudentId(e.currentTarget.value);
            }}
          />
        </div>
      </div>

      <div style={{ width: "100%", display: "flex", justifyContent: "center" }}>
        <div style={{}}>
          <Button
            variant={"contained"}
            style={{ margin: "10px" }}
            onClick={handleUpdateUserInfo}
          >
            Update
          </Button>
          <Button
            variant="outlined"
            style={{ margin: "10px" }}
            onClick={logoutHandle}
          >
            Logout
          </Button>
        </div>
      </div>
    </>
  );
};

export default ProfilePage;
