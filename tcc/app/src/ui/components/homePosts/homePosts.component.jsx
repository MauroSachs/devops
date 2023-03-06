import './homePosts.css'
import logo from '../../../images/logo.png'
import { useState, useEffect } from 'react'
import { PostList } from '../postList/postList.component'
import { usePostFunctions } from '../../../api/hooks/usePostFunctions.hook'

export function HomePosts({ loggedUser, setPage }) {
    const { makePost, getPostPage } = usePostFunctions()
    const [postPageNumber, setPostPageNumber] = useState(0)
    const [posts, setPosts] = useState()
    const [reload, setReload] = useState(false)
    const [userInput, setUserInput] = useState({
        title: { value: '', error: '' },
        rating: { value: 10, error: '' },
        review: { value: '', error: '' },
        privacy: { value: 'PUBLICO', error: '' }
    })

    useEffect(() => {
        async function fetchPosts() {
            const response = await getPostPage(postPageNumber)
            setPosts(response)
        }
        
        fetchPosts()
    }, [postPageNumber, reload])


    function handleChange(event) {
        const { name, value } = event.target
        setUserInput(oldUserInput => ({ ...oldUserInput, [name]: { ...oldUserInput[name], value: value } }))
    }
    async function handleSubmit(event) {
        event.preventDefault()
        const title = userInput.title.value
        const review = userInput.review.value
        let erro = ''
        if (title === '') {
            erro = 'Titulo não pode ser vazio'
        }
        if (title.length > 255) {
            erro = 'Titulo deve ter até 255 caracteres'
        }
        if (review.length > 255) {
            erro = 'Opinião deve ter até 255 caracteres'
        }
        if (review === '') {
            erro = 'Opinião não pode ser vazia'
        }
        if (erro) {
            setUserInput(oldUserInput => ({
                ...oldUserInput, title: { ...title, error: erro }
            }))
        }
        else {
            await makePost(userInput)
            setReload(reload => !reload)
        }
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


    return (
        <div className='homeposts-page-container'>
            <div className='create-post'>
                <form className='create-post-form' onSubmit={handleSubmit}>
                    <input name='title' value={userInput.title.value} onChange={handleChange} placeholder='Título'></input>
                    <div className='create-post-rating'>
                        <span>Nota: </span>
                        <button name='rating' value={1} onClick={handleChange} className={userInput.rating.value >= 1 ? 'active' : 'disabled'}>⭐</button>
                        <button name='rating' value={2} onClick={handleChange} className={userInput.rating.value >= 2 ? 'active' : 'disabled'}>⭐</button>
                        <button name='rating' value={3} onClick={handleChange} className={userInput.rating.value >= 3 ? 'active' : 'disabled'}>⭐</button>
                        <button name='rating' value={4} onClick={handleChange} className={userInput.rating.value >= 4 ? 'active' : 'disabled'}>⭐</button>
                        <button name='rating' value={5} onClick={handleChange} className={userInput.rating.value >= 5 ? 'active' : 'disabled'}>⭐</button>
                        <button name='rating' value={6} onClick={handleChange} className={userInput.rating.value >= 6 ? 'active' : 'disabled'}>⭐</button>
                        <button name='rating' value={7} onClick={handleChange} className={userInput.rating.value >= 7 ? 'active' : 'disabled'}>⭐</button>
                        <button name='rating' value={8} onClick={handleChange} className={userInput.rating.value >= 8 ? 'active' : 'disabled'}>⭐</button>
                        <button name='rating' value={9} onClick={handleChange} className={userInput.rating.value >= 9 ? 'active' : 'disabled'}>⭐</button>
                        <button name='rating' value={10} onClick={handleChange} className={userInput.rating.value >= 10 ? 'active' : 'disabled'}>⭐</button>
                    </div>
                    <textarea rows='10' name='review' className='create-post-review' value={userInput.review.value} onChange={handleChange} placeholder='Escreva sua opinião'></textarea>
                    <div className='create-post-submit'>
                        <select name="privacy" value={userInput.privacy.value} onChange={handleChange}>
                            <option value='' disabled></option>
                            <option value={'PUBLICO'}>PÚBLICO</option>
                            <option value={'PRIVADO'}>PRIVADO</option>
                        </select>
                        <button>Postar</button>
                    </div>
                </form>
                <img src={logo} alt='crescinema logo' />
            </div>

            {posts ?
                <div>
                    <PostList setPage={setPage} posts={posts} loggedUser={loggedUser} setReload={setReload} />
                    <div className='change-page-buttons'>
                        <button onClick={handlePreviousPage} className={checkFirstPage() ? 'disabled' : null}>{'<'}</button>
                        <button onClick={handleNextPage} className={checkLastPage() ? 'disabled' : null}>{'>'}</button>
                    </div>
                </div>
                : null}
        </div>
    )
}