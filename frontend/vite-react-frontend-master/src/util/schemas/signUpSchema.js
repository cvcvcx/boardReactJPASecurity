import * as yup from "yup";

const signUpSchema = yup.object().shape({
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
  passwordConfirm: yup
    .string()
    .oneOf([yup.ref("password"), null], "비밀번호가 일치하지 않습니다.")
    .required("비밀번호 확인을 입력해주세요"),
  phoneNumber: yup
    .string()
    .matches(
      /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/,
      "전화번호를 제대로 입력해주세요"
    )
    .required("전화번호를 입력해주세요"),
  name: yup
    .string()
    .matches(/^[가-힣a-zA-Z]+$/, "이름을 제대로 입력해주세요")
    .required("이름을 입력해주세요"),
  nickName: yup
    .string()
    .matches(
      /^[\w\Wㄱ-ㅎㅏ-ㅣ가-힣]{2,20}$/,
      "닉네임은 2자이상, 20자이하로 입력해주세요"
    )
    .required("닉네임을 입력해주세요"),
});

export default signUpSchema;
