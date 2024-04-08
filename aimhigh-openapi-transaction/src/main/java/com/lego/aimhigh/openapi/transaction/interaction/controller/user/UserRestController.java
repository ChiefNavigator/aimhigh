package com.lego.aimhigh.openapi.transaction.interaction.controller.user;

import com.lego.aimhigh.openapi.transaction.domain.usecase.user.UserUseCase;
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.command.CreateUserCommand;
import com.lego.aimhigh.openapi.transaction.interaction.controller.model.ResultVo;
import com.lego.aimhigh.openapi.transaction.interaction.controller.user.model.CreateUserPayload;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {

  private final UserUseCase userUseCase;

  @PostMapping("/openapi-transaction/api/user/create/v1")
  public ResultVo<Void> createBizRemitRequest(@RequestBody CreateUserPayload payload) {
    validateCreateUserPayload(payload);

    CreateUserCommand command = CreateUserCommand.builder()
      .name(payload.getName())
      .build();
    userUseCase.createUser(command);

    return ResultVo.buildSuccess();
  }

  private void validateCreateUserPayload(CreateUserPayload payload) {
    if (payload == null
      || StringUtils.isEmpty(payload.getName())
    ) {
      throw new IllegalArgumentException("Invalid CreateUserPayload");
    }
  }
}
