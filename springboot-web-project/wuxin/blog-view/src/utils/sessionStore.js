import store from '../store/index'
import {setSetStore,getStore} from "@/utils/session";

const Index = store.state.index
const Comment = store.state.comment


const Index_State = 'Index_State'
const Comment_State = 'Comment_State'

// console.log('index',Index,'comment',Comment)

export function setIndexState(){
    setSetStore(Index_State,Index)
    setSetStore(Comment_State,Comment)
}

export function getIndexState(){
    return  getStore(Index_State)
}

export function getCommentState(){
    return getStore(Comment_State)
}


