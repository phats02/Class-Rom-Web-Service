import { useDispatch } from "react-redux";
import { Navigate } from "react-router-dom";
import { logoutUser } from "../../../../redux-toolkit/slice/auth.slice";

const LogoutHandler = () => {
  const storeDispatch = useDispatch();
  storeDispatch(logoutUser({}));
  return <Navigate to={"/login"} />;
};

export default LogoutHandler;
