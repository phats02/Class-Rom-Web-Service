import { ToastContainer } from "react-toastify";
import { Routes, Route } from "react-router-dom";
import { privateRoutes, publicRoutes } from "./routes";

import "./App.css";
import PrivateRouter from "./pages/Common/PrivateRouter";
import { RootState } from "./redux-toolkit/store";
import "react-toastify/dist/ReactToastify.css";
import useAuth from "./hook/useAuth";
import { useSelector } from "react-redux";
import { ACCESS_TOKEN } from "./utils/constant";

function App() {
  useAuth();
  const accessToken = useSelector(
    (state: any) =>
      state.authReducer.accessToken || localStorage.getItem(ACCESS_TOKEN)
  );
  return (
    <div>
      <ToastContainer />
      <Routes>
        {publicRoutes.map((item) => (
          <Route
            key={item.path}
            path={item.path}
            element={item.component}
          ></Route>
        ))}
        {privateRoutes.map((item) => (
          <Route
            key={item.path}
            path={item.path}
            element={
              <PrivateRouter isLogged={!!accessToken}>
                {item.component}
              </PrivateRouter>
            }
          ></Route>
        ))}
      </Routes>
    </div>
  );
}

export default App;
