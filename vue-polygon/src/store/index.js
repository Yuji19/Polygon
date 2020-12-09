import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import employee from './modules/employee'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    employee
  },
  getters
})

export default store
