package com.lego.aimhigh.channel.domain.usecase.bizremit.model;

import com.lego.aimhigh.channel.domain.entity.bizremit.BizRemit;

public interface BizRemitModel {
  Long createBizRemitRequest(BizRemit bizRemit);
  void depositToKcdBankAccount(BizRemit bizRemit, Long bizRemitRequestId);
  void withdrawalFromKcdBankAccount(BizRemit bizRemit, Long bizRemitRequestId);
}
