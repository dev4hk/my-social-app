import axios from "axios";
import {
  GET_PROFILE_FAILURE,
  GET_PROFILE_REQUEST,
  GET_PROFILE_SUCCESS,
  LOGIN_FAILURE,
  LOGIN_REQUEST,
  LOGIN_SUCCESS,
  REGISTER_FAILURE,
  REGISTER_REQUEST,
  REGISTER_SUCCESS,
  SEARCH_USER_FAILURE,
  SEARCH_USER_REQUEST,
  SEARCH_USER_SUCCESS,
  UPDATE_PROFILE_FAILURE,
  UPDATE_PROFILE_REQUEST,
  UPDATE_PROFILE_SUCCESS,
} from "./auth.actionType";
import { API_BASE_URL, getToken } from "../../config/api";

export const loginUserAction = (loginData) => async (dispatch) => {
  dispatch({ type: LOGIN_REQUEST });
  try {
    const { data } = await axios.post(
      `${API_BASE_URL}/auth/signin`,
      loginData.data,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    if (data.token) {
      localStorage.setItem("jwt", data.token);
    }
    dispatch({ type: LOGIN_SUCCESS, payload: data.token });
  } catch (error) {
    dispatch({ type: LOGIN_FAILURE, payload: error });
  }
};

export const registerUserAction = (registerData) => async (dispatch) => {
  dispatch({ type: REGISTER_REQUEST });
  try {
    const { data } = await axios.post(
      `${API_BASE_URL}/auth/signup`,
      registerData.data,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    if (data.token) {
      localStorage.setItem("jwt", data.token);
    }
    dispatch({ type: REGISTER_SUCCESS, payload: data.token });
  } catch (error) {
    dispatch({ type: REGISTER_FAILURE, payload: error });
  }
};

export const getProfileAction = () => async (dispatch) => {
  dispatch({ type: GET_PROFILE_REQUEST });
  try {
    const { data } = await axios.get(`${API_BASE_URL}/api/users/profile`, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
    });
    console.log("profile --- ", data);
    dispatch({ type: GET_PROFILE_SUCCESS, payload: data });
  } catch (error) {
    dispatch({ type: GET_PROFILE_FAILURE, payload: error });
  }
};

export const updateUserAction = (updateData) => async (dispatch) => {
  dispatch({ type: UPDATE_PROFILE_REQUEST });
  try {
    const { data } = await axios.put(`${API_BASE_URL}/api/users`, updateData, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
        "Content-Type": "multipart/form-data",
      },
    });
    dispatch({ type: UPDATE_PROFILE_SUCCESS, payload: data });
  } catch (error) {
    dispatch({ type: UPDATE_PROFILE_FAILURE, payload: error });
  }
};

export const searchUser = (query) => async (dispatch) => {
  dispatch({ type: SEARCH_USER_REQUEST });
  try {
    const { data } = await axios.get(
      `${API_BASE_URL}/api/users/search?query=${query}`,
      {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      }
    );
    dispatch({ type: SEARCH_USER_SUCCESS, payload: data });
  } catch (error) {
    dispatch({ type: SEARCH_USER_FAILURE, payload: error });
  }
};
