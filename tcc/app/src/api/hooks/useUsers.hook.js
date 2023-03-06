import { useEffect, useState } from "react";
import { axiosInstance } from "../user/_base/axiosInstance";

export function useUsers() {
        const [users, setUsers] = useState()
        const [error, setError] = useState()
        const [isLoading, setIsLoading] = useState(false)
        
        useEffect(() => {
            async function fetchUsers() {
            setIsLoading(true)

            try {
                const response = await axiosInstance.get('/usuarios')
                if(response.data) setUsers(response.data)
            }
            catch(error) {
                setError('Não foi possível buscar usuarios')
            }
            finally {
                setIsLoading(false)
            }
        }
        fetchUsers()
        }, [])
        
       
    return [users, isLoading, error]
}