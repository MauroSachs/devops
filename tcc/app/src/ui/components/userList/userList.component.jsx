import { useEffect, useState } from 'react';
import './userList.css'
import defaultImage from '../../../images/defaultImage.png'
import { useUserFunctions } from '../../../api/hooks/useUserFunctions.hook';
import { useFriendFunctions } from '../../../api/hooks/useFriendFunctions.hook';

export function UserList({ initialFilter, loggedUser, type, setPage }) {
    const { getUsersPage } = useUserFunctions()
    const { getFriendsPage, getFriendsList, requestFriend, removeFriend } = useFriendFunctions()

    const [userPage, setUserPage] = useState()
    const [friendsList, setFriendsList] = useState()
    const [filter, setFilter] = useState(initialFilter)
    const [userInput, setUserInput] = useState(filter)
    const [postPageNumber, setPostPageNumber] = useState(0)
    const [friendError, setFriendError] = useState({
        error: '',
        id: ''
    })

    useEffect(() => {
        async function fetchUsersPage() {
            const response = await getUsersPage(postPageNumber, filter)
            setUserPage(response)
        }
        async function fetchFriendsPage() {
            const response = await getFriendsPage(postPageNumber, filter)
            setUserPage(response)
        }
        async function fetchFriendsList() {
            const response = await getFriendsList(loggedUser.id)
            setFriendsList(response)
        }
        fetchFriendsList()
        if (type === 'user')
            fetchUsersPage()
        else fetchFriendsPage()

    }, [postPageNumber, filter, friendError, type])


    function handleChange(event) {
        setUserInput(event.target.value)
    }
    function handleSubmit(event) {
        event.preventDefault()
        setFilter(userInput)
    }
    function getFriendshipButton(friendId) {
        if (friendId === loggedUser.id) return
        if (friendsList.find(friend => friend.id === friendId)) {
            return <button className='remove-friend-button' onClick={() => deleteFriend(friendId)}>Desfazer Amizade</button>
        }
        return <button onClick={() => makeFriendRequest(friendId)}>Solicitar Amizade</button>
    }
    async function makeFriendRequest(friendId) {
        const response = await requestFriend(friendId)
        if (response) setFriendError({ error: response.response.data.message, id: friendId })
        else setFriendError({ error: '', id: '' })
    }
    async function deleteFriend(friendId) {
        const response = await removeFriend(friendId)
        if (response) setFriendError({ error: response.response.data.message, id: friendId })
        else setFriendError({ error: '', id: '' })
    }
    function handlePreviousPage() {
        if (!checkFirstPage()) {
            setPostPageNumber(page => page - 1)
        }
    }
    function handleNextPage() {
        if (!checkLastPage()) {
            setPostPageNumber(page => page + 1)
        }
    }

    function checkFirstPage() {
        return (postPageNumber === 0)
    }
    function checkLastPage() {
        return (postPageNumber === userPage.totalPages - 1)
    }



    if (userPage)
        return (
            <div className='user-list-container'>
                <form className='user-list-search' onSubmit={handleSubmit}>
                    <input value={userInput} onChange={handleChange} placeholder={'Buscar ' + (type === 'user' ? 'Usuarios' : 'Amigos')}></input>
                </form>
                <ul className='user-list'>
                    {userPage.content.length > 0
                        ?
                        <>
                            {userPage.content.map(user =>
                                <div className='user-info' id={user.id}>
                                    <div className='user-details' >
                                        <img  onClick={() => setPage({ value: 'userPage', prop: user.id })} src={user.imagem_perfil || defaultImage} alt='usuario-foto' />
                                        <div className='user-details-names'>
                                            <h1 onClick={() => setPage({ value: 'userPage', prop: user.id })} >{user.apelido}</h1>
                                            <h3 onClick={() => setPage({ value: 'userPage', prop: user.id })} >{user.nome}</h3>
                                        </div>
                                    </div>
                                    <div className='user-friend-button'>
                                        {getFriendshipButton(user.id)}
                                        {friendError.id === user.id ? <span>{friendError.error}</span> : null}
                                    </div>
                                </div>
                            )}
                            <div className='userList-change-page-buttons'>
                                <button onClick={handlePreviousPage} className={checkFirstPage() ? 'disabled' : null}>{'<'}</button>
                                <button onClick={handleNextPage} className={checkLastPage() ? 'disabled' : null}>{'>'}</button>
                            </div>
                        </>
                        :
                        <h1>Nenhum usuario encontrado</h1>}
                </ul>
            </div>
        )
}