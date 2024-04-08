package com.lego.aimhigh.openapi.transaction.domain.usecase.user.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserCommand {

  private final String name;
}
