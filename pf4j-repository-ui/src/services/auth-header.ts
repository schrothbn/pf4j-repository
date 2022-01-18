import { AxiosRequestHeaders } from 'axios';

export default function authHeader() :  AxiosRequestHeaders {
    let user = JSON.parse(localStorage.getItem('user') as string)

    if (user && user.accessToken) {
        return { Authorization: 'Bearer ' + user.accessToken }
    }

    return {}
    
}