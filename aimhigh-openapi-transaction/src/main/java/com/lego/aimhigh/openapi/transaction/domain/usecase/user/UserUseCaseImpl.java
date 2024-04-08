package com.lego.aimhigh.openapi.transaction.domain.usecase.user;

import com.lego.aimhigh.openapi.transaction.domain.entity.user.User;
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.command.CreateUserCommand;
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.model.CreateUserModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.model.GetUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

  private final CreateUserModel createUserModel;
  private final GetUserModel getUserModel;

  @Override
  @Transactional
  public User createUser(CreateUserCommand command) {
    User user = new User();
    user.setName(command.getName());

    return createUserModel.createUser(user);
  }

  @Override
  public User getUser(Long userId) {
    return getUserModel.getUser(userId);
  }
}
