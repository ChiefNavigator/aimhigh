package com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount
import com.lego.aimhigh.openapi.transaction.domain.entity.user.User
import com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount.command.CreateKcdBankAccountCommand
import com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount.model.CreateKcdBankAccountModel
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.UserUseCase
import spock.lang.Specification

class KcdBankAccountUseCaseImplTest extends Specification {

    def createKcdBankAccountModel = Mock(CreateKcdBankAccountModel)
    def userUseCase = Mock(UserUseCase)

    def kcdBankAccountUseCase = new KcdBankAccountUseCaseImpl(
            createKcdBankAccountModel,
            userUseCase
    )

    def "CreateKcdBankAccount"() {
        given:
        def command = CreateKcdBankAccountCommand.builder()
                .accountNumber("accountNumber")
                .userId(1)
                .amount(3000)
                .build()

        def user = new User()
        user.setId(1)
        user.setName("name")

        def createdKcdBankAccount = new KcdBankAccount()
        createdKcdBankAccount.setId(1)
        createdKcdBankAccount.setAccountNumber("accountNumber")
        createdKcdBankAccount.setAmount(3000)
        createdKcdBankAccount.setUser(user)

        when:
        def kcdBankAccount = kcdBankAccountUseCase.createKcdBankAccount(command);

        then:
        1 * userUseCase.getUser(command.getUserId()) >> user
        1 * createKcdBankAccountModel.createKcdBankAccount(_) >> createdKcdBankAccount

        kcdBankAccount.getAccountNumber() == "accountNumber"
        kcdBankAccount.getUser().getId() == 1
        kcdBankAccount.getUser().getName() == "name"
        kcdBankAccount.getAmount() == 3000


    }
}
