package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.model;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;

import java.util.concurrent.CompletableFuture;

public interface OpenApiAccountTransferModel {

  CompletableFuture<Void> send(BizRemitRequest bizRemitRequest, KcdBankAccount kcdBankAccount);
}
