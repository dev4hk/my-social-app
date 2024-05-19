import axios from "axios";
import { API_BASE_URL, getToken } from "../../config/api";
import {
  CREATE_CHAT_FAILURE,
  CREATE_CHAT_REQUEST,
  CREATE_CHAT_SUCCESS,
  CREATE_MESSAGE_FAILURE,
  CREATE_MESSAGE_REQUEST,
  CREATE_MESSAGE_SUCCESS,
  GET_ALL_CHATS_FAILURE,
  GET_ALL_CHATS_REQUEST,
  GET_ALL_CHATS_SUCCESS,
} from "./message.actionType";

export const createMessage =
  (formData, chatId, sendMessageToServer) => async (dispatch) => {
    dispatch({ type: CREATE_MESSAGE_REQUEST });
    try {
      const { data } = await axios.post(
        `${API_BASE_URL}/api/messages/chat/${chatId}`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${getToken()}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );
      console.log("data to send...", data);
      console.log("data file...", data.file);
      sendMessageToServer(data);
      dispatch({ type: CREATE_MESSAGE_SUCCESS, payload: data });
    } catch (error) {
      dispatch({ type: CREATE_MESSAGE_FAILURE, payload: error });
    }
  };

export const createChat = (chat) => async (dispatch) => {
  dispatch({ type: CREATE_CHAT_REQUEST });

  try {
    const { data } = await axios.post(`${API_BASE_URL}/api/chats`, chat, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
        "Content-Type": "application/json",
      },
    });
    dispatch({ type: CREATE_CHAT_SUCCESS, payload: data });
  } catch (error) {
    dispatch({ type: CREATE_CHAT_FAILURE, payload: error });
  }
};

export const getAllChats = () => async (dispatch) => {
  dispatch({ type: GET_ALL_CHATS_REQUEST });

  try {
    const { data } = await axios.get(`${API_BASE_URL}/api/chats`, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
    });
    dispatch({ type: GET_ALL_CHATS_SUCCESS, payload: data });
  } catch (error) {
    dispatch({ type: GET_ALL_CHATS_FAILURE, payload: error });
  }
};

export const getMessagesInChat = async (chatId) => {
  const response = await axios.get(
    `${API_BASE_URL}/api/messages/chat/${chatId}`,
    {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
    }
  );
  return response.data;
};
