import { axiosInstance } from "../user/_base/axiosInstance"


export function usePostFunctions() {
    async function getPostPage(page) {
        try {
            const response = await axiosInstance.get('/posts?sort=data_inclusao,desc&size=5&page=' + page)
            return response.data
        }
        catch (error) {
            return "Erro"
        }
    }

    async function makePost(postInfo)   {
        try {
            const response = await axiosInstance.post('/posts', {
                "tituloFilme": postInfo.title.value,
                "notaFilme": postInfo.rating.value,
                "texto": postInfo.review.value,
                "privacidade": postInfo.privacy.value
            }) 
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function getPostDetails(postId)   {
        try {
            const response = await axiosInstance.get('/posts/' +postId)
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function commentPost(postId,text)   {
        try {
            const response = await axiosInstance.post('/posts/' + postId + '/comentar', {
                "texto": text
            }) 
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function likePost(postId)   {
        try {
            const response = await axiosInstance.put('/posts/' + postId+ '/curtir') 
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function dislikePost(postId)   {
        try {
            const response = await axiosInstance.put('/posts/' + postId+ '/descurtir') 
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function alterPost(postId,privacy)   {
        try {
            const response = await axiosInstance.put('/posts/' + postId, {
                "privacidade": privacy
            }) 
            return response.data
        }
        catch(error) {
            return error
        }
    }
    async function deletePost(postId)   {
        try {
            const response = await axiosInstance.delete('/posts/' + postId)
            return response.data
        }
        catch(error) {
            return error
        }
    }
        
    return {getPostPage ,makePost, getPostDetails, commentPost, likePost, dislikePost, alterPost, deletePost}
}