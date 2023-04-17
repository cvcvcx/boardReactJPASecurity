import axios from "axios";

export function axiosRequest(path, method, data, contentType) {
  const loginUser = localStorage.getItem("accessToken");
  let accessToken = "";
  if (loginUser === "" || loginUser === null) {
    accessToken = null;
  } else {
    accessToken = "Bearer " + loginUser;
  }
  return axios({
    method: method,
    url: "http://127.0.0.1:8080/" + path,
    data: data,
    headers: {
      Authorization: accessToken,
      "Content-Type": contentType,
    },
  });
}
