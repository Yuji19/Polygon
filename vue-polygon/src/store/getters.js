const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  employee: state => state.employee.employee,
  isLogin: state => state.employee.isLogin,
  permissions: state => state.employee.permissions,
  routes: state => state.employee.routes
}
export default getters
