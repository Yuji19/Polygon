import request from '@/utils/request'

export function getAllPermission() {
  return request({
    url: '/permission/query/all',
    method: 'get',
  })
}

export function getPermissionOfOperation(param) {
  return request({
    url: `/permission/query/operation/${param}`,
    method: 'get',
  })
}

export function getPermissionOfMenu(param) {
  return request({
    url: `/permission/query/menu/${param}`,
    method: 'get',
  })
}