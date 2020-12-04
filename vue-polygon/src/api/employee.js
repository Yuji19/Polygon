import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/employee/current',
    method: 'get',
  })
}

export function getList(data){
  return request({
    url: '/employee/query/page',
    method: 'put',
    data: data,
    transformRequest: [function (data) {
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&'
            }
            return ret;
        }],
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export function addInfo(data) {
  return request({
    url: '/employee/add',
    method: 'post',
    data: data,
    transformRequest: [function (data) {
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&'
            }
            return ret;
        }],
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export function updateInfo(data) {
  return request({
    url: '/employee/update',
    method: 'put',
    data
  })
}

export function addEmployeeRole(params) {
  return request({
    url: `/employee/add/${params.eid}/${params.rids}`,
    method: 'get'
  })
}

export function deleteEmployeeRole(params) {
  return request({
    url: `/employee/delete/${params.eid}/${params.rids}`,
    method: 'delete',
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'get'
  })
}
