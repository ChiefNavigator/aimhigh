package com.lego.aimhigh.channel.interaction.apigateway.aimhigh.openapi.transaction;

import com.lego.aimhigh.channel.config.annotation.ApiGateway;
import com.lego.aimhigh.channel.domain.constant.StatusCode;
import com.lego.aimhigh.channel.domain.entity.bizremit.BizRemit;
import com.lego.aimhigh.channel.domain.usecase.bizremit.constant.BizRemitExceptionCode;
import com.lego.aimhigh.channel.domain.usecase.bizremit.exception.BizRemitException;
import com.lego.aimhigh.channel.domain.usecase.bizremit.model.BizRemitModel;
import com.lego.aimhigh.channel.interaction.apigateway.aimhigh.openapi.transaction.model.BizRemitRequestBody;
import com.lego.aimhigh.channel.interaction.apigateway.model.ResultVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@ApiGateway
@Slf4j
@RequiredArgsConstructor
public class AimHighOpenApiTransactionApiGateway implements BizRemitModel {

  private static final String BASE_URL = "http://localhost:8080";
  private static final String CREATE_BIZ_REMIT_REQUEST_URL = "/openapi-transaction/api/biz-remit-request/v1";
  private static final String DEPOSIT_TO_KCD_BANK_ACCOUNT_URL = "/openapi-transaction/api/biz-remit-request/v1/{bizRemitRequestId}/account-deposit";
  private static final String WITHDRAWAL_FROM_KCD_BANK_ACCOUNT_URL = "/openapi-transaction/api/biz-remit-request/v1/{bizRemitRequestId}/account-withdrawal";

  private final RestTemplate restTemplate;

  @Override
  public Long createBizRemitRequest(BizRemit bizRemit) {
    HttpHeaders headers = createHeaders();
    BizRemitRequestBody requestBody = BizRemitRequestBody.builder()
      .bankTransactionId(bizRemit.getBankTransactionId())
      .userId(bizRemit.getUserId())
      .userKcdBankAccountId(bizRemit.getUserKcdBankAccountId())
      .requestDate(bizRemit.getRequestDate())
      .fromAccountId(bizRemit.getFromAccountId())
      .toAccountId(bizRemit.getToAccountId())
      .amount(bizRemit.getAmount())
      .build();

    HttpEntity<BizRemitRequestBody> requestEntity = new HttpEntity<>(requestBody, headers);
    ResponseEntity<ResultVo<Long>> responseEntity = null;

    String url = UriComponentsBuilder.fromUriString(BASE_URL).path(CREATE_BIZ_REMIT_REQUEST_URL).toUriString();
    try {
      responseEntity = restTemplate.exchange(
        url,
        HttpMethod.POST,
        requestEntity,
        new ParameterizedTypeReference<>() {
        }
      );
    } catch (Exception ex) {
      log.error("BizRemitFail Cause: createBizRemitRequest API has failed. bankTransactionId: {}", bizRemit.getBankTransactionId());
    }

    if (isFailedCreateBizRemitRequest(responseEntity)) {
      log.error("BizRemitFail Cause: createBizRemitRequest API has failed. bankTransactionId: {}", bizRemit.getBankTransactionId());
      throw new BizRemitException(BizRemitExceptionCode.CREATE_BIZ_REMIT_REQUEST, bizRemit.getBankTransactionId());
    }

    return responseEntity.getBody().getData();
  }

  private boolean isFailedCreateBizRemitRequest(ResponseEntity<ResultVo<Long>> responseEntity) {
    if (responseEntity == null || HttpStatus.OK != responseEntity.getStatusCode() || responseEntity.getBody() == null) {
      return true;
    }

    if (!StatusCode.SUCCESS.equals(responseEntity.getBody().getStatusCode())) {
      return true;
    }

    return responseEntity.getBody().getData() == null;
  }

