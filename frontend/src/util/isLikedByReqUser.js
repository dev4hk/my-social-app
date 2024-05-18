export const isLikedByReqUser = (reqUserId, post) => {
  console.log(post);
  for (let user of post.likedBy) {
    if (user.id === reqUserId) {
      return true;
    }
  }
  return false;
};
