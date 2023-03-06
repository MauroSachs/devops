import { axiosInstance } from "../user/_base/axiosInstance"

export function useFriendFunctions() {

    async function getFriendsPage(page, filter) {
        try {
            const response = await axiosInstance.get(`usuarios/amigos/paginado/?size=6&text=${filter}&page=${page}`)
            return response.data
        }
        catch (error) {
            return "Erro"
        }
    }
    async function getFriendsList(userId) {
        try {
            const response = await axiosInstance.get(`usuarios/amigos/${userId}`)
            return response.data
        }
        catch (error) {
            return "Erro"
        }
    }
    async function getRequestsList() {
        try {
            const response = await axiosInstance.get('/usuarios/amigos/solicitacoes')
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function requestFriend(userId) {
        
        try {
            const response = await axiosInstance.put('/usuarios/amigos/' + userId + '/solicitar')
            return response.data
        }
        catch (error) {
            return error
        }
    }
    async function acceptFriend(userId) {
        try {
            const response = await axiosInstance.put('/usuarios/amigos/' + userId + '/aceitar')
            return response.data
        }
        catch (error) {
            return error
            
        }
    }
    async function rejectFriend(userId) {
        try {
            const response = await axiosInstance.put('/usuarios/amigos/' + userId + '/recusar')
            return response.data
        }
        catch (error) {

            return error
        }
    }
    async function removeFriend(userId) {
        try {
            const response = await axiosInstance.delete('/usuarios/amigos/' + userId + '/remover')
            return response.data
        }
        catch (error) {
            return error
        }
    }

    return {getFriendsPage, getFriendsList,getRequestsList ,requestFriend, acceptFriend, rejectFriend, removeFriend }
}
