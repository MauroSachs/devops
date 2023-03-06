import { useState } from 'react';
import { useUserFunctions } from '../../../api/hooks/useUserFunctions.hook';
import './editProfile.css'

export function EditProfile({ loggedUser }) {
    const { editUser } = useUserFunctions()
    const [formInput, setFormInput] = useState({
        name: { value: loggedUser.nome, error: '' },
        nick: { value: loggedUser.apelido, error: '' },
        image: { value: loggedUser.imagem_perfil, error: '' }
    })
    function handleChange(event) {
        const { name, value } = event.target
        setFormInput(oldFormInput => ({ ...oldFormInput, [name]: { ...oldFormInput[name], value: value } }))
    }
    async function handleSubmit(event) {
        event.preventDefault()
        if (validateUsername() && validateImage() && validateNick()) {
            await editUser(formInput)
        }
    }

    function validateUsername() {
        if (!formInput.name.value) {
            setFormInput(oldFormInput => ({ ...oldFormInput, name: { ...oldFormInput.name, error: 'Campo Obrigatório' } }))
            return false
        }
        setFormInput(oldFormInput => ({ ...oldFormInput, name: { ...oldFormInput.name, error: '' } }))
        return true
    }
    function validateNick() {
        if (formInput.nick.value.length > 50) {
            setFormInput(oldFormInput => ({ ...oldFormInput, nick: { ...oldFormInput.nick, error: 'Apelido deve ter até 50 caracteres' } }))
            return false
        }
        setFormInput(oldFormInput => ({ ...oldFormInput, nick: { ...oldFormInput.nick, error: '' } }))
        return true
    }
    function validateImage() {
        if (formInput.image.value?.length > 512) {
            setFormInput(oldFormInput => ({ ...oldFormInput, image: { ...oldFormInput.image, error: 'URL muito longa' } }))
            return false
        }
        setFormInput(oldFormInput => ({ ...oldFormInput, image: { ...oldFormInput.image, error: '' } }))
        return true
    }


    return (
        <form className='editProfile-container' onSubmit={handleSubmit}>
        <h1>Editar Perfil</h1>
            <div className='editProfile-input'>
                <label>Nome Completo</label>
                <input className="identification-input" name="name" autoComplete="off" value={formInput.name.value} onChange={handleChange} />
                <span>{formInput.name.error}</span>
            </div>
            <div className='editProfile-input'>
                <label>Apelido</label>
                <input className="identification-input" name="nick" autoComplete="off" value={formInput.nick.value} onChange={handleChange} />
                <span>{formInput.nick.error}</span>
            </div>

            <div className='editProfile-input'>
                <label>Imagem de Perfil (URL)</label>
                <input className="identification-input" type='url' name="image" autoComplete="off" value={formInput.image.value} onChange={handleChange} />
                <span>{formInput.image.error}</span>
            </div>
            <button>Editar</button>
        </form>
    )

}