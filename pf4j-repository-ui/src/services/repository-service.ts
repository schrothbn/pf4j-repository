import axios from 'axios'
import authHeader from './auth-header'

const API_URL = 'http://localhost:8080/repositories/'

class RepositoryService {
    getRepositories() {
        return axios.get(API_URL, { headers: authHeader() })
    }

    getRepository(name : string) {
        return axios.get(API_URL+name, { headers: authHeader() })
    }
    createRepository(name: string) {
        return axios.post(API_URL, { headers: authHeader(), body: JSON.stringify(name)})
    }
}

export default new RepositoryService()