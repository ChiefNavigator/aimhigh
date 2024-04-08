package com.lego.aimhigh.openapi.transaction.interaction.controller.user.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserPayload {
  private final String name;
}
