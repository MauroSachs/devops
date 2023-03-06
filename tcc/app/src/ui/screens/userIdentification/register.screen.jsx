import './userIdentification.css'
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import logo from '../../../images/logo.png'
import { register } from '../../../api/user/identification/register.api';


export function RegisterScreen() {
    const navigate = useNavigate()
    const [formInput, setFormInput] = useState({
        name: { value: '', error: '' },
        nick: { value: '', error: '' },
        email: { value: '', error: '' },
        password: { value: '', error: '' },
        birthDate: { value: '', error: '' },
        image: { value: '', error: '' }
    })
    const [page, setPage] = useState(0)
 
    function handleChange(event) {
        const { name, value } = event.target
        setFormInput(oldFormInput => ({ ...oldFormInput, [name]: { ...oldFormInput[name], value: value } }))
    }
    async function handleNextPage(event) {
        event.preventDefault()
        if(validateNick() && validateBirthDate() && validateImage()) {
            setPage(1)
        }
    }
    async function handleSubmit(event) {
        event.preventDefault()
        if (validateUsername() && validateEmail() && validatePassword()) {
            const response = await register(formInput)
            if(response.response) {
                setFormInput(oldFormInput => ({ ...oldFormInput, email: { ...oldFormInput.email, error: response.response.data?.message } }))
            }
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
    function validateEmail() {
        if (!formInput.email.value) {
            setFormInput(oldFormInput => ({ ...oldFormInput, email: { ...oldFormInput.email, error: 'Campo Obrigatório' } }))
            return false
        }
        if(!formInput.email.value.match('@')) {
            setFormInput(oldFormInput => ({ ...oldFormInput, email: { ...oldFormInput.email, error: 'Email inválido' } }))
        }
        setFormInput(oldFormInput => ({ ...oldFormInput, email: { ...oldFormInput.email, error: '' } }))
        return true
    }
    function validatePassword() {
        if (!formInput.password.value) {
            setFormInput(oldFormInput => ({ ...oldFormInput, password: { ...oldFormInput.password, error: 'Campo Obrigatório' } }))
            return false
        }
        setFormInput(oldFormInput => ({ ...oldFormInput, password: { ...oldFormInput.password, error: '' } }))
        return true
    }
    function validateNick() {
        if (formInput.nick.value.length>50) {
            setFormInput(oldFormInput => ({ ...oldFormInput, nick: { ...oldFormInput.nick, error: 'Apelido deve ter até 50 caracteres' } }))
            return false
        }
        setFormInput(oldFormInput => ({ ...oldFormInput, nick: { ...oldFormInput.nick, error: '' } }))
        return true
    }
    function validateBirthDate() {
        if (!formInput.birthDate.value) {
            setFormInput(oldFormInput => ({ ...oldFormInput, birthDate: { ...oldFormInput.birthDate, error: 'Campo Obrigatório' } }))
            return false
        }
        if(Date.parse(formInput.birthDate.value)>Date.now()) {
            setFormInput(oldFormInput => ({ ...oldFormInput, birthDate: { ...oldFormInput.birthDate, error: 'Data de nascimento inválida'} }))
            return false
        }
        
        setFormInput(oldFormInput => ({ ...oldFormInput, birthDate: { ...oldFormInput.birthDate, error: '' } }))
        return true
    }
    function validateImage() {
        if (formInput.image.value.length>512) {
            setFormInput(oldFormInput => ({ ...oldFormInput, image: { ...oldFormInput.image, error: 'URL muito longa' } }))
            return false
        }
        setFormInput(oldFormInput => ({ ...oldFormInput, image: { ...oldFormInput.image, error: '' } }))
        return true
    }
    return (
        <div className="identification-screen-container">
            <div className='identification-larger-card identification-left-card '>
                <div className="identification-form">
                    <h1>Registre-se</h1>
                    {page === 0 ?
                        <>
                            <div className='form-item'>
                                <label>Apelido</label>
                                <input className="identification-input" name="nick" autoComplete="off" value={formInput.nick.value} onChange={handleChange} />
                                <span>{formInput.nick.error}</span>
                            </div>

                            <div className='form-item'>
                                <label>Data de Nascimento</label>
                                <input className="identification-input" type='date' name="birthDate" autoComplete="off" value={formInput.birthDate.value} onChange={handleChange} />
                                <span>{formInput.birthDate.error}</span>
                            </div>

                            <div className='form-item'>
                                <label>Imagem de Perfil (URL)</label>
                                <input className="identification-input" type='url' name="image" autoComplete="off" value={formInput.image.value} onChange={handleChange} />
                                <span>{formInput.image.error}</span>
                            </div>
                            <button type='button' onClick={handleNextPage}>Próximo</button>
                        </>
                        :
                        <>
                            <div className='form-item'>
                                <label>Nome Completo</label>
                                <input className="identification-input" name="name" autoComplete="off" value={formInput.name.value} onChange={handleChange} />
                                <span>{formInput.name.error}</span>
                            </div>
                            <div className='form-item'>
                                <label>Email</label>
                                <input className="identification-input" type='email' name="email" autoComplete="off" value={formInput.email.value} onChange={handleChange} />
                                <span>{formInput.email.error}</span>
                            </div>
                            <div className='form-item'>
                                <label>Senha</label>
                                <input className="identification-input" type='password' name="password" autoComplete="off" value={formInput.password.value} onChange={handleChange} />
                                <span>{formInput.password.error}</span>
                            </div>

                            <button onClick={() => setPage(0)} className='return-register-button'>Voltar</button>
                            <button onClick={handleSubmit}>Entrar</button>
                        </>

                    }
                    <span>Já possui uma conta? <a onClick={() => navigate('/login')}>Login</a> </span>
                </div>

            </div>
            <div className='identification-smaller-card identification-right-card'>
                <img src={logo} />
                <h2>Sua rede social de filmes</h2>
            </div>
        </div>
    )
}