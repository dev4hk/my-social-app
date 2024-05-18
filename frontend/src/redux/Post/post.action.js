import axios from "axios";
import { API_BASE_URL, getToken } from "../../config/api";
import {
  CREATE_COMMENT_FAILURE,
  CREATE_COMMENT_REQUEST,
  CREATE_COMMENT_SUCCESS,
  CREATE_POST_FAILURE,
  CREATE_POST_REQUEST,
  CREATE_POST_SUCCESS,
  GET_ALL_POST_FAILURE,
  GET_ALL_POST_REQUEST,
  GET_ALL_POST_SUCCESS,
  GET_USERS_POST_FAILURE,
  GET_USERS_POST_REQUEST,
  GET_USERS_POST_SUCCESS,
  LIKE_POST_FAILURE,
  LIKE_POST_REQUEST,
  LIKE_POST_SUCCESS,
} from "./post.actionType";

export const createPostAction = (postData) => async (dispatch) => {
  dispatch({ type: CREATE_POST_REQUEST });
  try {
    const { data } = await axios.post(`${API_BASE_URL}/api/posts`, postData, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
        "Content-Type": "multipart/form-data",
      },
    });
    dispatch({ type: CREATE_POST_SUCCESS, payload: data });
    console.log("create post success: ", data);
  } catch (error) {
    dispatch({ type: CREATE_POST_FAILURE, payload: error });
    console.log("create post error", error);
  }
};

export const getAllPostAction = () => async (dispatch) => {
  dispatch({ type: GET_ALL_POST_REQUEST });
  try {
    const { data } = await axios.get(`${API_BASE_URL}/api/posts`, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
    });
    dispatch({ type: GET_ALL_POST_SUCCESS, payload: data });
    console.log("get all posts success: ", data);
  } catch (error) {
    dispatch({ type: GET_ALL_POST_FAILURE, payload: error });
    console.log("get all posts error: ", error);
  }
};

export const getUsersPostAction = (userId) => async (dispatch) => {
  dispatch({ type: GET_USERS_POST_REQUEST });
  try {
    const { data } = await axios.get(
      `${API_BASE_URL}/api/posts/user/${userId}`,
      {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      }
    );
    dispatch({ type: GET_USERS_POST_SUCCESS, payload: data });
  } catch (error) {
    dispatch({ type: GET_USERS_POST_FAILURE, payload: error });
  }
};

export const likePostAction = (postId) => async (dispatch) => {
  dispatch({ type: LIKE_POST_REQUEST });
  try {
    const { data } = await axios.put(
      `${API_BASE_URL}/api/posts/like/${postId}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      }
    );
    dispatch({ type: LIKE_POST_SUCCESS, payload: data });
  } catch (error) {
    dispatch({ type: LIKE_POST_FAILURE, payload: error });
  }
};

export const createCommentAction = (reqData) => async (dispatch) => {
  dispatch({ type: CREATE_COMMENT_REQUEST });
  try {
    const { data } = await axios.post(
      `${API_BASE_URL}/api/comments/post/${reqData.postId}`,
      reqData.data,
      {
        headers: {
          Authorization: `Bearer ${getToken()}`,
          "Content-Type": "application/json",
        },
      }
    );
    dispatch({ type: CREATE_COMMENT_SUCCESS });
  } catch (error) {
    dispatch({ type: CREATE_COMMENT_FAILURE, payload: error });
  }
};

export async function getMessagesInChat(chatId) {
  const response = await axios.get(
    `${API_BASE_URL}/api/messages/chat/${chatId}`,
    {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
    }
  );
  return response.data;
}
