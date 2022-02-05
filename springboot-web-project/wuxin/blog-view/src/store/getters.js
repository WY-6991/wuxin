const getters = {


  /* ========================comment======================== */

  parentCommentId: state => state.blog.parentCommentId,
  blogId: state => state.blog.blogId,
  commentList: state => state.blog.commentList,
  current: state => state.blog.current,
  totalPage: state => state.blog.totalPage,
  commentCount: state => state.blog.commentCount,
  commentContent: state => state.blog.commentContent,
  commentUser: state => state.comment.commentUser,

  /* ========================setting======================== */
  clientSize: state => state.setting.clientSize,
  settingState: state => state.setting,



}
export default getters
