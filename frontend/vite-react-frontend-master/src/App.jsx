import React from "react";
// import { Route, Routes } from "react-router-dom";
import { Route, Routes } from "react-router-dom";
import Layout from "./components/Layout";
import Content from "./pages/Content";
import GuestBookList from "./pages/GuestBookList";
import RegisterContent from "./pages/RegisterContent";
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";
const App = () => {
  return (
    <Routes>
      <Route path={"/login"} element={<SignIn />} />
      <Route path={"/signup"} element={<SignUp />} />
      <Route element={<Layout />}>
        <Route path="/" element={<GuestBookList />} />
        <Route path="/register" element={<RegisterContent />} />
        <Route path="/read" element={<Content />} />
      </Route>
    </Routes>
  );
};

export default App;
