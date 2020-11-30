import {login,getInfo,logout} from '@/api/employee'
import { resetRouter, addRoutes } from '@/router'
import router from '../../router/'
import { formatRoutes } from '@/utils/index'
import { setToken , removeToken} from '@/utils/auth'

const getDefaultState = () => {
  return {
    isLogin: false,
    employee: JSON.parse(window.sessionStorage.getItem("employee")),
    routes: [],
    permissions: []
  }
}

const state = getDefaultState()

const mutations = {
    RESET_STATE: (state) => {
      Object.assign(state, getDefaultState())
    },
    INIT_CURRENT_EMPLOYEE(state,data){
      state.employee = data;
    },
    INIT_ROUTES(state,data){
      state.routes = data;
    },
    SET_PERMISSIONS(state,data){
      state.permissions = data;
    },
    SET_ISLOGIN(state,data){
      console.log("islogin: "+data)
      state.isLogin=data;
    }
}

const actions = {
  // employee login
  login({ commit }, info) {
    const { employeeNo, password, code } = info
    return new Promise((resolve, reject) => {
      login({ employeeNo: employeeNo.trim(), password: password , code: code}).then(response => {
        const { data } = response
        commit('SET_ISLOGIN',true);
        //setToken(true)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get employee info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response

        if (!data) {
          return reject('Verification failed, please Login again.')
        }

        const { employee, permissions, menus } = data

        commit('INIT_CURRENT_EMPLOYEE', employee)
        commit('SET_PERMISSIONS', permissions)
        let routes = formatRoutes(menus)
        commit('INIT_ROUTES',routes)
        addRoutes(routes)
        router.options.routes=router.options.routes.concat(routes);
        console.log(router.options.routes)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetState({ commit }) {
    return new Promise(resolve => {
      //removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

