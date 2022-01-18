import { Store } from 'vuex';

declare module '@vue/runtime-core' {

    interface AuthState {
        loggedIn: boolean,
        user: object
    }

    interface ComponentCustomProperties {
        $store : Store<AuthState>
    }
}