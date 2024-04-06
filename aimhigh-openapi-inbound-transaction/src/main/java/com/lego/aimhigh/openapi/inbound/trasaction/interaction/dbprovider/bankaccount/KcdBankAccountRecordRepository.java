package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bankaccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KcdBankAccountRecordRepository extends JpaRepository<JpaKcdBankAccountRecord, Long> {
}
