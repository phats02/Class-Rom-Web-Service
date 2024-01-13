import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { matchRoutes, useLocation, useNavigate } from "react-router-dom";
import { ACCESS_TOKEN, USER_ID } from "../utils/constant";
import { logoutUser, setToken } from "../redux-toolkit/slice/auth.slice";
import { AppDispatch, RootState } from "../redux-toolkit/store";
import { fetchUserInfo } from "../redux-toolkit/slice/user.slice";
import { publicRoutes } from "../router";

const useAuth = () => {
  const storeDispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const isExpired = useSelector(
    (state: RootState) => state.userReducer.isExpired
  );
  const location = useLocation();
  const match = matchRoutes(publicRoutes, location);
  const router = match ? match[0].route : null;

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
      if (!router?.path) navigate("/login");
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
