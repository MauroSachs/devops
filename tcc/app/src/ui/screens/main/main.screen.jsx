import { useState } from 'react'
import { HomePosts } from '../../components/homePosts/homePosts.component'
import { UserCard } from '../../components/userCard/userCard.component'
import { useCurrentUser } from '../../../api/hooks/useCurrentUser.hook'
import './main.css'
import { UserList } from '../../components/userList/userList.component'
import { Requests } from '../../components/requests/requests.component'
import { UserPage } from '../../components/userPage/userPage.component'
import { EditProfile } from '../../components/editProfile/editProfile.component'
export function MainScreen() {
   
    const [page, setPage] = useState({
        value: 'home',
        prop: null
    })
    const [loggedUser] = useCurrentUser()
    
    function getCurrentPage() {
        
        switch (page.value) {
            case 'friends':
                return <UserList initialFilter={''} loggedUser={loggedUser} type={'friends'} setPage={setPage} />
            case 'requests':
                return <Requests setPage={setPage}/>
            case 'userList':
                return <UserList initialFilter={page.prop} loggedUser={loggedUser} type={'user'} setPage={setPage}/>
            case 'userPage':
                return <UserPage loggedUser={loggedUser} pageUserId={page.prop}/>
            case 'edit':
                return <EditProfile loggedUser={loggedUser}/>
            default:
                return <HomePosts loggedUser={loggedUser} setPage={setPage}/>
        }
    }
     
    if( loggedUser) {
    return (
        <div className='main-screen-container'>
            <div className='main-left-card'>
                {getCurrentPage()}
            </div>
            <UserCard setPage={setPage} page={page} loggedUser={loggedUser}/>
        </div>
    )
    }
}