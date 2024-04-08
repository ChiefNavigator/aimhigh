package com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.contant.KcdBankAccountAction;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class KcdBankAccountRecord {
  private Long id;
  @NonNull private String bankTransactionId;
  @NonNull private KcdBankAccount kcdBankAccount;
  @NonNull private KcdBankAccountAction action;
  @NonNull private Long amount;
}
