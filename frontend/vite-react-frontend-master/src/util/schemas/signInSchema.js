import * as yup from "yup";

const signInSchema = yup.object().shape({
  email: yup
    .string()
    .email("이메일 형식이 아닙니다.")
    .required("이메일을 입력해주세요"),
  password: yup
    .string()
    .min(8, "비밀번호는 8자리 이상 입력해주세요")
    .matches(
      /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$/,
      "비밀번호는 특수문자,영문,숫자를 각각 1자이상 입력하셔야 합니다."
    )
    .required("비밀번호를 입력해주세요"),
});

export default signInSchema;
