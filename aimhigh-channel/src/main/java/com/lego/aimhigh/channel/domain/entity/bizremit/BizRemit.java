package com.lego.aimhigh.channel.domain.entity.bizremit;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BizRemit {
  @NonNull private String bankTransactionId;
  @NonNull private Long userId;
  @NonNull private Long userKcdBankAccountId;
  @NonNull private LocalDateTime requestDate;
  @NonNull private String fromAccountId;
  @NonNull private String toAccountId;
  @NonNull private Long amount;
}
