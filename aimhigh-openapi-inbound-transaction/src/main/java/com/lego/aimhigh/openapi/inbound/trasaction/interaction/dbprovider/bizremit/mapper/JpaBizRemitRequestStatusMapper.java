package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit.mapper;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.contant.BizRemitRequestStatus;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit.contant.JpaBizRemitRequestStatus;

import java.util.Map;

public class JpaBizRemitRequestStatusMapper {

  private JpaBizRemitRequestStatusMapper() {
  }

  private static final Map<JpaBizRemitRequestStatus, BizRemitRequestStatus> jpaBizRemitRequestStatusMap = Map.of(
    JpaBizRemitRequestStatus.FAIL, BizRemitRequestStatus.FAIL,
    JpaBizRemitRequestStatus.REQUEST, BizRemitRequestStatus.REQUEST,
    JpaBizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_PENDING, BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_PENDING,
    JpaBizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_COMPLETED, BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_COMPLETED,
    JpaBizRemitRequestStatus.KCD_ACCOUNT_WITHDRAWAL_COMPLETED, BizRemitRequestStatus.KCD_ACCOUNT_WITHDRAWAL_COMPLETED,
    JpaBizRemitRequestStatus.DONE, BizRemitRequestStatus.DONE
  );

  public static BizRemitRequestStatus to(JpaBizRemitRequestStatus jpaBizRemitRequestStatus) {
    return jpaBizRemitRequestStatusMap.get(jpaBizRemitRequestStatus);
  }

}
