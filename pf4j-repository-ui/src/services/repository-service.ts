import axios from 'axios'
import useAuthHeader from './auth-header'

const API_URL = 'http://localhost:8080/repositories/'

class RepositoryService {
    getRepositories() {
        return axios.get(API_URL, { headers: useAuthHeader() })
    }

    getRepository(name : string) {
        return axios.get(API_URL+name, { headers: useAuthHeader() })
    }
    createRepository(name: string) {
        return axios.post(API_URL, { headers: useAuthHeader(), body: JSON.stringify(name)})
    }
}

export default new RepositoryService()