import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useForm } from "react-hook-form";
import CustomTextInput from "../components/CustomTextInput";
import { yupResolver } from "@hookform/resolvers/yup";
import signUpSchema from "../util/schemas/signUpSchema";
import { Link, useNavigate } from "react-router-dom";
import { axiosRequest } from "../util/request/requestService";
import { useState } from "react";

const theme = createTheme();

function SignUp() {
  const { control, handleSubmit, getValues } = useForm({
    mode: "onBlur",
    resolver: yupResolver(signUpSchema),
    defaultValues: {
      email: "",
      password: "",
      passwordConfirm: "",
      phoneNumber: "",
      name: "",
      nickName: "",
    },
  });
  const navigate = useNavigate();
  const [emailValidated, setEmailValidated] = useState(false);
  const onSubmit = (data) => {
    axiosRequest("/signup", "post", data)
      .then((res) => {
        alert("회원가입 성공!");
        navigate("/");
      })
      .catch((e) => alert("회원가입에 실패했습니다."));
  };
  const onClickCheckEmailSubmitBtn = (data) => {
    const emailValue = getValues("email");
    alert(emailValue);
    axiosRequest("/checkEmail", "post", { email: emailValue })
      .then((res) => {
        if (res.data == true) {
          alert("이미 존재하는 아이디입니다!");
          setEmailValidated(false);
        } else if ((res.status = 200)) {
          setEmailValidated(true);
        }
      })
      .catch((e) => {
        alert(e.message);
      });
  };
  return (
    <ThemeProvider theme={theme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}>
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            회원가입
          </Typography>
          <form onSubmit={handleSubmit(onSubmit)}>
            <Box sx={{ mt: 3 }}>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <CustomTextInput
                    fullWidth
                    id="email"
                    type="text"
                    label="이메일"
                    name="email"
                    control={control}
                    autoComplete="on"
                  />
                  <Button onClick={onClickCheckEmailSubmitBtn}>
                    이메일 중복 확인
                  </Button>
                </Grid>
                <Grid item xs={12}>
                  <CustomTextInput
                    fullWidth
                    id="password"
                    type="password"
                    label="비밀번호"
                    name="password"
                    control={control}
                    autoComplete="new-password"
                  />
                </Grid>
                <Grid item xs={12}>
                  <CustomTextInput
                    fullWidth
                    id="passwordConfirm"
                    type="password"
                    label="비밀번호 확인"
                    name="passwordConfirm"
                    control={control}
                    autoComplete="new-password"
                  />
                </Grid>
                <Grid item xs={12}>
                  <CustomTextInput
                    fullWidth
                    id="phoneNumber"
                    type="text"
                    label="핸드폰번호"
                    name="phoneNumber"
                    control={control}
                  />
                </Grid>
                <Grid item xs={12}>
                  <CustomTextInput
                    fullWidth
                    id="name"
                    type="text"
                    label="이름"
                    name="name"
                    control={control}
                  />
                </Grid>
                <Grid item xs={12}>
                  <CustomTextInput
                    fullWidth
                    id="nickName"
                    type="text"
                    label="닉네임"
                    name="nickName"
                    control={control}
                  />
                </Grid>
              </Grid>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                disabled={!emailValidated}
                sx={{ mt: 3, mb: 2 }}>
                Sign Up
              </Button>
              <Grid container justifyContent="flex-end">
                <Grid item>
                  <Link to="/login" variant="body2">
                    로그인 화면으로 가기
                  </Link>
                </Grid>
              </Grid>
            </Box>
          </form>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
export default SignUp;
