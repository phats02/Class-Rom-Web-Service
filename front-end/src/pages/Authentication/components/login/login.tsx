import * as React from "react";
import Avatar from "@mui/material/Avatar";
import LoadingButton from "@mui/lab/LoadingButton";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import { Link, useNavigate } from "react-router-dom";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useDispatch } from "react-redux";
import { useState } from "react";
import GoogleButton from "react-google-button";
import Paper from "@mui/material/Paper";
import { AuthApi } from "../../../../api/auth";
import { setToken } from "../../../../redux-toolkit/slice/auth.slice";
import { toast } from "react-toastify";
import { setCurrentUser } from "../../../../redux-toolkit/slice/user.slice";
import { useGoogleLogin, GoogleOAuthProvider } from "@react-oauth/google";

const defaultTheme = createTheme();

const initialForm = {
  email: "",
  password: "",
};

export default function Login() {
  const dispatch = useDispatch();
  const [formData, setFormData] = useState(initialForm);
  const [isLoading, setIsLoading] = useState(false);
  const [emailError, setEmailError] = useState("");
  const navigate = useNavigate();
  const validateEmail = (email: string) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    return emailRegex.test(email);
  };

  const google_login = useGoogleLogin({
    onSuccess: async (tokenResponse) => {
      try {
        const res = await AuthApi.loginByGoogle(tokenResponse.access_token);
        dispatch(setToken({ token: res.jwt }));
        dispatch(setCurrentUser({ user: res.user }));
      } catch (err) {
        toast.error(err as string);
      }
    },
    onError: (errorResponse) => console.log(errorResponse),
  });

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    if (!validateEmail(formData.email)) {
      setEmailError("Please enter a valid email address");
      return;
    }

    setEmailError("");

    try {
      setIsLoading(true);
      const res = await AuthApi.login(formData);
      console.log("🚀 ~ file: login.tsx:54 ~ handleSubmit ~ res:", res);
      if (!res?.success) throw res?.message || "Cannot send your request";
      dispatch(setToken({ token: res.jwt as any }));
      dispatch(setCurrentUser({ user: res.user as any }));
      toast.success("Login successfully");
      navigate("/");
    } catch (error) {
      console.log(error);
      toast.warning("Login fail with error:" + error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <ThemeProvider theme={defaultTheme}>
        <Grid container component="main" sx={{ height: "100vh" }}>
          <CssBaseline />
          <Grid
            item
            xs={false}
            sm={4}
            md={7}
            sx={{
              backgroundImage:
                "url(https://images.unsplash.com/photo-1524178232363-1fb2b075b655?q=80&w=1470&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D)",
              backgroundRepeat: "no-repeat",
              backgroundColor: (t) =>
                t.palette.mode === "light"
                  ? t.palette.grey[50]
                  : t.palette.grey[900],
              backgroundSize: "cover",
              backgroundPosition: "center",
            }}
          />
          <Grid
            item
            xs={12}
            sm={8}
            md={5}
            component={Paper}
            elevation={6}
            square
          >
            <Box
              sx={{
                my: 4,
                mx: 4,
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                <LockOutlinedIcon />
              </Avatar>
              <Typography component="h1" variant="h5">
                Sign in
              </Typography>
              <Box component="form" onSubmit={handleSubmit} sx={{ mt: 1 }}>
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  name="email"
                  autoComplete="email"
                  autoFocus
                  error={!!emailError}
                  helperText={emailError}
                  onChange={(event) =>
                    setFormData((prev) => ({
                      ...prev,
                      email: event.target.value,
                    }))
                  }
                />
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  autoComplete="current-password"
                  onChange={(event) =>
                    setFormData((prev) => ({
                      ...prev,
                      password: event.target.value,
                    }))
                  }
                />
                <LoadingButton
                  type="submit"
                  fullWidth
                  variant="contained"
                  sx={{ mt: 3, mb: 2 }}
                  loading={isLoading}
                >
                  Sign In
                </LoadingButton>
                <Grid container>
                  <Grid item xs>
                    <Link to="/login">Forgot password?</Link>
                  </Grid>
                  <Grid item>
                    <Link to="/register">
                      {"Don't have an account? Sign Up"}
                    </Link>
                  </Grid>
                </Grid>
              </Box>
            </Box>
            <GoogleButton
              className="google-button"
              onClick={() => {
                google_login();
              }}
              style={{ margin: "0px auto" }}
            >
              Sign in with Google
            </GoogleButton>
          </Grid>
        </Grid>
      </ThemeProvider>
    </>
  );
}
