package com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bankaccount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KcdBankAccountRecordRepository extends JpaRepository<JpaKcdBankAccountRecord, Long> {

  Optional<JpaKcdBankAccountRecord> findJpaKcdBankAccountRecordByBankTransactionId(String bankTransactionId);
}
