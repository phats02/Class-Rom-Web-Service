import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { ACCESS_TOKEN, USER_ID } from "../utils/constant";
import { logoutUser, setToken } from "../redux-toolkit/slice/auth.slice";
import { AppDispatch, RootState } from "../redux-toolkit/store";
import { fetchUserInfo } from "../redux-toolkit/slice/user.slice";

const useAuth = () => {
  const storeDispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const isExpired = useSelector(
    (state: RootState) => state.userReducer.isExpired
  );

  useEffect(() => {
    if (isExpired) {
      storeDispatch(logoutUser({}));
      navigate("/login");
      return;
    }
  }, [isExpired]);
  useEffect(() => {
    const accessToken = localStorage.getItem(ACCESS_TOKEN);
    const userId = localStorage.getItem(USER_ID);
    if (!userId || !accessToken) {
      storeDispatch(logoutUser({}));
      navigate("/login");
      return;
    }
    storeDispatch(setToken({ token: accessToken }));
    try {
      storeDispatch(fetchUserInfo(userId));
    } catch (err) {
      storeDispatch(logoutUser({}));
      navigate("/login");
      return;
    }
  }, []);
};

export default useAuth;
