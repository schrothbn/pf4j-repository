import axios from 'axios'
import { User } from '@/types/user'
const API_URL : string = 'http://localhost:8080/auth'

class AuthService {
    login(user : string, password: string): Promise<User> {
        return axios.post(API_URL+'/login', {
            username: user,
            password: password
        }).then(response => {
            console.log(response)
            if (response.data.accessToken) {
                localStorage.setItem('user', JSON.stringify(response.data));
            }

            return response.data;
        })
    }

    logout() {
        localStorage.removeItem('user');
    }
}

export default new AuthService()