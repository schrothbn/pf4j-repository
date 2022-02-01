import {defineStore} from 'pinia'
import { Repository } from '@/types/repository'
import repositoryService from '@/services/repository-service'


export interface RepositoryState {
    repositories:  Repository[],
    currentRepository: Repository|null|undefined
}
const initialState: RepositoryState =     {
            repositories: [],
            currentRepository: null 
        } 


const useRepositoryStore = defineStore({
    id: 'repositoryStore',
    state: () => ( initialState ),
    actions: {
        fetch() {
           return repositoryService.getRepositories().then(
               (repos) => {
                   this.repositories = repos.data
                },
               (error) => {
                   throw error;
               } 
           )
        },
        create(name: string) {
            return repositoryService.createRepository(name).then(
                (repo) => {
                    this.repositories.push(repo);
                },
                (error) => {
                    throw error; 
                }
            );

        }

    }

})
export default useRepositoryStore