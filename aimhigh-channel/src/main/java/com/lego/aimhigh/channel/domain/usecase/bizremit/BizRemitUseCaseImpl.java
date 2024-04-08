package com.lego.aimhigh.channel.domain.usecase.bizremit;

import com.lego.aimhigh.channel.domain.entity.bizremit.BizRemit;
import com.lego.aimhigh.channel.domain.usecase.bizremit.command.BizRemitCommand;
import com.lego.aimhigh.channel.domain.usecase.bizremit.model.BizRemitModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BizRemitUseCaseImpl implements BizRemitUseCase {

  private final BizRemitModel bizRemitModel;

  @Override
  public void remit(BizRemitCommand command) {
    BizRemit bizRemit = new BizRemit();
    bizRemit.setBankTransactionId(command.getBankTransactionId());
    bizRemit.setUserId(command.getUserId());
    bizRemit.setUserKcdBankAccountId(command.getUserKcdBankAccountId());
    bizRemit.setRequestDate(command.getRequestDate());
    bizRemit.setFromAccountId(command.getFromAccountId());
    bizRemit.setToAccountId(command.getToAccountId());
    bizRemit.setAmount(command.getAmount());


    Long bizRemitRequestId = bizRemitModel.createBizRemitRequest(bizRemit);
    bizRemitModel.depositToKcdBankAccount(bizRemit, bizRemitRequestId);
    bizRemitModel.withdrawalFromKcdBankAccount(bizRemit, bizRemitRequestId);
  }
}
