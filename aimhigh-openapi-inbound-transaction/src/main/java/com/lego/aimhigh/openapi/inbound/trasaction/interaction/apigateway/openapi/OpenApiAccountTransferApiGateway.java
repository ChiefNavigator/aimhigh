package com.lego.aimhigh.openapi.inbound.trasaction.interaction.apigateway.openapi;

import com.lego.aimhigh.openapi.inbound.trasaction.config.annotation.ApiGateway;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.contant.BankCode;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.user.User;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.constant.KcdBankAccountDepositExceptionCode;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.exception.KcdBankAccountDepositException;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.model.OpenApiAccountTransferModel;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.apigateway.openapi.constant.AccountTransferConstant;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.apigateway.openapi.model.AccountTransferRequest;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.apigateway.openapi.model.AccountTransferRequestBody;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.apigateway.openapi.model.AccountTransferResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import java.util.Collections;

@ApiGateway
@Slf4j
@RequiredArgsConstructor
public class OpenApiAccountTransferApiGateway implements OpenApiAccountTransferModel {

  private final RestTemplate restTemplate;

  @Override
  public void send(BizRemitRequest bizRemitRequest, KcdBankAccount kcdBankAccount) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth("<access_token>");

    AccountTransferRequestBody accountTransferRequestBody = buildAccountTransferBody(bizRemitRequest, kcdBankAccount);
    HttpEntity<AccountTransferRequestBody> requestEntity = new HttpEntity<>(accountTransferRequestBody, headers);
    ResponseEntity<AccountTransferResponse> responseEntity = null;

    try {
      responseEntity = restTemplate.exchange(
        AccountTransferConstant.OPENAPI_ACCOUNT_TRANSFER_URL,
        HttpMethod.POST,
        requestEntity,
        AccountTransferResponse.class
      );
    } catch (Exception ex) {
      log.error("exception on {}", AccountTransferConstant.OPENAPI_ACCOUNT_TRANSFER_URL, ex);
    }

    if (isFailedAccountTransfer(responseEntity)) {
      log.error("openapi account transfer fail. bizRemitRequsetId : {}", bizRemitRequest.getId());
      throw new KcdBankAccountDepositException(KcdBankAccountDepositExceptionCode.OPEN_API_ACCOUNT_TRANSFER_FAIL);
    }
  }

  private boolean isFailedAccountTransfer(ResponseEntity<AccountTransferResponse> responseEntity) {
    if (responseEntity == null || responseEntity.getBody() == null) {
      return true;
    }

    AccountTransferResponse response = responseEntity.getBody();
    if (response.getRsp_code() == null) {
      return true;
    }

    String rspCode = response.getRsp_code();
    int length = rspCode.length();
    if (length < 3) {
      return true;
    }

    return AccountTransferConstant.SUCCESS.equals(rspCode.substring(length - 3, length));
  }

  private AccountTransferRequestBody buildAccountTransferBody(
    BizRemitRequest bizRemitRequest,
    KcdBankAccount kcdBankAccount
  ) {


    final String kcdBankAccountNumber = kcdBankAccount.getAccountNumber();
    final User user = kcdBankAccount.getUser();
    final String username = user.getName();

    AccountTransferRequest request = AccountTransferRequest.builder()
      .tran_no(String.valueOf(bizRemitRequest.getId()))
      .bank_tran_id("은행거래고유번호")
      .bank_code_std(BankCode.KOREA_BANK)
      .account_num(kcdBankAccountNumber)
      .account_seq(null)
      .account_holder_name(username)
      .print_content("BizBankAccount 입금")
      .tran_amt(String.valueOf(bizRemitRequest.getAmount()))
      .req_client_name(username)
      .req_client_bank_code(null)
      .req_client_account_num(null)
      .req_client_fintech_use_num(null)
      .req_client_num(String.valueOf(user.getId()))
      .transfer_purpose(AccountTransferConstant.TRANSFER_PURPOSE)
      .recv_bank_tran_id(null)
      .cms_num(null)
      .build();


    return AccountTransferRequestBody.builder()
      .cntr_account_type(AccountTransferConstant.CNTR_ACCOUNT_TYP)
      .cntr_account_num(kcdBankAccountNumber)
      .wd_pass_phrase("790d56ed........821a69")
      .wd_print_content("KcdBankAccount 계좌 이체")
      .name_check_option(AccountTransferConstant.NAME_CHECK_OPTION)
      .sub_frnc_name(null)
      .sub_frnc_num(null)
      .sub_frnc_business_num(null)
      .tran_dtime(LocalDateTime.now().format(AccountTransferConstant.TRANS_DTIME_FORMAT))
      .req_cnt(AccountTransferConstant.REQ_CNT)
      .req_list(Collections.singletonList(request))
      .build();
  }
}