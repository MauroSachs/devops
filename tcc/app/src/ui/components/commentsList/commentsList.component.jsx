import './commentsList.css'
export function CommentsList({comments}) {

    return (
        <div className='comments-list-container'>
            {comments.map(comment => 
            <div className='comment-card' id={comment.id}>
                <h3>{comment.autorApelido}   comentou:</h3>
                <p>{comment.texto}</p>
            </div>
            )}
        </div>
    )
}