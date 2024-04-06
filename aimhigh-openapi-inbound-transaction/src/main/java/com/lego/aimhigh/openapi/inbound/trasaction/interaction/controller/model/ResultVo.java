package com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.model;

import com.lego.aimhigh.openapi.inbound.trasaction.interaction.contant.ResultVoStatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor(staticName = "of")
public class ResultVo<T> {

  private final String statusCode;

  private final String message;

  private final T data;

  public static ResultVo<Void> buildFail(String message) {
    return ResultVo.of(ResultVoStatusCode.FAIL, message, null);
  }

  public static ResultVo<Void> buildSuccess() {
    return ResultVo.of(ResultVoStatusCode.SUCCESS, null, null);
  }
}
