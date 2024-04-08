package com.lego.aimhigh.openapi.transaction.interaction.apigateway.openapi.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountTransferRequest {
  private final String tran_no;
  private final String bank_tran_id;
  private final String bank_code_std;
  private final String account_num;
  private final String account_seq;
  private final String account_holder_name;
  private final String print_content;
  private final String tran_amt;
  private final String req_client_name;
  private final String req_client_bank_code;
  private final String req_client_account_num;
  private final String req_client_fintech_use_num;
  private final String req_client_num;
  private final String transfer_purpose;
  private final String recv_bank_tran_id;
  private final String cms_num;
}
