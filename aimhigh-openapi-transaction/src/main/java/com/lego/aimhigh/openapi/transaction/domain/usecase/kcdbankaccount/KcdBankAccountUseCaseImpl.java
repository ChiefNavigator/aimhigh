package com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.transaction.domain.entity.user.User;
import com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount.command.CreateKcdBankAccountCommand;
import com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount.model.CreateKcdBankAccountModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KcdBankAccountUseCaseImpl implements KcdBankAccountUseCase {

  private final CreateKcdBankAccountModel createKcdBankAccountModel;
  private final UserUseCase userUseCase;

  @Override
  public KcdBankAccount createKcdBankAccount(CreateKcdBankAccountCommand command) {
    User user = userUseCase.getUser(command.getUserId());
    KcdBankAccount kcdBankAccount = new KcdBankAccount();
    kcdBankAccount.setAccountNumber(command.getAccountNumber());
    kcdBankAccount.setUser(user);
    kcdBankAccount.setAmount(0L);

    return createKcdBankAccountModel.createKcdBankAccount(kcdBankAccount);
  }

}
