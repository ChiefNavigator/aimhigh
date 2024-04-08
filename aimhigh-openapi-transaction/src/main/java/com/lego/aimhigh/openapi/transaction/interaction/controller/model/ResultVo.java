package com.lego.aimhigh.openapi.transaction.interaction.controller.model;

import com.lego.aimhigh.openapi.transaction.domain.constant.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor(staticName = "of")
public class ResultVo<T> {

  private final String statusCode;

  private final String message;

  private final T data;

  public static ResultVo<Void> buildFail(String message) {
    return ResultVo.of(StatusCode.FAIL, message, null);
  }

  public static <T> ResultVo<T> buildSuccess(T data) {
    return ResultVo.of(StatusCode.SUCCESS, null, data);
  }

  public static ResultVo<Void> buildSuccess() {
    return ResultVo.of(StatusCode.SUCCESS, null, null);
  }
}
