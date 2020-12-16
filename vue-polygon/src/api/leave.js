import request from '@/utils/request'



export function addLeave(data){
  return request({
    url: '/leave/add',
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

