import request from '@/utils/request'

export function getAllDepartment() {
  return request({
    url: '/department/query/all',
    method: 'get'
  })
}


