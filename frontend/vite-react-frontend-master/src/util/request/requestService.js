import axios from "axios";

export function axiosRequest(path, method, data = null, contentType) {
  const loginUser = sessionStorage.getItem("accessToken");
  const BASE_URL = import.meta.env.VITE_PRODUCTION_API_URL;
  let accessToken = "";
  if (loginUser === "" || loginUser === null) {
    accessToken = null;
  } else {
    accessToken = "Bearer " + loginUser;
  }
  return axios({
    method: method,
    url: BASE_URL + path,
    data: data,
    headers: {
      Authorization: accessToken,
      "Content-Type": contentType,
    },
  });
}
