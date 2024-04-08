package com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bankaccount;

import com.lego.aimhigh.openapi.transaction.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.transaction.entity.bankaccount.KcdBankAccountRecord;
import com.lego.aimhigh.openapi.transaction.entity.bankaccount.contant.KcdBankAccountAction;
import com.lego.aimhigh.openapi.transaction.entity.bankaccount.mapper.KcdBankAccountActionMapper;
import com.lego.aimhigh.openapi.transaction.command.BizRemitRequestCommand;
import com.lego.aimhigh.openapi.transaction.model.CreateKcdBankAccountRecordModel;
import com.lego.aimhigh.openapi.transaction.model.GetKcdBankAccountModel;
import com.lego.aimhigh.openapi.transaction.model.GetKcdBankAccountRecordModel;
import com.lego.aimhigh.openapi.transaction.model.UpdateKcdBankAccountModel;
import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bankaccount.mapper.JpaKcdBankAccountEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class KcdBankAccountDataProvider implements
  UpdateKcdBankAccountModel,
  CreateKcdBankAccountRecordModel,
  GetKcdBankAccountModel,
  GetKcdBankAccountRecordModel {

  private final KcdBankAccountRepository kcdBankAccountRepository;
  private final KcdBankAccountRecordRepository kcdBankAccountRecordRepository;

  @Override
  @Transactional
  public KcdBankAccount updateAmount(BizRemitRequestCommand command) {
    JpaKcdBankAccount jpaKcdBankAccount = getJpaKcdBankAccount(command.getUserKcdBankAccountId());
    final Long baseAmount = jpaKcdBankAccount.getAmount();
    jpaKcdBankAccount.setAmount(baseAmount + command.getAmount());
    jpaKcdBankAccount.setUpdatedBy(String.valueOf(command.getUserId()));
    jpaKcdBankAccount.setUpdatedAt(LocalDateTime.now());

    return JpaKcdBankAccountEntityMapper.to(kcdBankAccountRepository.save(jpaKcdBankAccount));
  }

  @Override
  @Transactional
  public KcdBankAccountRecord createKcdBankAccountRecord(
    KcdBankAccount kcdBankAccount,
    KcdBankAccountAction kcdBankAccountAction,
    Long userId,
    String bankTransactionId
  ) {
    JpaKcdBankAccountRecord jpaKcdBankAccountRecord = new JpaKcdBankAccountRecord();
    jpaKcdBankAccountRecord.setBankTransactionId(bankTransactionId);
    jpaKcdBankAccountRecord.setKcdBankAccount(getJpaKcdBankAccount(kcdBankAccount.getId()));
    jpaKcdBankAccountRecord.setAction(KcdBankAccountActionMapper.to(kcdBankAccountAction));
    jpaKcdBankAccountRecord.setDeleted(false);
    LocalDateTime now = LocalDateTime.now();
    jpaKcdBankAccountRecord.setCreatedBy(String.valueOf(userId));
    jpaKcdBankAccountRecord.setUpdatedBy(String.valueOf(userId));
    jpaKcdBankAccountRecord.setCreatedAt(now);
    jpaKcdBankAccountRecord.setUpdatedAt(now);

    return JpaKcdBankAccountEntityMapper.to(kcdBankAccountRecordRepository.save(jpaKcdBankAccountRecord));
  }

  private JpaKcdBankAccount getJpaKcdBankAccount(Long id) {
    return kcdBankAccountRepository.findById(id).orElseThrow();
  }

  @Override
  public KcdBankAccount getKcdBankAccount(Long id) {
    return JpaKcdBankAccountEntityMapper.to(getJpaKcdBankAccount(id));
  }

  @Override
  public KcdBankAccountRecord getKcdBankAccountRecord(String bankTransactionId) {
    return JpaKcdBankAccountEntityMapper.to(getJpaKcdBankAccountRecord(bankTransactionId));
  }

  private JpaKcdBankAccountRecord getJpaKcdBankAccountRecord(String bankTransactionId) {
    return kcdBankAccountRecordRepository.findJpaKcdBankAccountRecordByBankTransactionId(bankTransactionId).orElseThrow();
  }
}
