package com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.mapper;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.contant.KcdBankAccountAction;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bankaccount.contant.JpaKcdBankAccountAction;

import java.util.Map;

public class KcdBankAccountActionMapper {

  private KcdBankAccountActionMapper() {
  }

  private static final Map<KcdBankAccountAction, JpaKcdBankAccountAction> kcdBankAccountActionMap = Map.of(
    KcdBankAccountAction.DEPOSIT, JpaKcdBankAccountAction.DEPOSIT,
    KcdBankAccountAction.WITHDRAWAL, JpaKcdBankAccountAction.WITHDRAWAL
  );

  public static JpaKcdBankAccountAction to(KcdBankAccountAction kcdBankAccountAction) {
    return kcdBankAccountActionMap.get(kcdBankAccountAction);
  }

}
