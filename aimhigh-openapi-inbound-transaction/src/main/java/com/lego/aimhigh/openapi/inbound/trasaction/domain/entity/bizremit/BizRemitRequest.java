package com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.contant.BizRemitRequestStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BizRemitRequest {
  @NonNull private Long id;
  @NonNull private Long userId;
  @NonNull private Long userKcdBankAccountId;
  @NonNull private LocalDateTime requestDate;
  @NonNull private String fromAccountId;
  @NonNull private String toAccountId;
  @NonNull private Long amount;
  @NonNull private BizRemitRequestStatus status;
}
