import { Avatar, Card, CardHeader } from "@mui/material";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { searchUser } from "../../redux/Auth/auth.action";
import { createChat } from "../../redux/Message/message.action";

const SearchUser = () => {
  const reduxUser = useSelector((store) => store.auth.user);
  const [username, setUsername] = useState("");
  const dispatch = useDispatch();
  const reduxSearchUser = useSelector((store) => store.auth.searchUser);
  const handleSearchUser = (event) => {
    setUsername(event.target.value);
    dispatch(searchUser(username));
  };
  const handleClick = (id) => {
    dispatch(createChat({ userId: id }));
  };
  return (
    <div>
      <div className="py-5 relative">
        <input
          type="text"
          className="bg-transparent border border-[#3b4054] outline-none w-full px-5 py-3 rounded-full"
          placeholder="Search User..."
          onKeyDown={handleSearchUser}
        />
        <div className="absolute w-full z-10 top-[4.5rem]">
          {username &&
            reduxSearchUser
              .filter((user) => user.id !== reduxUser.id)
              .map((user) => (
                <Card
                  key={user.id}
                  className="cursor-pointer hover:text-sky-400"
                >
                  <CardHeader
                    title={user.firstName + " " + user.lastName}
                    subheader={
                      user.firstName.toLowerCase() +
                      " " +
                      user.lastName.toLowerCase()
                    }
                    avatar={
                      <Avatar src="https://images.pexels.com/photos/1264210/pexels-photo-1264210.jpeg?auto=compress&cs=tinysrgb&w=800" />
                    }
                    onClick={() => {
                      handleClick(user.id);
                      setUsername("");
                    }}
                  />
                </Card>
              ))}
        </div>
      </div>
    </div>
  );
};

export default SearchUser;
