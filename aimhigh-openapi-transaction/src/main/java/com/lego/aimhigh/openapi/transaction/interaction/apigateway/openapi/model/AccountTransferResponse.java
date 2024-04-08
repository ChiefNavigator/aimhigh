package com.lego.aimhigh.openapi.transaction.interaction.apigateway.openapi.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AccountTransferResponse {
  private final String api_tran_id;
  private final String api_tran_dtm;
  private final String rsp_code;
  private final String rsp_message;
  private final String wd_bank_code_std;
  private final String wd_bank_code_sub;
  private final String wd_bank_name;
  private final String wd_account_num_masked;
  private final String wd_print_content;
  private final String wd_account_holder_name;
  private final String res_cnt;
  private final List<AccountTransferResult> res_list;
}
