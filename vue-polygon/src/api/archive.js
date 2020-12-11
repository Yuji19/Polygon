import request from '@/utils/request'

export function getList(data){
  return request({
    url: '/archive/query/page',
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

export function addArchive(data) {
  return request({
    url: '/archive/add',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export function updateInfo(data) {
  return request({
    url: '/archive/update',
    method: 'put',
    data
  })
}


export function deleteArchive(param) {
  return request({
    url: `/archive/delete/${param}`,
    method: 'delete',
  })
}

export function download(param) {
  return request({
    url: `/archive/download/${param}`,
    method: 'get'
  })
}
