import { Avatar, Box, Button, Card, Tab, Tabs } from "@mui/material";
import React from "react";
import { useParams } from "react-router-dom";
import PostCard from "../../components/Post/PostCard";

const tabs = [
  { value: "post", name: "Post" },
  { value: "reels", name: "Reels" },
  { value: "saved", name: "Saved" },
  { value: "repost", name: "Repost" },
];

const posts = [1, 1, 1, 1];

const Profile = () => {
  const { id } = useParams();

  const [value, setValue] = React.useState("post");

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

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
            <Button variant="outlined" sx={{ borderRadius: "20px" }}>
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
            <h1 className="py-1 font-bold text-xl">Username1</h1>
            <p>@Username1</p>
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
                {posts.map((item, index) => (
                  <div className="border rounded-md border-slate-100">
                    <PostCard />
                  </div>
                ))}
              </div>
            )}
          </div>
        </section>
      </div>
    </Card>
  );
};

export default Profile;
