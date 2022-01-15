package de.schrotbn.pf4jrepository.domain.mappers

import de.schrotbn.pf4jrepository.domain.dto.UserView
import de.schrotbn.pf4jrepository.domain.model.User
import org.springframework.stereotype.Component

@Component
class UserViewMapper {

    fun  toUserView(user : User) : UserView {
        return UserView(user.name)
    }
}