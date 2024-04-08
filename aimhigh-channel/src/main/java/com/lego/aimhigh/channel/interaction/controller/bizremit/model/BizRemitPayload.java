package com.lego.aimhigh.channel.interaction.controller.bizremit.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BizRemitPayload {
  private String bankTransactionId;
  private Long userId;
  private Long userKcdBankAccountId;
  private LocalDateTime requestDate;
  private String fromAccountId;
  private String toAccountId;
  private Long amount;
}
