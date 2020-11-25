import {login,getInfo,logout} from '@/api/employee'
import { resetRouter, addRoutes } from '@/router'
import { formatRoutes } from '@/utils/index'
import { setToken , removeToken} from '@/utils/auth'

const getDefaultState = () => {
  return {
    session: null,
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
    }
}

const actions = {
  // employee login
  login({ commit }, info) {
    const { employeeNo, password } = info
    return new Promise((resolve, reject) => {
      login({ employeeNo: employeeNo.trim(), password: password }).then(response => {
        const { data } = response
        setToken(true)
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
        addRoutes(routes)
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

