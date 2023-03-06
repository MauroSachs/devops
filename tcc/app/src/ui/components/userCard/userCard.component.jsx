import { useState } from 'react'
import { useLogout } from '../../../api/user/identification/logout.api'


import defaultImage from '../../../images/defaultImage.png'
import './userCard.css'
export function UserCard({page, setPage, loggedUser }) {
    const {logout} = useLogout()
    const [userInput, setUserInput] = useState('')

    function getUserAge() {
        const birthDate = Date.parse(loggedUser?.data_nascimento)
        const MILISSECONS_IN_YEAR = 31556952000
        const ageInMilli = Date.now() - birthDate
        return Number.parseInt(ageInMilli / MILISSECONS_IN_YEAR)
    
    }
 

    function handleChange(event) {
        setUserInput(event.target.value)
    }
    async function handleSubmit(event) {
        event.preventDefault()
        await setPage({value: 'userList', prop: userInput})
        
    }   
    async function handleLogout() {
        await logout()
    }


    return (
        <div className='user-card-container'>
            {loggedUser ?
                <>
                {page.value!=='userList' ?
                    <form className='user-search-form' onSubmit={handleSubmit}>
                        <input value={userInput} onChange={handleChange} placeholder='Buscar Usuário'></input>
                        <button></button>
                    </form>
                    :null}
                    <div className='user-card-info'>
                        <img onClick={() => setPage({ value: 'userPage', prop: loggedUser.id })} src={loggedUser.imagem_perfil || defaultImage} alt='usuario-foto'/>
                        <h1 onClick={() => setPage({ value: 'userPage', prop: loggedUser.id })}> {loggedUser.apelido}</h1>
                        <h2 onClick={() => setPage({ value: 'userPage', prop: loggedUser.id })}>{loggedUser.nome}</h2>
                        <h3>{loggedUser.email}</h3>
                        <h2>{getUserAge()} anos</h2>
                        <div className='user-card-buttons'>
                            <button onClick={() => setPage({value:'home'})}>HOME</button>
                            <button onClick={() => setPage({value:'friends'})}>AMIGOS</button>
                            <button onClick={() => setPage({value:'requests'})}>SOLICITAÇÕES</button>
                            <button onClick={() => setPage({value:'edit'})}>EDITAR PERFIL</button>
                            <button onClick={handleLogout}>SAIR</button>
                        </div>


                    </div>
                </>
                : null}
        </div>
    )

}