import { Route, Routes } from "react-router-dom";
import "./App.css";
import Authentication from "./pages/Authentication/Authentication";
import Message from "./pages/Message/Message";
import HomePage from "./pages/HomePage/HomePage";
import { useDispatch, useSelector } from "react-redux";
import { useEffect } from "react";
import { getProfileAction } from "./redux/Auth/auth.action";
import { ThemeProvider } from "@emotion/react";
import { darkTheme } from "./theme/DarkTheme";

function App() {
  const reduxJwt = useSelector((store) => store.auth.jwt);
  const reduxUser = useSelector((store) => store.auth.user);
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(getProfileAction());
  }, [reduxJwt]);
  return (
    <ThemeProvider theme={darkTheme}>
      <Routes>
        <Route
          path="/*"
          element={reduxUser ? <HomePage /> : <Authentication />}
        />
        <Route path="/message" element={<Message />} />
        <Route path="/*" element={<Authentication />} />
      </Routes>
    </ThemeProvider>
  );
}

export default App;
