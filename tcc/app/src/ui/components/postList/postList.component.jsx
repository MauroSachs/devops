import { useState } from 'react'
import { usePostFunctions } from '../../../api/hooks/usePostFunctions.hook'
import { CommentsList } from '../commentsList/commentsList.component'
import './postList.css'
export function PostList({ posts, loggedUser, setPage, setReload }) {
    const [comment, setComment] = useState({
        value: '', error: '', postId: ''
    })
    const [createCommentId, setCreateCommentId] = useState()
    const [showCommentsId, setShowCommentsId] = useState()
    const { commentPost, likePost, dislikePost, alterPost, deletePost } = usePostFunctions()

    function handleChange(event) {
        const { value } = event.target
        setComment(oldComment => ({ ...oldComment, value: value }))
    }
    async function handleSubmit(event) {
        event.preventDefault()
        const postId = event.target.name
        if (comment.value === '') {
            setComment(oldComment => ({ ...oldComment, error: 'Comentário não pode ser vazio' }))
        }
        else {
            await commentPost(postId, comment.value)
            setCreateCommentId(null)
            setReload(reload => !reload)
        }
    }
    function handleShowComments(postId) {
        if (showCommentsId === postId) {
            setShowCommentsId(null)
        }
        else setShowCommentsId(postId)
    }

    function handleCommentButton(postId) {
        if (createCommentId === postId) {
            setCreateCommentId(null)
        }
        else setCreateCommentId(postId)
    }

    async function handleLike(postId) {
        if (posts?.content) {
            if (posts.content.find(post => post.id === postId).curtidas.find(curtida => curtida.autorId === loggedUser.id)) {
                await dislikePost(postId)
                setReload(reload => !reload)
            }
            else {
                await likePost(postId)
                setReload(reload => !reload)
            }
        }
    }
    function getCurrentPost(postId) {
        return posts?.content?.find(post => post.id === postId)
    }

    function getLikeButtonClass(postId) {
        if (getCurrentPost(postId)?.curtidas?.find(curtida => curtida.autorId === loggedUser.id)) return 'enabled'
        return 'disabled'
    }

    function getCommentButtonClass(postId) {
        if (createCommentId === postId) {
            return 'enabled'
        }
        return 'disabled'
    }
    function getLikeAmount(postId) {
        const likes = getCurrentPost(postId)?.curtidas?.length
        if (likes === 1) return likes + ' curtida'
        else return likes + ' curtidas'
    }
    function getCreateCommentForm(postId) {
        if (createCommentId === postId) {
            return (
                <form name={postId} className='post-comment-form' onSubmit={handleSubmit}>
                    <textarea rows='5' className='create-post-comment' value={comment.value} onChange={handleChange} placeholder='Escreva sua opinião'></textarea>
                    <button>Comentar</button>
                </form>
            )
        }
    }
    function getCommentList(postId) {
        if (showCommentsId === postId) {
            return (
                <CommentsList comments={getCurrentPost(postId).comentarios} />
            )
        }
    }

    function getAlterPostButton(post) {

        if (post?.usuarioId === loggedUser.id) {
            if (post.privacidade === "PUBLICO") return <button className='post-alter'
                onClick={async () => { await alterPost(post.id, 'PRIVADO'); setReload(reload => !reload) }}>Tornar Privado</button>
            else return <button className='post-alter'
                onClick={async () => { await alterPost(post.id, 'PUBLICO'); setReload(reload => !reload) }}>Tornar Publico</button>
        }
    }
    function getDeletePostButton(post) {
        if (post?.usuarioId === loggedUser.id) {
            return <button className='post-delete' onClick={async () => { await deletePost(post.id); setReload(reload => !reload) }}>Deletar Post</button>

        }
    }
    return (
        <div className='posts-list'>
            {posts?.content?.map(post =>
                <div id={post.id} className='post'>
                    <div className='post-card'>
                        <div className='post-left-info'>
                            <h2 onClick={() => setPage({ value: 'userPage', prop: post.usuarioId })}>{post.usuarioNome}</h2>
                            <h1>{post.tituloFilme}</h1>
                            <h2>{'Nota: ' + post.notaFilme}</h2>
                        </div>
                        <p className='post-right-info'>{post.texto}</p>
                    </div>
                    <div className='post-interactions'>
                        {getCommentList(post.id)}
                        <div className='post-interactions-info'>
                            <span>{getLikeAmount(post.id)}</span>
                            {getAlterPostButton(post)}
                            {getDeletePostButton(post)}
                            <button onClick={() => handleShowComments(post.id)}> Ver Comentarios</button>
                        </div>
                        <div className='post-interacions-buttons'>
                            <button onClick={() => handleLike(post.id)} className={getLikeButtonClass(post.id)}>Curtir</button>
                            <button onClick={() => handleCommentButton(post.id)} className={getCommentButtonClass(post.id)}>Comentar</button>
                            {getCreateCommentForm(post.id)}
                        </div>

                    </div>
                </div>
            )}
        </div>
    )
}