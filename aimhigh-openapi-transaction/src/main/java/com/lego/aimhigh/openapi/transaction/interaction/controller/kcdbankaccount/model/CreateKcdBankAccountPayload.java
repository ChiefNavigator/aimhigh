package com.lego.aimhigh.openapi.transaction.interaction.controller.kcdbankaccount.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateKcdBankAccountPayload {
  private final String accountNumber;
  private final Long userId;
  private final Long amount;
}
