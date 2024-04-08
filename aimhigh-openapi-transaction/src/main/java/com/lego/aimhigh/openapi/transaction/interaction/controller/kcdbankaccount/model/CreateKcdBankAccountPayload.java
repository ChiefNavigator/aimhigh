package com.lego.aimhigh.openapi.transaction.interaction.controller.kcdbankaccount.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateKcdBankAccountPayload {
  private String accountNumber;
  private Long userId;
  private Long amount;
}
