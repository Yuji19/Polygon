import request from '@/utils/request'



export function addLeave(data){
  return request({
    url: '/leave/add/one',
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

export function update(data){
    return request({
        url: '/leave/update/leaveFlow',
        method: 'put',
        data: data
    })
}

export function getLeave(param){
    return request({
        url: `/leave/query/${param}`,
        method: 'get'
    })
}

export function getLeavePage(data){
  return request({
    url: '/leave/query/page/leave',
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

export function getLeaveFlowPage(data){
  return request({
    url: '/leave/query/page/leaveFlow',
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

export function getMineLeaveFlowPage(data){
  return request({
    url: '/leave/query/page/mine',
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

