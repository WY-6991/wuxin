import axios from 'axios'
import {Message} from "element-ui";

const instance = axios.create({
    baseURL: 'http://localhost:8888',
    timeout: 10000

})


// 请求拦截
// instance.interceptors.request.use(
//
//     ()=>{
//
//     },
//     ()=>{
//
//     }
// )



// 响应拦截
instance.interceptors.response.use(
    response => {
        const res = response.data
        if (res.code !== 200) {
            Message({
                message: res.message || 'Error',
                type: 'error',
                duration: 5 * 1000
            })
            return Promise.reject(new Error(res.message || 'Error'))
        } else {
            return res
        }
    },
    error => {
        Message({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)


export default instance
