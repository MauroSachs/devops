import { axiosInstance } from "../_base/axiosInstance";

export async function register(userInfo) {
    try {
        const response = await axiosInstance.post('/usuarios',
            {
                "nome": userInfo.name.value,
                "apelido": userInfo.nick.value,
                "email": userInfo.email.value,
                "senha": userInfo.password.value,
                "data_nascimento": userInfo.birthDate.value,
                "imagem_perfil": userInfo.image.value
            })
        return response.data
    }
    catch (error) {
        return error
    }
}