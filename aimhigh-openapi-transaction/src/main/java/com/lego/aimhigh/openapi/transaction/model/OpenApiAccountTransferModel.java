package com.lego.aimhigh.openapi.transaction.model;

import com.lego.aimhigh.openapi.transaction.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.transaction.entity.bizremit.BizRemitRequest;

import java.util.concurrent.CompletableFuture;

public interface OpenApiAccountTransferModel {

  CompletableFuture<Void> send(BizRemitRequest bizRemitRequest, KcdBankAccount kcdBankAccount);
}
