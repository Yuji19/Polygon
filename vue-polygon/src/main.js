import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

// set ElementUI lang to EN
Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

Vue.config.productionTip = false

//自定义指令
Vue.directive('permission', {
    inserted(el, binding) {
    if (!Vue.prototype.$_hasPermission(binding.value)) {
      el.parentNode.removeChild(el);
    }
  }
});



//全局方法
Vue.prototype.$_hasPermission = function(value){
	
  if (value && value instanceof Array && value.length > 0) {
    const permissions = store.getters && store.getters.permissions
    const hasPermission = permissions.some(role => {
      return value.includes(role)
    })
    return hasPermission
  } else {
    //console.error(`need roles! Like v-permission="['admin','editor']"`)
    return false
  }
}

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
