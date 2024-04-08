package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccountRecord
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.contant.KcdBankAccountAction
import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.BizRemitRequest
import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.contant.BizRemitRequestStatus
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.command.BizRemitRequestCommand
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.constant.BizRemitExceptionCode
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.exception.BizRemitException
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.CreateBizRemitRequestModel
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.CreateBizRemitRequestRecordModel
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.CreateKcdBankAccountRecordModel
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.GetBizRemitRequestModel
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.GetKcdBankAccountModel
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.GetKcdBankAccountRecordModel
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.OpenApiAccountTransferModel
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.UpdateBizRemitRequestModel
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.UpdateKcdBankAccountModel
import spock.lang.Specification

import java.time.LocalDateTime

class BizRemitUseCaseImplTest extends Specification {

    def createBizRemitRequestModel = Mock(CreateBizRemitRequestModel)
    def getBizRemitRequestModel = Mock(GetBizRemitRequestModel)
    def updateBizRemitRequestModel = Mock(UpdateBizRemitRequestModel)
    def createBizRemitRequestRecordModel = Mock(CreateBizRemitRequestRecordModel)
    def openApiAccountTransferModel = Mock(OpenApiAccountTransferModel)
    def updateKcdBankAccountModel = Mock(UpdateKcdBankAccountModel)
    def getKcdBankAccountModel = Mock(GetKcdBankAccountModel)
    def createKcdBankAccountRecordModel = Mock(CreateKcdBankAccountRecordModel)
    def getKcdBankAccountRecordModel = Mock(GetKcdBankAccountRecordModel)

    def bizRemitUseCase = new BizRemitUseCaseImpl(
            createBizRemitRequestModel,
            getBizRemitRequestModel,
            updateBizRemitRequestModel,
            createBizRemitRequestRecordModel,
            openApiAccountTransferModel,
            updateKcdBankAccountModel,
            getKcdBankAccountModel,
            createKcdBankAccountRecordModel,
            getKcdBankAccountRecordModel
    );

    def "CreateBizRemitRequest"() {
        given:
        BizRemitRequestCommand command = BizRemitRequestCommand.builder()
                .bankTransactionId("99")
                .userId(1)
                .userKcdBankAccountId(2)
                .requestDate(LocalDateTime.now())
                .fromAccountId("fromAccountId")
                .toAccountId("toAccountId")
                .amount(3000)
                .build()

        BizRemitRequest bizRemitRequest = new BizRemitRequest()
        bizRemitRequest.setId(99)

        KcdBankAccount kcdBankAccount = new KcdBankAccount()

        when:
        def result = bizRemitUseCase.createBizRemitRequest(command)

        then:
        1 * createBizRemitRequestModel.createBizRemitRequest(_) >> bizRemitRequest
        1 * createBizRemitRequestRecordModel.createBizRemitRequestRecord(
                bizRemitRequest,
                BizRemitRequestStatus.REQUEST
        )
        1 * getKcdBankAccountModel.getKcdBankAccount(command.getUserKcdBankAccountId()) >> kcdBankAccount
        1 * createBizRemitRequestRecordModel.createBizRemitRequestRecord(
                bizRemitRequest,
                BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_PENDING
        )
        1 * openApiAccountTransferModel.send(bizRemitRequest, kcdBankAccount, KcdBankAccountAction.DEPOSIT)
        result == 99
    }

    def "CreateBizRemitRequest: 이미 완료된 BizRemitRequest 인 경우"() {
        given:
        BizRemitRequestCommand command = BizRemitRequestCommand.builder()
                .bankTransactionId("99")
                .userId(1)
                .userKcdBankAccountId(2)
                .requestDate(LocalDateTime.now())
                .fromAccountId("fromAccountId")
                .toAccountId("toAccountId")
                .amount(3000)
                .build()

        BizRemitRequest bizRemitRequest = new BizRemitRequest()
        bizRemitRequest.setId(99)
        bizRemitRequest.setStatus(BizRemitRequestStatus.DONE)

        when:
        def result = bizRemitUseCase.createBizRemitRequest(command)

        then:
        1 * getBizRemitRequestModel.getNullableBizRemitRequest(command.getBankTransactionId()) >> bizRemitRequest
        result == 99
    }

    def "CreateBizRemitRequest: 생성된 BizRemitRequest 찾아 시도 횟수를 1 늘리는 경우"() {
        given:
        BizRemitRequestCommand command = BizRemitRequestCommand.builder()
                .bankTransactionId("100")
                .userId(1)
                .userKcdBankAccountId(2)
                .requestDate(LocalDateTime.now())
                .fromAccountId("fromAccountId")
                .toAccountId("toAccountId")
                .amount(3000)
                .build()

        BizRemitRequest bizRemitRequest = new BizRemitRequest()
        bizRemitRequest.setId(99)
        bizRemitRequest.setRetryCount(0)
        bizRemitRequest.setStatus(BizRemitRequestStatus.REQUEST)

        when:
        def result = bizRemitUseCase.createBizRemitRequest(command)

        then:
        1 * getBizRemitRequestModel.getNullableBizRemitRequest(command.getBankTransactionId()) >> bizRemitRequest
        1 * updateBizRemitRequestModel.updateBizRemitRequest(_) >> { BizRemitRequest updatedBizRemitRequest ->
            assert updatedBizRemitRequest.getRetryCount() == 1
        }
        result == 99
    }

