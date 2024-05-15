export const isLikedByReqUser = (reqUserId, post) => {
  for (let user of post.likedBy) {
    if (user.id === reqUserId) {
      return true;
    }
  }
  return false;
};
