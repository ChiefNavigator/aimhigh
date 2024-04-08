package com.lego.aimhigh.openapi.transaction.interaction.controller.bizremit.exception;

import com.lego.aimhigh.openapi.transaction.exception.BizRemitException;
import com.lego.aimhigh.openapi.transaction.interaction.controller.bizremit.contant.BizRemitRequestPayloadExceptionCode;
import lombok.Getter;

@Getter
public class BizRemitPayloadException extends BizRemitException {

  public BizRemitPayloadException(BizRemitRequestPayloadExceptionCode code) {
    super(code.getStatusCode(), code.getMessage());
  }
}
