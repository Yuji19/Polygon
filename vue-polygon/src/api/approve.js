import request from '@/utils/request'


export function getApproveList(param) {
  return request({
    url: `/approve/list/${param}`,
    method: 'get'
  })
}
