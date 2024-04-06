package com.lego.aimhigh.openapi.inbound.trasaction.interaction.apigateway.openapi.constant;

import java.time.format.DateTimeFormatter;

public class AccountTransferConstant {

  private AccountTransferConstant() {
  }

  public static final DateTimeFormatter TRANS_DTIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
  public static final String OPENAPI_ACCOUNT_TRANSFER_URL = "https://openapi.openbanking.or.kr/v2.0/transfer/deposit/acnt_num";
  public static final String SUCCESS = "000";
  public static final String TRANSFER_PURPOSE = "TR";
  public static final String CNTR_ACCOUNT_TYP = "N";
  public static final String NAME_CHECK_OPTION = "on";
  public static final String REQ_CNT = "1";
}
