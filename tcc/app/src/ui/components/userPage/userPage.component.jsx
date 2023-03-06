import { useEffect, useState } from 'react'
import { useFriendFunctions } from '../../../api/hooks/useFriendFunctions.hook'
import { useUserFunctions } from '../../../api/hooks/useUserFunctions.hook'
import { PostList } from '../postList/postList.component'
import './userPage.css'
import defaultImage from '../../../images/defaultImage.png'


export function UserPage({ loggedUser, pageUserId }) {
  const { getFriendsList, requestFriend, removeFriend } = useFriendFunctions()
  const { listUserPosts, detailUserById } = useUserFunctions()
  const [posts, setPosts] = useState()
  const [friends, setFriends] = useState()
  const [pageUser, setPageUser] = useState()
  const [friendError, setFriendError] = useState()
  const [postPageNumber, setPostPageNumber] = useState(0)

  useEffect(() => {
    async function fetchData() {
      const friendsResponse = await getFriendsList(pageUserId)
      const postsResponse = await listUserPosts(pageUserId, postPageNumber)
      const userResponse = await detailUserById(pageUserId)
      setFriends(friendsResponse)
      setPosts(postsResponse)
      setPageUser(userResponse)
    }
    fetchData()
  }, [postPageNumber])

  function getFriendshipButton() {
    if (pageUserId === loggedUser.id) return
    if (friends.find(friend => friend.id === loggedUser.id)) {
      return <button onClick={() => deleteFriend(pageUserId)}>Desfazer Amizade</button>
    }
    return <button onClick={() => makeFriendRequest(pageUserId)}>Solicitar Amizade</button>
  }
  async function makeFriendRequest(friendId) {
    const response = await requestFriend(friendId)
    if (response) setFriendError(response.response.data.message)
    else setFriendError()
  }
  async function deleteFriend(friendId) {
    const response = await removeFriend(friendId)
    if (response) setFriendError(response.response.data.message)
    else setFriendError()
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
    return (postPageNumber === posts.totalPages - 1)
  }

  if (posts?.content)
    return (
      <div className='userPage-container'>
        <div className='userPage-user'>
          <img src={pageUser.imagem_perfil || defaultImage} alt='usuario-foto' />
          <h1>{pageUser.apelido}</h1>
          <h2>{pageUser.nome}</h2>
          <div className='userPage-user-details'>
            <span>{posts.totalElements} Posts</span>
            <span>{friends.length} Amigos</span>
            {getFriendshipButton()}

          </div>
          <span>{friendError}</span>
        </div>
        <PostList posts={posts} loggedUser={loggedUser} />
        <div className='change-page-buttons'>
          <button onClick={handlePreviousPage} className={checkFirstPage() ? 'disabled' : null}>{'<'}</button>
          <button onClick=  {handleNextPage} className={checkLastPage() ? 'disabled' : null}>{'>'}</button>
        </div>
      </div>
    )

}