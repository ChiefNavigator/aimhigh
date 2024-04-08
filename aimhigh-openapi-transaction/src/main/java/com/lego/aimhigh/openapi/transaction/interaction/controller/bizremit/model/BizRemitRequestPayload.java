package com.lego.aimhigh.openapi.transaction.interaction.controller.bizremit.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BizRemitRequestPayload {
  private final String bankTransactionId;
  private final Long userId;
  private final Long userKcdBankAccountId;
  private final LocalDateTime requestDate;
  private final String fromAccountId;
  private final String toAccountId;
  private final Long amount;
}