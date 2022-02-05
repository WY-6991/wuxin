import request from '@/utils/request'



export function getUpdateQuestionList(data) {
  return request({
    url: `/admin/question/list`,
    method: 'post',
    data
  })
}

export function createQuestion(data){
  return request({
    url: `/admin/question/add`,
    method: 'post',
    data
  })
}


export function updateQuestion(data){
  return request({
    url: `/admin/question/update`,
    method: 'post',
    data
  })
}


export function delQuestion(id){
  return request({
    url: `/admin/question/del/${id}`,
    method: 'get',
  })
}
