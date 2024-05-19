import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { getMediaResource } from "../../redux/Post/post.action";
import { CardMedia } from "@mui/material";

const ChatMessage = ({ item }) => {
  const user = useSelector((store) => store.auth.user);
  const [userId, setUserId] = useState();
  useEffect(() => {
    setUserId(user.id);
  }, [user.id]);
  // const isReqUserMessage = auth.user?.id === item.user.id;

  const [media, setMedia] = useState();
  useEffect(() => {
    if (item?.fileName && item?.filePath) {
      getMediaResource(item.fileName, item.filePath).then((data) => {
        setMedia(data);
      });
    }
  }, []);

  return (
    <div
      className={`flex text-white ${
        item?.user?.id !== user.id ? "justify-start" : "justify-end"
      }`}
    >
      <div
        className={`p-1 bg-[#191c29] ${
          true ? "rounded-md " : "px-5 rounded-full"
        }`}
      >
        {item?.contentType?.includes("image") && (
          <img
            src={`data:${item?.contentType};base64,${media}`}
            alt=""
            className="w-[12rem] h-[17rem] object-cover rounded-md"
          />
        )}
        {/* 
        {item?.contentType?.includes("video") && (
          <video
            className="w-[12rem] h-[17rem] object-cover rounded-md"
            controls
          >
            <source
              src={`data:${item?.contentType};base64,${media}`}
              type={item?.contentType}
            />
          </video>
        )} */}
        {item?.contentType?.includes("video") && (
          <CardMedia
            component="video"
            sx={{ maxWidth: "20rem" }}
            image={`data:${item?.contentType};base64,${media}`}
            // image={encodeURI(generateMediaURL(item.fileName, item.filePath))}
            controls
            alt=""
          />
        )}
        {item?.content.length > 0 && (
          <p className={`${true ? "py-2" : "py-1"}`}>{item?.content}</p>
        )}
      </div>
    </div>
  );
};

export default ChatMessage;
