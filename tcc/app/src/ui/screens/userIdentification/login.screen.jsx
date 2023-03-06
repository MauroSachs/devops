import './userIdentification.css'
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useEffect } from 'react'
import useGlobalUser from '../../../context/user.context';
import logo from '../../../images/logo.png'
import { login } from '../../../api/user/identification/login.api';

export function LoginScreen() {
    const navigate = useNavigate()
    const [formInput, setFormInput] = useState({
        username: { value: '', error: '' },
        password: { value: '', error: '' }
    })
    const [user, setUser] = useGlobalUser()

    function handleChange(event) {
        const { name, value } = event.target
        setFormInput(oldFormInput => ({ ...oldFormInput, [name]: { ...oldFormInput[name], value: value } }))
    }

    async function handleSubmit(event) {
        event.preventDefault()
        if (validateUsername() && validatePassword()) {
            try {
                const user = await login({ username: formInput.username.value, password: formInput.password.value })
                setUser(user)
            }
            catch (error) {
            }
        }
    }

    useEffect(() => {
        if (user) {
            navigate('/')
        }
    }, [user, navigate])

    function validateUsername() {
        if (!formInput.username.value) {
            setFormInput(oldFormInput => ({ ...oldFormInput, username: { ...oldFormInput.username, error: 'Nome não pode ser vazio' } }))
            return false
        }
        setFormInput(oldFormInput => ({ ...oldFormInput, username: { ...oldFormInput.username, error: '' } }))
        return true
    }
    function validatePassword() {
        if (!formInput.password.value) {
            setFormInput(oldFormInput => ({ ...oldFormInput, password: { ...oldFormInput.password, error: 'Senha não pode ser vazia' } }))
            return false
        }
        setFormInput(oldFormInput => ({ ...oldFormInput, password: { ...oldFormInput.password, error: '' } }))
        return true
    }

    return (
        <div className="identification-screen-container">
            <div className='identification-smaller-card identification-left-card'>
                <img src={logo} />
                <h2>Sua rede social de filmes</h2>
            </div>
            <div className='identification-larger-card identification-right-card'>
                <form className="identification-form" onSubmit={handleSubmit}>
                    <h1>Login</h1>
                    <div className='form-item'>
                        <label>Usuário</label>
                        <input
                            className="identification-input"
                            name="username"
                            autoComplete="off"
                            value={formInput.username.value}
                            onChange={handleChange}
                        />
                        <span>{formInput.username.error}</span>
                    </div>
                    <div className='form-item'>
                        <label className="identification-lable">Senha</label>
                        <input
                            type='password'
                            className="identification-input"
                            name="password"
                            autoComplete="off"
                            value={formInput.password.value}
                            onChange={handleChange}
                        />
                        <span>{formInput.password.error}</span>
                    </div>
                    <button className="identification-button">Entrar</button>
                    <span>Não possui uma conta? <a onClick={() => navigate('/register')}>Registre-se</a> </span>
                </form>
            </div>
        </div>
    )
}