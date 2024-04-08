package com.lego.aimhigh.channel.domain.usecase.bizremit.exception;

import com.lego.aimhigh.channel.domain.usecase.bizremit.constant.BizRemitExceptionCode;
import lombok.Getter;

@Getter
public class BizRemitException extends RuntimeException {

  private final String statusCode;
  private final String message;
  private final String bizRemitRequestId;

  public BizRemitException(String statusCode, String message, String bizRemitRequestId) {
    super(message);
    this.statusCode = statusCode;
    this.message = message;
    this.bizRemitRequestId = bizRemitRequestId;
  }

  public BizRemitException(BizRemitExceptionCode code, String bizRemitRequestId) {
    super(code.getMessage());
    this.statusCode = code.getStatusCode();
    this.message = code.getMessage();
    this.bizRemitRequestId = bizRemitRequestId;
  }
}
