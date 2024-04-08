package com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bankaccount;

import com.lego.aimhigh.openapi.transaction.config.annotation.DistributedLock;
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccountRecord;
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.contant.KcdBankAccountAction;
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.mapper.KcdBankAccountActionMapper;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.CreateKcdBankAccountRecordModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.GetKcdBankAccountModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.GetKcdBankAccountRecordModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.UpdateKcdBankAccountModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount.model.CreateKcdBankAccountModel;
import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bankaccount.mapper.JpaKcdBankAccountEntityMapper;
import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.user.JpaUser;
import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.user.UserRepository;
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
  GetKcdBankAccountRecordModel,
  CreateKcdBankAccountModel {

  private final KcdBankAccountRepository kcdBankAccountRepository;
  private final KcdBankAccountRecordRepository kcdBankAccountRecordRepository;
  private final UserRepository userRepository;

  @Override
  public KcdBankAccount createKcdBankAccount(KcdBankAccount kcdBankAccount) {
    JpaUser user = userRepository.findById(kcdBankAccount.getUser().getId()).orElseThrow();
    JpaKcdBankAccount jpaKcdBankAccount = new JpaKcdBankAccount();
    jpaKcdBankAccount.setAccountNumber(kcdBankAccount.getAccountNumber());
    jpaKcdBankAccount.setUser(user);
    jpaKcdBankAccount.setAmount(kcdBankAccount.getAmount());
    jpaKcdBankAccount.setDeleted(false);
    LocalDateTime now = LocalDateTime.now();
    jpaKcdBankAccount.setCreatedBy(String.valueOf(user.getId()));
    jpaKcdBankAccount.setUpdatedBy(String.valueOf(user.getId()));
    jpaKcdBankAccount.setCreatedAt(now);
    jpaKcdBankAccount.setUpdatedAt(now);

    return JpaKcdBankAccountEntityMapper.to(kcdBankAccountRepository.save(jpaKcdBankAccount));
  }


  @Override
  @DistributedLock(key = "'amount_' + #userId")
  public KcdBankAccount amountDeposit(KcdBankAccount kcdBankAccount, Long amount, Long userId) {
    JpaKcdBankAccount jpaKcdBankAccount = getJpaKcdBankAccount(kcdBankAccount.getId());
    jpaKcdBankAccount.setAmount(kcdBankAccount.getAmount() + amount);
    jpaKcdBankAccount.setUpdatedBy(String.valueOf(userId));
    jpaKcdBankAccount.setUpdatedAt(LocalDateTime.now());

    return JpaKcdBankAccountEntityMapper.to(kcdBankAccountRepository.save(jpaKcdBankAccount));
  }

  @Override
  @DistributedLock(key = "'amount_' + #userId")
  public KcdBankAccount amountWithdrawal(KcdBankAccount kcdBankAccount, Long amount, Long userId) {
    JpaKcdBankAccount jpaKcdBankAccount = getJpaKcdBankAccount(kcdBankAccount.getId());
    jpaKcdBankAccount.setAmount(kcdBankAccount.getAmount() - amount);
    jpaKcdBankAccount.setUpdatedBy(String.valueOf(userId));
    jpaKcdBankAccount.setUpdatedAt(LocalDateTime.now());

    return JpaKcdBankAccountEntityMapper.to(kcdBankAccountRepository.save(jpaKcdBankAccount));
  }

  @Override
  @Transactional
  public KcdBankAccountRecord createKcdBankAccountRecord(
    KcdBankAccount kcdBankAccount,
    Long amount,
    KcdBankAccountAction kcdBankAccountAction,
    Long userId,
    String bankTransactionId
  ) {
    JpaKcdBankAccountRecord jpaKcdBankAccountRecord = new JpaKcdBankAccountRecord();
    jpaKcdBankAccountRecord.setBankTransactionId(bankTransactionId);
    jpaKcdBankAccountRecord.setKcdBankAccount(getJpaKcdBankAccount(kcdBankAccount.getId()));
    jpaKcdBankAccountRecord.setAmount(amount);
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
  public KcdBankAccountRecord getNullableKcdBankAccountRecord(String bankTransactionId, KcdBankAccountAction action) {
    return kcdBankAccountRecordRepository
      .findJpaKcdBankAccountRecordByBankTransactionIdAndAction(
        bankTransactionId,
        KcdBankAccountActionMapper.to(action)
      )
      .map(JpaKcdBankAccountEntityMapper::to)
      .orElse(null);
  }
}
