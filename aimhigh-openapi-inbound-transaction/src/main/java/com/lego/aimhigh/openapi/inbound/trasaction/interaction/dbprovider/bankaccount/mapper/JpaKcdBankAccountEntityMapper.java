package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bankaccount.mapper;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccountRecord;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bankaccount.JpaKcdBankAccount;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bankaccount.JpaKcdBankAccountRecord;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.user.mapper.JpaUserMapper;

public class JpaKcdBankAccountEntityMapper {

  private JpaKcdBankAccountEntityMapper() {
  }

  public static KcdBankAccount to(JpaKcdBankAccount account) {
    KcdBankAccount kcdBankAccount = new KcdBankAccount();
    kcdBankAccount.setId(account.getId());
    kcdBankAccount.setAccountNumber(account.getAccountNumber());
    kcdBankAccount.setUser(JpaUserMapper.to(account.getUser()));
    kcdBankAccount.setAmount(account.getAmount());

    return kcdBankAccount;
  }

  public static KcdBankAccountRecord to(JpaKcdBankAccountRecord record) {
    KcdBankAccountRecord kcdBankAccountRecord = new KcdBankAccountRecord();
    kcdBankAccountRecord.setId(record.getId());
    kcdBankAccountRecord.setKcdBankAccount(to(record.getKcdBankAccount()));
    kcdBankAccountRecord.setAction(JpaKcdBankAccountActionMapper.to(record.getAction()));

    return kcdBankAccountRecord;
  }
}
