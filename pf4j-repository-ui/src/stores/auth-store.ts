import authService from "@/services/auth-service";
import { defineStore } from 'pinia'
import { User } from "@/types/user";

export interface AuthState {
    loggedIn: boolean,
    user : User | null
}

const user = JSON.parse(localStorage.getItem('user') as string) as User

const initialState : AuthState = (user ?  { loggedIn: true , user }
: { loggedIn: false , user: null})

export const useAuthStore = defineStore('authStore', {
    state: () => ( initialState ),
    actions: {
        login(user: string, password: string) {
            return authService.login(user, password).then( user => {
                this.loggedIn = true
                this.user = user
                return Promise.resolve(user);
            },
            error => {
                this.loggedIn = false
                this.user = null;
                return Promise.reject(error)
            })
        },
        logout() {
            authService.logout()
            this.loggedIn = false,
            this.user = null
        }
    }
        
})
