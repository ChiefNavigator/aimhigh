package com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bankaccount;

import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bankaccount.contant.JpaKcdBankAccountAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KcdBankAccountRecordRepository extends JpaRepository<JpaKcdBankAccountRecord, Long> {

  Optional<JpaKcdBankAccountRecord> findJpaKcdBankAccountRecordByBankTransactionIdAndAction(String bankTransactionId, JpaKcdBankAccountAction action);
}
