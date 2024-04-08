package com.lego.aimhigh.channel.interaction.apigateway.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor(staticName = "of")
public class ResultVo<T> {
  private final String statusCode;
  private final String message;
  private final T data;
}
