import { axiosInstance } from "../user/_base/axiosInstance"


export function useUserFunctions() {
    async function getUsersPage(page,filter) {
        try {
            const response = await axiosInstance.get(`/usuarios/?size=6&text=${filter}&page=${page}`)
            return response.data
        }
        catch(error) {
            return "Erro"
        }
    }
    
    async function detailUserById(userId)   {
        try {
            const response = await axiosInstance.get('/usuarios/'+userId)
            return response.data
        }
        catch(error) {
            return('Erro')
        }
    }
    async function listUserPosts(userId, page)   {
        try {
            const response = await axiosInstance.get('/usuarios/'+userId+'/posts?sort=data_inclusao,desc&size=5&page=' + page)
            return response.data
        }
        catch(error) {
            return('Erro')
        }
    }
    async function editUser(userInfo)   {
        try {
            const response = await axiosInstance.put('/usuarios/editar', {
                nome: userInfo.name.value,
                imagem_perfil: userInfo.image.value,
                apelido: userInfo.nick.value
            })
            return response.data
        }
        catch(error) {
            return error
        }
    }
    
        
    return { getUsersPage,detailUserById, listUserPosts, editUser}
}