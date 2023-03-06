import { useEffect, useState } from 'react'
import { useFriendFunctions } from '../../../api/hooks/useFriendFunctions.hook'
import './request.css'
import defaultImage from '../../../images/defaultImage.png'

export function Requests({setPage}) {
    const { getRequestsList, rejectFriend, acceptFriend } = useFriendFunctions()
    const [requests, setRequests] = useState()
    
    useEffect(() => {
        fetchRequests() 
    }, [])

    async function fetchRequests() {
        const response = await getRequestsList()
        setRequests(response)
    }

    async function handleReject(userId) {
        await rejectFriend(userId)
        fetchRequests()
    }
    async function handleAccept(userId) {
        await acceptFriend(userId)
        fetchRequests()
    }
    if (requests)
        return (
            <div className='request-list-container'>
                <ul className='request-list'>
                    {requests?.length > 0
                        ?
                        requests.map(user =>
                            <div className='request-user-info' id={user.id}>
                                <div onClick={() => setPage({ value: 'userPage', prop: user.id })} className='request-user-details'>
                                    <img onClick={() => setPage({ value: 'userPage', prop: user.id })}  src={user.imagem_perfil || defaultImage} alt='usuario-foto' />
                                    <div className='request-user-details-names'>
                                        <h1 onClick={() => setPage({ value: 'userPage', prop: user.id })} >{user.apelido}</h1>
                                        <h3 onClick={() => setPage({ value: 'userPage', prop: user.id })} >{user.nome}</h3>
                                    </div>
                                </div>
                                <div className='request-buttons'>
                                    <button onClick={() => handleAccept(user.id)}>Aceitar</button>
                                    <button onClick={() => handleReject(user.id)}>Recusar</button>
                                </div>
                            </div>
                        )
                        :
                        <h1>Nenhuma Solicitação Encontrada</h1>}
                </ul>
            </div>
        )


}