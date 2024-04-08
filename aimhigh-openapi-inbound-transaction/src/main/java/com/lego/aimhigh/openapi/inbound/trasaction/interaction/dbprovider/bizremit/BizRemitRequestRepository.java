package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BizRemitRequestRepository extends JpaRepository<JpaBizRemitRequest, Long> {

  Optional<JpaBizRemitRequest> findJpaBizRemitRequestByBankTransactionId(String bankTransactionId);
}