    def "CreateBizRemitRequest: 생성된 BizRemitRequest 찾아 최대 시도 횟수를 넘긴 경우"() {
        given:
        BizRemitRequestCommand command = BizRemitRequestCommand.builder()
                .bankTransactionId("100")
                .userId(1)
                .userKcdBankAccountId(2)
                .requestDate(LocalDateTime.now())
                .fromAccountId("fromAccountId")
                .toAccountId("toAccountId")
                .amount(3000)
                .build()

        BizRemitRequest bizRemitRequest = new BizRemitRequest()
        bizRemitRequest.setId(99)
        bizRemitRequest.setRetryCount(2)
        bizRemitRequest.setStatus(BizRemitRequestStatus.REQUEST)

        when:
        bizRemitUseCase.createBizRemitRequest(command)

        then:
        1 * getBizRemitRequestModel.getNullableBizRemitRequest(command.getBankTransactionId()) >> bizRemitRequest
        1 * updateBizRemitRequestModel.updateBizRemitRequest(_) >> { BizRemitRequest updatedBizRemitRequest ->
            assert updatedBizRemitRequest.getRetryCount() == 3
        }
        def ex = thrown(BizRemitException)
        assert ex.getStatusCode() == BizRemitExceptionCode.EXCEED_MAX_RETRY_COUNT.name()
    }

    def "DepositToKcdBankAccount"() {
        given:
        def bizRemitRequestId = 1L
        BizRemitRequestCommand command = BizRemitRequestCommand.builder()
                .bankTransactionId("99")
                .userId(1)
                .userKcdBankAccountId(2)
                .requestDate(LocalDateTime.now())
                .fromAccountId("fromAccountId")
                .toAccountId("toAccountId")
                .amount(3000)
                .build()

        KcdBankAccount kcdBankAccount = new KcdBankAccount();

        when:
        bizRemitUseCase.depositToKcdBankAccount(command, bizRemitRequestId)

        then:
        1 * getKcdBankAccountModel.getKcdBankAccount(command.getUserKcdBankAccountId()) >> kcdBankAccount
        1 * updateKcdBankAccountModel.amountDeposit(kcdBankAccount, command.getAmount(), command.getUserId())
        1 * createKcdBankAccountRecordModel.createKcdBankAccountRecord(
                kcdBankAccount,
                command.getAmount(),
                KcdBankAccountAction.DEPOSIT,
                command.getUserId(),
                command.getBankTransactionId()
        )
        1 * createBizRemitRequestRecordModel.createBizRemitRequestRecord(
                getBizRemitRequestModel.getBizRemitRequest(bizRemitRequestId),
                BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_COMPLETED
        )
    }

    def "DepositToKcdBankAccount: 이미 KCD 은행 입금 처리된 경우"() {
        given:
        def bizRemitRequestId = 1L
        BizRemitRequestCommand command = BizRemitRequestCommand.builder()
                .bankTransactionId("99")
                .userId(1)
                .userKcdBankAccountId(2)
                .requestDate(LocalDateTime.now())
                .fromAccountId("fromAccountId")
                .toAccountId("toAccountId")
                .amount(3000)
                .build()

        KcdBankAccountRecord kcdBankAccountRecord = new KcdBankAccountRecord();
        KcdBankAccount kcdBankAccount = new KcdBankAccount()

        when:
        bizRemitUseCase.depositToKcdBankAccount(command, bizRemitRequestId)

        then:
        1 * getKcdBankAccountRecordModel.getNullableKcdBankAccountRecord(
                command.getBankTransactionId(),
                KcdBankAccountAction.DEPOSIT
        ) >> kcdBankAccountRecord

        0 * getKcdBankAccountModel.getKcdBankAccount(command.getUserKcdBankAccountId()) >> kcdBankAccount
        0 * updateKcdBankAccountModel.amountDeposit(kcdBankAccount, command.getAmount(), command.getUserId())

        0 * createKcdBankAccountRecordModel.createKcdBankAccountRecord(
                kcdBankAccount,
                command.getAmount(),
                KcdBankAccountAction.DEPOSIT,
                command.getUserId(),
                command.getBankTransactionId()
        )

        0 * createBizRemitRequestRecordModel.createBizRemitRequestRecord(
                getBizRemitRequestModel.getBizRemitRequest(bizRemitRequestId),
                BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_COMPLETED
        )
    }

