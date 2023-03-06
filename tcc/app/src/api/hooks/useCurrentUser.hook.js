import { useEffect, useState } from "react";
import useGlobalUser from "../../context/user.context";
import { axiosInstance } from "../user/_base/axiosInstance";

export function useCurrentUser() {
        const [currentUser, setCurrentUser] = useState()
        const [error, setError] = useState()
        const [isLoading, setIsLoading] = useState(false)
        const [, setUser] = useGlobalUser()
        
        useEffect(() => {
            async function fetchUser() {
            setIsLoading(true)

            try {
                const response = await axiosInstance.get('/usuarios/me')
                if(response.data) setCurrentUser(response.data)
            }
            catch(error) {
                setError('Não foi possível buscar usuario')
                setUser(null)

            }
            finally {
                setIsLoading(false)
            }
        }
        fetchUser()
        }, [])
        
       
    return [currentUser, isLoading, error]
}