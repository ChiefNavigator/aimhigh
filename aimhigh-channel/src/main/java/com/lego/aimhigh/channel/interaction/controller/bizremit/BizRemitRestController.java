package com.lego.aimhigh.channel.interaction.controller.bizremit;

import com.lego.aimhigh.channel.domain.usecase.bizremit.BizRemitUseCase;
import com.lego.aimhigh.channel.domain.usecase.bizremit.exception.BizRemitException;
import com.lego.aimhigh.channel.domain.usecase.bizremit.command.BizRemitCommand;
import com.lego.aimhigh.channel.interaction.controller.bizremit.contant.BizRemitPayloadExceptionCode;
import com.lego.aimhigh.channel.interaction.controller.bizremit.exception.BizRemitPayloadException;
import com.lego.aimhigh.channel.interaction.controller.bizremit.model.BizRemitPayload;
import com.lego.aimhigh.channel.interaction.controller.model.ResultVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.micrometer.common.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BizRemitRestController {

  private final BizRemitUseCase bizRemitUseCase;

  @PostMapping("/channel/api/biz-remit/v1")
  public ResultVo<Void> bizRemit(@RequestBody BizRemitPayload payload) {
    validateBizRemitPayload(payload);

    BizRemitCommand command = BizRemitCommand.builder()
      .bankTransactionId(payload.getBankTransactionId())
      .userId(payload.getUserId())
      .userKcdBankAccountId(payload.getUserKcdBankAccountId())
      .requestDate(payload.getRequestDate())
      .fromAccountId(payload.getFromAccountId())
      .toAccountId(payload.getToAccountId())
      .amount(payload.getAmount())
      .build();

    bizRemitUseCase.remit(command);
    return ResultVo.buildSuccess();
  }

  @ExceptionHandler(BizRemitException.class)
  public ResultVo<Void> handleBizRemitException(BizRemitException ex) {
    log.error("handleBizRemitException bizRemitRequestId:{}", ex.getBizRemitRequestId(), ex);
    return ResultVo.buildFail(ex.getMessage());
  }

  private void validateBizRemitPayload(BizRemitPayload payload) {
    if (payload == null
      || payload.getBankTransactionId() == null
      || payload.getUserId() == null
      || payload.getUserKcdBankAccountId() == null
      || payload.getRequestDate() == null
      || StringUtils.isEmpty(payload.getFromAccountId())
      || StringUtils.isEmpty(payload.getToAccountId())
      || payload.getAmount() == null
    ) {
      throw new BizRemitPayloadException(BizRemitPayloadExceptionCode.INVALID_PARAMETER);
    }
  }
}