    def "WithdrawalFromKcdBankAccount"() {
        def bizRemitRequestId = 1L
        BizRemitRequestCommand command = BizRemitRequestCommand.builder()
                .bankTransactionId("99")
                .userId(1)
                .userKcdBankAccountId(2)
                .requestDate(LocalDateTime.now())
                .fromAccountId("fromAccountId")
                .toAccountId("toAccountId")
                .amount(3000)
                .build()

        KcdBankAccount kcdBankAccount = new KcdBankAccount()
        BizRemitRequest bizRemitRequest = new BizRemitRequest()

        when:
        bizRemitUseCase.withdrawalFromKcdBankAccount(command, bizRemitRequestId)

        then:
        1 * getKcdBankAccountModel.getKcdBankAccount(command.getUserKcdBankAccountId()) >> kcdBankAccount
        1 * updateKcdBankAccountModel.amountWithdrawal(kcdBankAccount, command.getAmount(), command.getUserId())

        1 * createKcdBankAccountRecordModel.createKcdBankAccountRecord(
                kcdBankAccount,
                command.getAmount(),
                KcdBankAccountAction.WITHDRAWAL,
                command.getUserId(),
                command.getBankTransactionId()
        )

        1 * createBizRemitRequestRecordModel.createBizRemitRequestRecord(
                getBizRemitRequestModel.getBizRemitRequest(bizRemitRequestId),
                BizRemitRequestStatus.KCD_ACCOUNT_WITHDRAWAL_COMPLETED
        )
        1 * createBizRemitRequestRecordModel.createBizRemitRequestRecord(
                getBizRemitRequestModel.getBizRemitRequest(bizRemitRequestId),
                BizRemitRequestStatus.DONE
        )

        1 * getBizRemitRequestModel.getBizRemitRequest(command.getBankTransactionId()) >> bizRemitRequest
        1 * updateBizRemitRequestModel.updateBizRemitRequest(_) >> { BizRemitRequest updatedBizRemitRequest ->
            assert updatedBizRemitRequest.getStatus() == BizRemitRequestStatus.DONE
        }

        1 * openApiAccountTransferModel.send(bizRemitRequest, kcdBankAccount, KcdBankAccountAction.WITHDRAWAL)
    }

    def "WithdrawalFromKcdBankAccount: 이미 KCD 은행 입금 처리된 경우"() {
        def bizRemitRequestId = 1L
        BizRemitRequestCommand command = BizRemitRequestCommand.builder()
                .bankTransactionId("99")
                .userId(1)
                .userKcdBankAccountId(2)
                .requestDate(LocalDateTime.now())
                .fromAccountId("fromAccountId")
                .toAccountId("toAccountId")
                .amount(3000)
                .build()

        KcdBankAccount kcdBankAccount = new KcdBankAccount()
        KcdBankAccountRecord kcdBankAccountRecord = new KcdBankAccountRecord();
        BizRemitRequest bizRemitRequest = new BizRemitRequest()


        when:
        bizRemitUseCase.withdrawalFromKcdBankAccount(command, bizRemitRequestId)

        then:
        1 * getKcdBankAccountRecordModel.getNullableKcdBankAccountRecord(
                command.getBankTransactionId(),
                KcdBankAccountAction.WITHDRAWAL
        ) >> kcdBankAccountRecord

        0 * getKcdBankAccountModel.getKcdBankAccount(command.getUserKcdBankAccountId()) >> kcdBankAccount
        0 * updateKcdBankAccountModel.amountWithdrawal(kcdBankAccount, command.getAmount(), command.getUserId())

        0 * createKcdBankAccountRecordModel.createKcdBankAccountRecord(
                kcdBankAccount,
                command.getAmount(),
                KcdBankAccountAction.WITHDRAWAL,
                command.getUserId(),
                command.getBankTransactionId()
        )

        0 * createBizRemitRequestRecordModel.createBizRemitRequestRecord(
                getBizRemitRequestModel.getBizRemitRequest(bizRemitRequestId),
                BizRemitRequestStatus.KCD_ACCOUNT_WITHDRAWAL_COMPLETED
        )
        0 * createBizRemitRequestRecordModel.createBizRemitRequestRecord(
                getBizRemitRequestModel.getBizRemitRequest(bizRemitRequestId),
                BizRemitRequestStatus.DONE
        )

        0 * getBizRemitRequestModel.getBizRemitRequest(command.getBankTransactionId()) >> bizRemitRequest
        0 * updateBizRemitRequestModel.updateBizRemitRequest(_) >> { BizRemitRequest updatedBizRemitRequest ->
            assert updatedBizRemitRequest.getStatus() == BizRemitRequestStatus.DONE
        }

        0 * openApiAccountTransferModel.send(bizRemitRequest, kcdBankAccount, KcdBankAccountAction.WITHDRAWAL)
    }
}
