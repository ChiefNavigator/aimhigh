package com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.user.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class KcdBankAccount {
  @NonNull private Long id;
  @NonNull private String accountNumber;
  @NonNull private User user;
  @NonNull private Long amount;
}
