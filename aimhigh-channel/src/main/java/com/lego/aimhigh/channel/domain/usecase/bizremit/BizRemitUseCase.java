package com.lego.aimhigh.channel.domain.usecase.bizremit;

import com.lego.aimhigh.channel.domain.usecase.bizremit.command.BizRemitCommand;

public interface BizRemitUseCase {
  void remit(BizRemitCommand command);
}
