import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { matchRoutes, useLocation, useNavigate } from "react-router-dom";
import { ACCESS_TOKEN, USER_ID } from "../utils/constant";
import { logoutUser, setToken } from "../redux-toolkit/slice/auth.slice";
import { AppDispatch, RootState } from "../redux-toolkit/store";
import { publicRoutes } from "../routes";

const useAuth = () => {
  const storeDispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const location = useLocation();
  const match = matchRoutes(publicRoutes, location);
  const router = match ? match[0].route : null;

  useEffect(() => {
    const accessToken = localStorage.getItem(ACCESS_TOKEN);
    if (!accessToken) {
      storeDispatch(logoutUser({}));
      if (!router?.path) navigate("/login");
      return;
    }
    storeDispatch(setToken({ token: accessToken }));
  }, []);
};

export default useAuth;
