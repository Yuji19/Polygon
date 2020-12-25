import request from '@/utils/request'

export function getAllRole() {
  return request({
    url: '/role/query/all',
    method: 'get'
  })
}


export function getRolePage(data){
  return request({
    url: '/role/query/page',
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

export function addRole(data){
	return request({
    url: '/role/add/one',
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

export function deleteRole(param){
	return request({
		url: `/role/delete/${param}`,
		method: 'delete'
	})
}

export function addRolePermission(params) {
  return request({
    url: `/role/add/${params.rid}/${params.pids}`,
    method: 'get'
  })
}

export function deleteRolePermission(params) {
  return request({
    url: `/role/delete/${params.rid}/${params.pids}`,
    method: 'delete'
  })
}

export function updateRoleBase(data){
	return request({
		url: '/role/update/base',
		method: 'put',
		data
	})
}


