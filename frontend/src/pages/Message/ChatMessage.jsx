import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";

const ChatMessage = ({ item }) => {
  const { auth } = useSelector((store) => store);
  const [userId, setUserId] = useState();
  useEffect(() => {
    setUserId(auth.user.id);
  }, [auth.user.id]);
  // const isReqUserMessage = auth.user?.id === item.user.id;
  return (
    <div
      className={`flex text-white ${!userId ? "justify-start" : "justify-end"}`}
    >
      <div
        className={`p-1 bg-[#191c29] ${
          true ? "rounded-md " : "px-5 rounded-full"
        }`}
      >
        {item?.file &&
          item?.contentType &&
          item?.contentType.includes("image") && (
            <img
              src={`data:${item?.contentType};base64,${item?.file}`}
              alt=""
              className="w-[12rem] h-[17rem] object-cover rounded-md"
            />
          )}

        {item?.file &&
          item?.contentType &&
          item?.contentType.includes("video") && (
            <video
              className="w-[12rem] h-[17rem] object-cover rounded-md"
              controls
            >
              <source
                src={`data:${item?.contentType};base64,${item?.file}`}
                type={item?.contentType}
              />
            </video>
          )}
        {item?.content.length > 0 && (
          <p className={`${true ? "py-2" : "py-1"}`}>{item?.content}</p>
        )}
      </div>
    </div>
  );
};

export default ChatMessage;
