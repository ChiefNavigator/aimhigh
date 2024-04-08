package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.exception;

import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.constant.BizRemitExceptionCode;
import lombok.Getter;

@Getter
public class BizRemitException extends RuntimeException {

  private final String statusCode;
  private final String message;
  private final Long bizRemitRequestId;

  public BizRemitException(String statusCode, String message, Long bizRemitRequestId) {
    super(message);
    this.statusCode = statusCode;
    this.message = message;
    this.bizRemitRequestId = bizRemitRequestId;
  }

  public BizRemitException(BizRemitExceptionCode code, Long bizRemitRequestId) {
    super(code.getMessage());
    this.statusCode = code.getStatusCode();
    this.message = code.getMessage();
    this.bizRemitRequestId = bizRemitRequestId;
  }
}