  @Override
  public void depositToKcdBankAccount(BizRemit bizRemit, Long bizRemitRequestId) {
    HttpHeaders headers = createHeaders();
    BizRemitRequestBody requestBody = BizRemitRequestBody.builder()
      .bankTransactionId(bizRemit.getBankTransactionId())
      .userId(bizRemit.getUserId())
      .userKcdBankAccountId(bizRemit.getUserKcdBankAccountId())
      .requestDate(bizRemit.getRequestDate())
      .fromAccountId(bizRemit.getFromAccountId())
      .toAccountId(bizRemit.getToAccountId())
      .amount(bizRemit.getAmount())
      .build();

    HttpEntity<BizRemitRequestBody> requestEntity = new HttpEntity<>(requestBody, headers);
    ResponseEntity<ResultVo<Void>> responseEntity = null;

    String url = UriComponentsBuilder.fromUriString(BASE_URL)
      .path(DEPOSIT_TO_KCD_BANK_ACCOUNT_URL)
      .buildAndExpand(bizRemitRequestId)
      .toUriString();

    try {
      responseEntity = restTemplate.exchange(
        url,
        HttpMethod.POST,
        requestEntity,
        new ParameterizedTypeReference<>() {
        }
      );
    } catch (Exception ex) {
      log.error("BizRemitFail Cause: depositToKcdBankAccount API has failed. bankTransactionId: {}", bizRemit.getBankTransactionId());
    }

    if (isFailedDepositToKcdBankAccount(responseEntity)) {
      log.error("BizRemitFail Cause: depositToKcdBankAccount API has failed. bankTransactionId: {}", bizRemit.getBankTransactionId());
      throw new BizRemitException(BizRemitExceptionCode.DEPOSIT_TO_KCD_BANK_ACCOUNT, bizRemit.getBankTransactionId());
    }
  }

  private boolean isFailedDepositToKcdBankAccount(ResponseEntity<ResultVo<Void>> responseEntity) {
    if (responseEntity == null || HttpStatus.OK != responseEntity.getStatusCode() || responseEntity.getBody() == null) {
      return true;
    }

    return !StatusCode.SUCCESS.equals(responseEntity.getBody().getStatusCode());
  }


  @Override
  public void withdrawalFromKcdBankAccount(BizRemit bizRemit, Long bizRemitRequestId) {
    HttpHeaders headers = createHeaders();
    BizRemitRequestBody requestBody = BizRemitRequestBody.builder()
      .bankTransactionId(bizRemit.getBankTransactionId())
      .userId(bizRemit.getUserId())
      .userKcdBankAccountId(bizRemit.getUserKcdBankAccountId())
      .requestDate(bizRemit.getRequestDate())
      .fromAccountId(bizRemit.getFromAccountId())
      .toAccountId(bizRemit.getToAccountId())
      .amount(bizRemit.getAmount())
      .build();

    HttpEntity<BizRemitRequestBody> requestEntity = new HttpEntity<>(requestBody, headers);
    ResponseEntity<ResultVo<Void>> responseEntity = null;

    String url = UriComponentsBuilder.fromUriString(BASE_URL)
      .path(WITHDRAWAL_FROM_KCD_BANK_ACCOUNT_URL)
      .buildAndExpand(bizRemitRequestId)
      .toUriString();

    try {
      responseEntity = restTemplate.exchange(
        url,
        HttpMethod.POST,
        requestEntity,
        new ParameterizedTypeReference<>() {
        }
      );
    } catch (Exception ex) {
      log.error("BizRemitFail Cause: withdrawalFromKcdBankAccount API has failed. bankTransactionId: {}", bizRemit.getBankTransactionId());
    }

    if (isFailedWithdrawalFromKcdBankAccount(responseEntity)) {
      log.error("BizRemitFail Cause: withdrawalFromKcdBankAccount API has failed. bankTransactionId: {}", bizRemit.getBankTransactionId());
      throw new BizRemitException(BizRemitExceptionCode.WITHDRAWAL_FROM_KCD_BANK_ACCOUNT, bizRemit.getBankTransactionId());
    }
  }

  private boolean isFailedWithdrawalFromKcdBankAccount(ResponseEntity<ResultVo<Void>> responseEntity) {
    if (responseEntity == null || HttpStatus.OK != responseEntity.getStatusCode() || responseEntity.getBody() == null) {
      return true;
    }

    return !StatusCode.SUCCESS.equals(responseEntity.getBody().getStatusCode());
  }


  private HttpHeaders createHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth("<access_token>");
    return headers;
  }
}
