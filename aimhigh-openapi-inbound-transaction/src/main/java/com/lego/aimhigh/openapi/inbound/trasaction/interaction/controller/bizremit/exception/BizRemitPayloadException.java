package com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.bizremit.exception;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.exception.BizRemitException;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.bizremit.contant.BizRemitRequestPayloadExceptionCode;
import lombok.Getter;

@Getter
public class BizRemitPayloadException extends BizRemitException {

  public BizRemitPayloadException(BizRemitRequestPayloadExceptionCode code) {
    super(code.getStatusCode(), code.getMessage());
  }
}
