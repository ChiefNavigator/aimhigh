package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.exception;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.constant.BizRemitExceptionCode;
import lombok.Getter;

@Getter
public class BizRemitException extends RuntimeException {

  private final String statusCode;
  private final String message;

  public BizRemitException(String statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
    this.message = message;
  }

  public BizRemitException(BizRemitExceptionCode code) {
    super(code.getMessage());
    this.statusCode = code.getStatusCode();
    this.message = code.getMessage();
  }
}
