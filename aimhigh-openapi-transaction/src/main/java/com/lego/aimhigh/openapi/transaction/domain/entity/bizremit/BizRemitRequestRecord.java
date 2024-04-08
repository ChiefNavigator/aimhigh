package com.lego.aimhigh.openapi.transaction.domain.entity.bizremit;

import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.contant.BizRemitRequestStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BizRemitRequestRecord {
  @NonNull private Long id;
  @NonNull private BizRemitRequest bizRemitRequest;
  @NonNull private BizRemitRequestStatus status;
}
