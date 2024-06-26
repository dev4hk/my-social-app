import { Avatar, Box, Button, Card, Tab, Tabs } from "@mui/material";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import PostCard from "../../components/Post/PostCard";
import UserReelCard from "../../components/Reels/UserReelCard";
import { useDispatch, useSelector } from "react-redux";
import ProfileModal from "./ProfileModal";
import { getUsersPostAction } from "../../redux/Post/post.action";

const tabs = [
  { value: "post", name: "Post" },
  { value: "reels", name: "Reels" },
  { value: "saved", name: "Saved" },
  { value: "repost", name: "Repost" },
];

const posts = [1, 1, 1, 1];
const reels = [1, 1, 1, 1];
const savedPost = [1, 1, 1, 1];
const reposts = [1, 1, 1, 1];

const Profile = () => {
  const reduxUser = useSelector((store) => store.auth.user);
  const reduxPosts = useSelector((store) => store.post.posts);

  const dispatch = useDispatch();
  const { id } = useParams();

  const [value, setValue] = useState("post");

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const [open, setOpen] = useState(false);
  const handleOpenProfileModal = () => setOpen(true);
  const handleClose = () => setOpen(false);

  useEffect(() => {
    dispatch(getUsersPostAction(id));
  }, []);

  return (
    <Card className="my-10 w-[70%]">
      <div className="rounded-md">
        <div className="h-[15rem]">
          <img
            className="w-full h-full rounded-t-md object-cover"
            src="https://cdn.pixabay.com/photo/2018/01/12/14/24/night-3078326_1280.jpg"
            alt=""
          />
        </div>
        <div className="px-5 flex justify-between items-start mt-5 h-[5rem]">
          <Avatar
            src="https://cdn.pixabay.com/photo/2022/10/01/21/25/woman-7492273_1280.jpg"
            className="transform -translate-y-24"
            sx={{ width: "10rem", height: "10rem" }}
          />
          {true ? (
            <Button
              variant="outlined"
              sx={{ borderRadius: "20px" }}
              onClick={handleOpenProfileModal}
            >
              Edit Profile
            </Button>
          ) : (
            <Button variant="outlined" sx={{ borderRadius: "20px" }}>
              Follow
            </Button>
          )}
        </div>
        <div className="p-5">
          <div>
            <h1 className="py-1 font-bold text-xl">
              {reduxUser?.firstName + " " + reduxUser?.lastName}
            </h1>
            <p>
              @
              {reduxUser?.firstName.toLowerCase() +
                "_" +
                reduxUser?.lastName.toLowerCase()}
            </p>
          </div>
          <div className="flex gap-2 item-center py-3">
            <span>41 post</span>
            <span>35 followers</span>
            <span>5 followings</span>
          </div>
          <div>
            <p>
              Lorem ipsum, dolor sit amet consectetur adipisicing elit. Fugiat,
              laboriosam, fuga exercitationem alias aliquam culpa.
            </p>
          </div>
        </div>
        <section>
          <Box sx={{ width: "100%", borderBottom: 1, borderColor: "divider" }}>
            <Tabs
              value={value}
              onChange={handleChange}
              aria-label="wrapped label tabs example"
            >
              {tabs.map((item, index) => (
                <Tab key={index} value={item.value} label={item.name} wrapped />
              ))}
            </Tabs>
          </Box>
          <div className="flex justify-center">
            {value === "post" && (
              <div className="space-y-5 w-[70%] my-10">
                {reduxPosts.map((item, index) => (
                  <div
                    key={index}
                    className="border rounded-md border-slate-100"
                  >
                    <PostCard item={item} />
                  </div>
                ))}
              </div>
            )}

            {value === "reels" && (
              <div className="flex flex-wrap gap-2 justify-center my-10">
                {/* {reels.map((item, index) => (
                  <UserReelCard key={index} />
                ))} */}
              </div>
            )}

            {value === "saved" && (
              <div className="space-y-5 w-[70%] my-10">
                {/* {savedPost.map((item, index) => (
                  <div className="border rounded-md border-slate-100">
                    <PostCard key={index} item={item} />
                  </div>
                ))} */}
              </div>
            )}

            {value === "repost" && (
              <div className="space-y-5 w-[70%] my-10">
                {/* {reposts.map((item, index) => (
                  <div className="border rounded-md border-slate-100">
                    <PostCard key={index} item={item} />
                  </div>
                ))} */}
              </div>
            )}
          </div>
        </section>
      </div>
      <section>
        <ProfileModal open={open} handleClose={handleClose} />
      </section>
    </Card>
  );
};

export default Profile;
