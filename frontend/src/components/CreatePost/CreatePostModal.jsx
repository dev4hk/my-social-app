import {
  Avatar,
  Backdrop,
  Box,
  Button,
  CircularProgress,
  IconButton,
  Modal,
  Typography,
} from "@mui/material";
import { useFormik } from "formik";
import React, { useState } from "react";
import ImageIcon from "@mui/icons-material/Image";
import VideocamIcon from "@mui/icons-material/Videocam";
import { useDispatch } from "react-redux";
import { createPostAction } from "../../redux/Post/post.action";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 500,
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 4,
  borderRadius: ".6rem",
  outline: "none",
};

const CreatePostModal = ({ handleClose, open }) => {
  const [isLoading, setIsLoading] = useState(false);
  const [filePreview, setFilePreview] = useState();
  const [fileType, setFileType] = useState();
  const dispatch = useDispatch();

  const handleSelectFile = (event) => {
    setIsLoading(true);
    setFileType(event.target.files[0].type);

    const image = event.target.files[0];
    formik.setFieldValue("file", image);
    console.log(formik.values);
    setFilePreview(URL.createObjectURL(image));
    setIsLoading(false);
  };

  const formik = useFormik({
    initialValues: {
      caption: "",
      file: "",
    },
    onSubmit: (values) => {
      console.log(values.file);
      const formData = new FormData();
      formData.append("caption", values.caption);
      formData.append("file", values.file);
      dispatch(createPostAction(formData));
      setFilePreview("");
      setFileType("");
      formik.resetForm();
    },
  });

  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <form onSubmit={formik.handleSubmit}>
          <div>
            <div className="flex space-x-4 items-center">
              <Avatar />
              <div>
                <p className="font-bold text-lg">Username</p>
                <p className="text-sm">@Username</p>
              </div>
            </div>
            <textarea
              className="outline-none w-full mt-5 p-2 bg-transparent border border-[#3b4054] rounded-sm"
              name="caption"
              id="caption"
              placeholder="Write Caption..."
              rows={4}
              value={formik.values.caption}
              onChange={formik.handleChange}
            />
            <div className="flex space-x-5 items-center mt-5">
              <div>
                <input
                  type="file"
                  accept="image/*"
                  onChange={handleSelectFile}
                  style={{ display: "none" }}
                  id="image-input"
                />
                <label htmlFor="image-input">
                  <IconButton color="primary" component="span">
                    <ImageIcon />
                  </IconButton>
                </label>
                <span>Image</span>
              </div>

              <div>
                <input
                  type="file"
                  accept="video/*"
                  onChange={handleSelectFile}
                  style={{ display: "none" }}
                  id="video-input"
                />
                <label htmlFor="video-input">
                  <IconButton color="primary" component="span">
                    <VideocamIcon />
                  </IconButton>
                </label>
                <span>Video</span>
              </div>
            </div>
            {filePreview && fileType.includes("image") && (
              <div className="flex justify-center my-8">
                <img className="h-[15rem]" src={filePreview} alt="" />
              </div>
            )}
            {filePreview && fileType.includes("video") && (
              <div className="flex justify-center my-8">
                <video className="h-[15rem]" controls>
                  <source src={filePreview} type={fileType} />
                </video>
              </div>
            )}
            <div className="flex w-full justify-end">
              <Button
                type="submit"
                variant="contained"
                sx={{ borderRadius: "1.5rem" }}
              >
                Post
              </Button>
            </div>
          </div>
        </form>

        <Backdrop
          sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
          open={isLoading}
          onClick={() => setIsLoading(false)}
        >
          <CircularProgress color="inherit" />
        </Backdrop>
      </Box>
    </Modal>
  );
};

export default CreatePostModal;
