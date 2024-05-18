import {
  Avatar,
  Backdrop,
  CircularProgress,
  Grid,
  IconButton,
} from "@mui/material";
import React, { useEffect, useRef, useState } from "react";
import WestIcon from "@mui/icons-material/West";
import AddIcCallIcon from "@mui/icons-material/AddIcCall";
import VideoCallIcon from "@mui/icons-material/VideoCall";
import AddPhotoAlternateIcon from "@mui/icons-material/AddPhotoAlternate";
import SearchUser from "../../components/SearchUser/SearchUser";
import "./Message.css";
import UserChatCard from "./UserChatCard";
import ChatMessage from "./ChatMessage";
import { useDispatch, useSelector } from "react-redux";
import {
  createMessage,
  getAllChats,
  getMessagesInChat,
} from "../../redux/Message/message.action";
import ChatBubbleOutlineIcon from "@mui/icons-material/ChatBubbleOutline";
import SockJS from "sockjs-client";
import Stomp from "stompjs";

const Message = () => {
  const dispatch = useDispatch();
  const reduxUser = useSelector((store) => store.auth.user);
  const reduxMessage = useSelector((store) => store.message.message);
  const reduxChats = useSelector((store) => store.message.chats);
  const [currentChat, setCurrentChat] = useState();
  const [messages, setMessages] = useState([]);
  const [content, setContent] = useState("");
  const [selectedFile, setSelectedFile] = useState();
  const [isLoading, setIsLoading] = useState(false);
  const chatContainerRef = useRef(null);

  const handleInputChange = (event) => {
    setContent(event.target.value);
  };

  const handleSelectFile = (event) => {
    const file = event.target.files[0];
    setSelectedFile(file);
  };

  useEffect(() => {
    dispatch(getAllChats());
  }, []);

  useEffect(() => {
    setMessages([...messages, reduxMessage]);
  }, [reduxMessage]);

  useEffect(() => {
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop =
        chatContainerRef.current.scrollHeight;
    }
  }, [messages]);

  const handleUserCardClick = (item) => {
    setIsLoading(true);
    setCurrentChat(item);
    getMessagesInChat(item.id).then((data) => {
      setMessages(data);
      setIsLoading(false);
    });
  };

  const [stompClient, setStompClient] = useState(null);

  useEffect(() => {
    const sock = new SockJS("http://localhost:8080/ws");
    const stomp = Stomp.over(sock);
    setStompClient(stomp);
    stomp.connect({}, onConnect, onError);
  }, []);

  useEffect(() => {
    if (stompClient && reduxUser && currentChat) {
      const subscription = stompClient.subscribe(
        `/user/${currentChat.id}/private`,
        onMessageReceive
      );
    }
  });

  const sendMessageToServer = (newMessage) => {
    if (stompClient && newMessage) {
      stompClient.send(
        `/app/chat/${currentChat.id.toString()}`,
        {},
        JSON.stringify(newMessage)
      );
    }
  };

  const onMessageReceive = (message) => {
    const receivedMessage = JSON.parse(message.body);
    console.log("message received from websocket...", receivedMessage);
    setMessages([...messages, receivedMessage]);
  };

  const onConnect = () => {
    console.log("websocket connected...");
  };

  const onError = (error) => {
    console.log("websocket connect error...", error);
  };

  const handleCreateMessage = () => {
    const formData = new FormData();
    formData.append("content", content);
    formData.append("file", selectedFile);
    dispatch(createMessage(formData, currentChat.id, sendMessageToServer));
    setContent("");
    setSelectedFile("");
  };

  return (
    <div>
      <Grid container className="h-screen overflow-y-hidden">
        <Grid item xs={3} className="px-5">
          <div className="flex h-full justify-between space-x-2">
            <div className="w-full">
              <div className="flex space-x-4 items-center py-5">
                <WestIcon />
                <h1 className="text-xl font-bold">Home</h1>
              </div>
              <div className="h-[83vh]">
                <div>
                  <SearchUser />
                </div>
                <div className="h-full space-y-4 mt-5 overflow-y-scroll no-scrollbar">
                  {reduxChats.map((item, index) => (
                    <div key={index} onClick={() => handleUserCardClick(item)}>
                      <UserChatCard chat={item} />
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </Grid>
        <Grid item xs={9} className="h-full">
          {currentChat ? (
            <div>
              <div className="flex justify-between items-center border-l p-5">
                <div className="flex items-center space-x-3">
                  <Avatar src="https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=800" />
                  <p>
                    {reduxUser?.id === currentChat.users[0].id
                      ? currentChat.users[1].firstName +
                        " " +
                        currentChat.users[1].lastName
                      : currentChat.users[0].firstName +
                        " " +
                        currentChat.users[0].lastName}
                  </p>
                </div>
                <div className="flex space-x-3">
                  <IconButton>
                    <AddIcCallIcon />
                  </IconButton>
                  <IconButton>
                    <VideoCallIcon />
                  </IconButton>
                </div>
              </div>
              <div
                className="no-scrollbar overflow-y-scroll h-[82vh] px-2 space-y-5 py-5"
                ref={chatContainerRef}
              >
                {messages.map((message, index) => (
                  <ChatMessage key={index} item={message} />
                ))}
              </div>
              <div className="sticky bottom-0 border-l">
                {selectedFile && selectedFile.type.includes("image") && (
                  <img
                    className="w-[5rem] h-[5rem] object-cover px-2"
                    src={URL.createObjectURL(selectedFile)}
                    alt=""
                  />
                )}
                {selectedFile && selectedFile.type.includes("video") && (
                  <video className="w-[5rem] h-[5rem] object-cover px-2">
                    <source
                      src={URL.createObjectURL(selectedFile)}
                      type={selectedFile.type}
                    />
                  </video>
                )}
                <div className="py-5 flex items-center justify-center space-x-5">
                  <input
                    type="text"
                    className="bg-transparent border border-[#3b4054] rounded-full w-[90%] py-3 px-5"
                    placeholder="Type Message..."
                    onChange={handleInputChange}
                    value={content}
                    onKeyDown={(e) => {
                      if (e.key === "Enter") {
                        handleCreateMessage();
                      }
                    }}
                  />
                  <div>
                    <input
                      type="file"
                      accept="image/*,video/*"
                      onChange={handleSelectFile}
                      className="hidden"
                      id="file-input"
                    />
                    <label htmlFor="file-input">
                      <AddPhotoAlternateIcon />
                    </label>
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className="h-full space-y-5 flex flex-col justify-center items-center">
              <ChatBubbleOutlineIcon sx={{ fontSize: "15rem" }} />
              <p className="text-xl font-semibold">No Chat Selected</p>
            </div>
          )}
        </Grid>
      </Grid>
      <Backdrop
        sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
        open={isLoading}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
    </div>
  );
};

export default Message;
