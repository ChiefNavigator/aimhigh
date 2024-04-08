package com.lego.aimhigh.openapi.transaction.domain.usecase.user;

import com.lego.aimhigh.openapi.transaction.domain.entity.user.User;
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.command.CreateUserCommand;

public interface UserUseCase {

  User createUser(CreateUserCommand command);
  User getUser(Long userId);
}
