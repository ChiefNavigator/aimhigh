package com.lego.aimhigh.openapi.transaction.domain.usecase.user

import com.lego.aimhigh.openapi.transaction.domain.entity.user.User
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.command.CreateUserCommand
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.model.CreateUserModel
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.model.GetUserModel
import spock.lang.Specification

class UserUseCaseImplTest extends Specification {

    def createUserModel = Mock(CreateUserModel)
    def getUserModel = Mock(GetUserModel)

    def userUseCase = new UserUseCaseImpl(
            createUserModel,
            getUserModel
    )

    def "CreateUser"() {
        given:
        User createdUser = new User()
        createdUser.setId(1L)
        createdUser.setName("name")
        createUserModel.createUser(_ as User) >> createdUser

        when:
        def user = userUseCase.createUser(
                CreateUserCommand.builder().name("name").build()
        )

        then:
        user.getId() == 1L
        user.getName() == "name"
    }

    def "GetUser"() {
        given:
        User createdUser = new User()
        createdUser.setId(1L)
        createdUser.setName("name")
        getUserModel.getUser(1L) >> createdUser

        when:
        def user = userUseCase.getUser(1L);

        then:
        user.getId() == 1L
        user.getName() == "name"
    }
}
