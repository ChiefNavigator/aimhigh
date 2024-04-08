package com.lego.aimhigh.channel.interaction.apigateway.aimhigh.openapi.transaction.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BizRemitRequestBody {
  private final String bankTransactionId;
  private final Long userId;
  private final Long userKcdBankAccountId;
  private final LocalDateTime requestDate;
  private final String fromAccountId;
  private final String toAccountId;
  private final Long amount;
}
