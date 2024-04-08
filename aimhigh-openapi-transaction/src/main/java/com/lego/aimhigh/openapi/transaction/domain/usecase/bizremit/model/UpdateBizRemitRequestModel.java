package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;

public interface UpdateBizRemitRequestModel {

  Integer increaseRetryCount(Long id, Long userId);
  void updateRequestStatusFail(Long id, Long userId);
}
