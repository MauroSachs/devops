import { createBrowserRouter } from 'react-router-dom'
import { MainScreen } from '../ui/screens/main/main.screen'
import { LoginScreen } from '../ui/screens/userIdentification/login.screen'
import { RegisterScreen } from '../ui/screens/userIdentification/register.screen'
import { PrivateRoute } from './private-route.component'


export const router = createBrowserRouter([
    {
        path: '/login',
        element: <LoginScreen />
    },
    {
        path: '/register',
        element: <RegisterScreen />
    },
    {
        path: '/',
        element: <PrivateRoute Screen={MainScreen} />
    }
])
