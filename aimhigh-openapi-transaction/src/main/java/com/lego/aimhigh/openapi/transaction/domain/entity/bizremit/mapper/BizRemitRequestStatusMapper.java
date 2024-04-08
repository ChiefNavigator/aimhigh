package com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.mapper;

import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.contant.BizRemitRequestStatus;
import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bizremit.contant.JpaBizRemitRequestStatus;

import java.util.Map;

public class BizRemitRequestStatusMapper {

  private BizRemitRequestStatusMapper() {
  }

  private static final Map<BizRemitRequestStatus, JpaBizRemitRequestStatus> bizRemitRequestStatusMap = Map.of(
    BizRemitRequestStatus.FAIL, JpaBizRemitRequestStatus.FAIL,
    BizRemitRequestStatus.REQUEST, JpaBizRemitRequestStatus.REQUEST,
    BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_PENDING, JpaBizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_PENDING,
    BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_COMPLETED, JpaBizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_COMPLETED,
    BizRemitRequestStatus.KCD_ACCOUNT_WITHDRAWAL_COMPLETED, JpaBizRemitRequestStatus.KCD_ACCOUNT_WITHDRAWAL_COMPLETED,
    BizRemitRequestStatus.DONE, JpaBizRemitRequestStatus.DONE
  );

  public static JpaBizRemitRequestStatus to(BizRemitRequestStatus bizRemitRequestStatus) {
    return bizRemitRequestStatusMap.get(bizRemitRequestStatus);
  }

}
