import { ReactElement } from "react";
import { Navigate } from "react-router-dom";

interface Props {
  isLogged: boolean;
  children: ReactElement;
}
const PrivateRouter = ({ isLogged, children }: Props) => {
  return isLogged ? children : <Navigate to="/login"></Navigate>;
};

export default PrivateRouter;
