import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../redux-toolkit/store";
import { Button } from "@mui/material";
import { logoutUser } from "../../redux-toolkit/slice/auth.slice";
import "./profile.css";

const ProfilePage = () => {
  const storeDispatch = useDispatch();
  const currentUser = useSelector(
    (state: RootState) => state.userReducer.currentUser
  );

  const logoutHandle = () => {
    storeDispatch(logoutUser({}));
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
        </div>
      </div>
      {currentUser && (
        <div style={{ width: "100%", display: "flex", justifyItems: "center" }}>
          <Button
            variant="outlined"
            style={{ margin: "10px auto" }}
            onClick={logoutHandle}
          >
            Logout
          </Button>
        </div>
      )}
    </>
  );
};

export default ProfilePage;
