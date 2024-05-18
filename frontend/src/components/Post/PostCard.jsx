import {
  Avatar,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CardMedia,
  Divider,
  IconButton,
  Typography,
} from "@mui/material";
import { red } from "@mui/material/colors";
import React, { useEffect, useState } from "react";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import ShareIcon from "@mui/icons-material/Share";
import ChatBubbleIcon from "@mui/icons-material/ChatBubble";
import BookmarkBorderIcon from "@mui/icons-material/BookmarkBorder";
import BookmarkIcon from "@mui/icons-material/Bookmark";
import { useDispatch, useSelector } from "react-redux";
import {
  createCommentAction,
  likePostAction,
} from "../../redux/Post/post.action";
import { isLikedByReqUser } from "../../util/isLikedByReqUser";

const comments = ["comment1", "comment2", "comment3"];

const PostCard = ({ item }) => {
  // const PostCard = () => {
  const [showComments, setShowComments] = useState(false);
  const user = useSelector((store) => store.auth.user);
  const dispatch = useDispatch();
  const handleShowComments = () => {
    setShowComments(!showComments);
  };
  const handleCreateComment = (content) => {
    const reqData = {
      postId: item?.id,
      data: {
        content: content,
      },
    };
    dispatch(createCommentAction(reqData));
  };
  // const handleCreateComment = (content) => {
  //   console.log("handleCreateComment");
  // };

  const handleLikePost = () => {
    dispatch(likePostAction(item?.id));
  };

  // const handleLikePost = () => {
  //   console.log("handleLikePost");
  // };

  return (
    <Card>
      <CardHeader
        avatar={
          <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
            R
          </Avatar>
        }
        action={
          <IconButton aria-label="settings">
            <MoreVertIcon />
          </IconButton>
        }
        title={item?.user?.firstName + " " + item?.user?.lastName}
        subheader={
          "@" +
          item?.user?.firstName.toLowerCase() +
          "_" +
          item?.user?.lastName.toLowerCase()
        }
        // title={"Title Name"}
        // subheader={"Subheader Name"}
      />
      {item?.contentType?.includes("image") && (
        // true
        <CardMedia
          component="img"
          height="194"
          image={`data:${item?.contentType};base64,${item?.file}`}
          // image="https://cdn.pixabay.com/photo/2022/11/29/11/30/lake-7624330_1280.jpg"
          alt=""
        />
      )}
      {item?.contentType?.includes("video") && (
        <CardMedia
          component="video"
          height="194"
          image={`data:${item?.contentType};base64,${item?.file}`}
          controls
          alt=""
        />
      )}
      <CardContent>
        <Typography variant="body2" color="text.secondary">
          {item?.caption}
          {/* {"Caption"} */}
        </Typography>
      </CardContent>

      <CardActions className="flex justify-between" disableSpacing>
        <div>
          <IconButton onClick={handleLikePost}>
            {isLikedByReqUser(user?.id, item) ? (
              // true
              <FavoriteIcon />
            ) : (
              <FavoriteBorderIcon />
            )}
          </IconButton>

          <IconButton>
            <ShareIcon />
          </IconButton>

          <IconButton onClick={handleShowComments}>
            <ChatBubbleIcon />
          </IconButton>
        </div>

        <div>
          <IconButton>
            {true ? <BookmarkIcon /> : <BookmarkBorderIcon />}
          </IconButton>
        </div>
      </CardActions>

      {showComments && (
        <section>
          <div className="flex items-center space-x-5 mx-3 my-5">
            <Avatar sx={{}} />
            <input
              className="w-full outline-none bg-transparent border border-[#3b4050] rounded-full px-5 py-2"
              type="text"
              placeholder="Write your comment..."
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  handleCreateComment(e.target.value);
                }
              }}
            />
          </div>
          <Divider />
          <div className="mx-3 space-y-2 my-5 text-sm">
            {item?.comments?.map((comment, index) => (
              <div key={index} className="flex items-center space-x-5">
                <Avatar
                  sx={{ height: "2rem", width: "2rem", fontSize: ".8rem" }}
                >
                  {comment?.user?.firstName[0]}
                  {/* {"S"} */}
                </Avatar>
                <p>{comment?.content}</p>
                {/* <p>{comment}</p> */}
              </div>
            ))}
          </div>
        </section>
      )}
    </Card>
  );
};

export default PostCard;
