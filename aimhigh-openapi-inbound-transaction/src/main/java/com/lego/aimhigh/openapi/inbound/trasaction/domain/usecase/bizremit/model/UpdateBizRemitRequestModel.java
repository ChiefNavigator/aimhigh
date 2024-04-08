package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.model;

public interface UpdateBizRemitRequestModel {

  Integer increaseRetryCount(Long id, Long userId);
  void updateRequestStatusFail(Long id, Long userId);
}
