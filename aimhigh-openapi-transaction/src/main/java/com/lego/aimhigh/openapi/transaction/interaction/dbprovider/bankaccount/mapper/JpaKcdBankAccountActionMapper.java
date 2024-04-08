package com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bankaccount.mapper;

import com.lego.aimhigh.openapi.transaction.entity.bankaccount.contant.KcdBankAccountAction;
import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bankaccount.contant.JpaKcdBankAccountAction;

import java.util.Map;

public class JpaKcdBankAccountActionMapper {

  private JpaKcdBankAccountActionMapper() {
  }

  private static final Map<JpaKcdBankAccountAction, KcdBankAccountAction> jpakcdBankAccountActionMap = Map.of(
    JpaKcdBankAccountAction.DEPOSIT, KcdBankAccountAction.DEPOSIT,
    JpaKcdBankAccountAction.WITHDRAWAL, KcdBankAccountAction.WITHDRAWAL
  );

  public static KcdBankAccountAction to(JpaKcdBankAccountAction jpaKcdBankAccountAction) {
    return jpakcdBankAccountActionMap.get(jpaKcdBankAccountAction);
  }

}
