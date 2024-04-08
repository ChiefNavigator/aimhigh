package com.lego.aimhigh.channel.interaction.controller.bizremit.exception;

import com.lego.aimhigh.channel.domain.usecase.bizremit.exception.BizRemitException;
import com.lego.aimhigh.channel.interaction.controller.bizremit.contant.BizRemitPayloadExceptionCode;
import lombok.Getter;

@Getter
public class BizRemitPayloadException extends BizRemitException {

  public BizRemitPayloadException(BizRemitPayloadExceptionCode code) {
    super(code.getStatusCode(), code.getMessage(), null);
  }
}
