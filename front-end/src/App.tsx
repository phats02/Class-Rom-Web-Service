import "./App.css";
import Login from "./pages/Authentication/components/login";
import Register from "./pages/Authentication/components/register";
import { Routes, Route, Navigate } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import PrivateRouter from "./pages/Authentication/components/common/PrivateRouter";
import { useSelector } from "react-redux";
import useAuth from "./hook/useAuth";
import Home from "./pages/Home/Home";
import VerifyPage from "./pages/Authentication/components/Verify";
import NavBars from "./pages/NavTabs/navTabs";
import { RootState } from "./redux-toolkit/store";
import ProfilePage from "./pages/Profile";
import Class from "./pages/Class/index";
import JoinClass from "./pages/JoinClass";
import LogoutHandler from "./pages/Authentication/components/Logout";
import { GoogleOAuthProvider } from "@react-oauth/google";
function App() {
  useAuth();
  const accessToken = useSelector(
    (state: any) => state.authReducer.accessToken
  );
  const currentUser = useSelector(
    (state: RootState) => state.userReducer.currentUser
  );
  return (
    <>
      <GoogleOAuthProvider
        clientId={process.env.REACT_APP_GOOGLE_CLIENT_ID || ""}
      >
        {!!accessToken && <NavBars currentUser={currentUser}></NavBars>}
        <ToastContainer />
        <Routes>
          <Route
            path="/"
            element={
              <PrivateRouter isLogged={!!accessToken}>
                <Navigate to="/home"></Navigate>
              </PrivateRouter>
            }
          ></Route>
          <Route
            path="/login"
            element={!!accessToken ? <Navigate to="/"></Navigate> : <Login />}
          />
          <Route
            path="/register"
            element={
              !!accessToken ? <Navigate to="/"></Navigate> : <Register />
            }
          ></Route>

          <Route
            path="auth/verify"
            element={
              !!accessToken ? <Navigate to="/"></Navigate> : <VerifyPage />
            }
          />
          <Route path="/profile" element={<ProfilePage></ProfilePage>}></Route>
          <Route
            path="/home"
            element={
              <PrivateRouter isLogged={!!accessToken}>
                <Home />
              </PrivateRouter>
            }
          />
          <Route path="/classes/:classId" element={<Class />}></Route>

          <Route
            path="/classes/join/:classCode"
            element={<JoinClass />}
          ></Route>
          <Route
            path="/logout"
            element={
              <PrivateRouter isLogged={!!accessToken}>
                <LogoutHandler />
              </PrivateRouter>
            }
          />
        </Routes>
      </GoogleOAuthProvider>
    </>
  );
}

export default App;
