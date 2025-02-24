package com.challenge.services.infrastructure.output.adapter;

import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.infrastructure.exception.TransactionException;
import com.challenge.services.infrastructure.output.adapter.mapper.PostgreSQLRepositoryAdapterMapper;
import com.challenge.services.infrastructure.output.repository.AccountRepository;
import com.challenge.services.infrastructure.output.repository.TransactionRepository;
import com.challenge.services.infrastructure.output.repository.entity.AccountEntity;
import com.challenge.services.infrastructure.output.util.Constants;
import com.challenge.services.input.server.models.*;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.error_005_Balance_Not_Available;
import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.error_006_Type;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostgresRepositoryAdapter implements RepositoryPort {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Mono<Void> createAccount(PostAccountRequest postAccountRequest) {
        return accountRepository
                .saveAccount(PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperToAccountEntity(postAccountRequest));
    }

    @Override
    public Flux<Account> getAccountByFilter(String accountNumber) {
        if (accountNumber == null) {
            return getAccounts();
        }
        return getByAccountNumber(accountNumber);

    }

    private Flux<Account> getAccounts() {
        return accountRepository.findAllAccounts()
                .map(accountEntity -> {
                    log.info("accounts: {}", new Gson().toJson(accountEntity));
                    return accountEntity;
                })
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperToAccount);
    }

    private Flux<Account> getByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(customerEntity -> {
                    log.info("customer: {}", new Gson().toJson(customerEntity));
                    return customerEntity;
                })
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperToAccount)
                .flux();
    }

    @Override
    public Mono<GetAccountByIdResponse> getAccountById(String accountId) {
        return accountRepository.findByAccountId(accountId)
                .map(accountEntity -> {
                    log.info("account: {}", new Gson().toJson(accountEntity));
                    return accountEntity;
                })
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperToGetAccountById);
    }

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        return accountRepository.deleteAccount(accountId);
    }

    @Override
    public Mono<Void> putAccount(String accountId, PutAccountRequest putAccountRequest) {
        return accountRepository.updateAccount(
                        accountId,
                        PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperPutAccountToAccountEntity(putAccountRequest))
                .doOnSuccess(response -> log.info("<---| putAccount finished successfully"))
                .doOnError(error -> log.error("<---| putAccount - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }

    @Override
    public Mono<Void> patchAccount(String accountId, PatchAccountRequest patchAccountRequest) {
        return accountRepository
                .updatePartialAccount(
                        accountId,
                        PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperPatchAccountToAccountEntity(patchAccountRequest))
                .doOnSuccess(response -> log.info("<---| patchAccount finished successfully"))
                .doOnError(error -> log.error("<---| patchAccount - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }

    @Override
    public Mono<Void> createTransaction(String accountNumber, PostAccountTransactionRequest postAccountTransactionRequest) {
        return accountRepository.findByAccountNumber(accountNumber)
                .flatMap(accountEntity -> processTransaction(accountEntity, postAccountTransactionRequest));
    }

    private Mono<Void> processTransaction(AccountEntity accountEntity, PostAccountTransactionRequest request) {
        BigDecimal transactionValue = BigDecimal.valueOf(Math.abs(request.getValue().doubleValue()));
        BigDecimal currentBalance = BigDecimal.valueOf(accountEntity.getInitialBalance());
        request.setValue(BigDecimal.valueOf(transactionValue.doubleValue()));

        switch (request.getType().getCode()) {
            case Constants.TYPE_DEPOSIT:
                return handleDeposit(accountEntity, transactionValue, request);
            case Constants.TYPE_WITHDRAWAL:
                return handleWithdrawal(accountEntity, transactionValue, currentBalance, request);
            default:
                return Mono.error(new TransactionException(error_006_Type));
        }
    }

    private Mono<Void> handleDeposit(AccountEntity accountEntity, BigDecimal transactionValue, PostAccountTransactionRequest request) {
        BigDecimal newBalance = BigDecimal.valueOf(accountEntity.getInitialBalance()).add(transactionValue);
        accountEntity.setInitialBalance(newBalance.doubleValue());
        return updateAccountAndSaveTransaction(accountEntity, request);
    }

    private Mono<Void> handleWithdrawal(AccountEntity accountEntity, BigDecimal transactionValue, BigDecimal currentBalance, PostAccountTransactionRequest request) {
        if (currentBalance.compareTo(transactionValue) > 0) {
            BigDecimal newBalance = currentBalance.subtract(transactionValue);
            accountEntity.setInitialBalance(newBalance.doubleValue());
            return updateAccountAndSaveTransaction(accountEntity, request);
        } else {
            return Mono.error(new TransactionException(error_005_Balance_Not_Available));
        }
    }

    private Mono<Void> updateAccountAndSaveTransaction(AccountEntity accountEntity, PostAccountTransactionRequest postAccountTransactionRequest) {
        return accountRepository.updateAccount(accountEntity.getId().toString(), accountEntity)
                .then(transactionRepository.saveTransaction(
                        PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperToTransactionEntity(postAccountTransactionRequest, accountEntity)
                ));
    }

    /*public Mono<Void> createTransaction(String accountNumber, PostAccountTransactionRequest postAccountTransactionRequest) {
        return accountRepository.findByAccountNumber(accountNumber)
                .flatMap(accountEntity -> {
                    BigDecimal transactionValue = postAccountTransactionRequest.getValue();
                    BigDecimal currentBalance = BigDecimal.valueOf(accountEntity.getInitialBalance());

                    if (postAccountTransactionRequest.getType().getCode().equals(Constants.TYPE_DEPOSIT)) {
                        BigDecimal newBalance = currentBalance.add(transactionValue);
                        accountEntity.setInitialBalance(newBalance.doubleValue());
                        return accountRepository.updateAccount(accountEntity.getId().toString(), accountEntity)
                                .then(transactionRepository
                                        .saveTransaction(PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperToTransactionEntity(postAccountTransactionRequest, accountEntity)));
                    }

                    if (postAccountTransactionRequest.getType().getCode().equals(Constants.TYPE_WITHDRAWAL)) {
                        if (currentBalance.compareTo(transactionValue) > 0) {
                            BigDecimal newBalance = currentBalance.subtract(transactionValue);
                            accountEntity.setInitialBalance(newBalance.doubleValue());
                            return accountRepository.updateAccount(accountEntity.getId().toString(), accountEntity)
                                    .then(transactionRepository
                                            .saveTransaction(PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperToTransactionEntity(postAccountTransactionRequest, accountEntity)));
                        } else {
                            return Mono.error(new TransactionException(error_005_Balance_Not_Available));
                        }
                    }
                    return Mono.error(new TransactionException(error_006_Type));
                });
    }*/

    @Override
    public Flux<Transaction> getTransactionByFilter() {
        return transactionRepository.findAll()
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperToTransaction);
    }

    @Override
    public Mono<Void> deleteTransaction(String transactionId) {
        return transactionRepository.deleteTransaction(transactionId);
    }

    @Override
    public Mono<Void> putTransaction(String transactionId, PutAccountTransactionRequest putAccountTransactionRequest) {
        return transactionRepository.updateTransaction(
                        transactionId,
                        PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperPutAccountTransactionRequestToTransactionEntity(putAccountTransactionRequest))
                .doOnSuccess(response -> log.info("<---| putTransaction finished successfully"))
                .doOnError(error -> log.error("<---| putTransaction - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }

    @Override
    public Mono<Void> patchAccountTransaction(String transactionId, PatchAccountTransactionRequest patchAccountTransactionRequest) {
        return transactionRepository
                .patchTransaction(
                        transactionId,
                        PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperPatchAccountTransactionToTransactionEntity(patchAccountTransactionRequest))
                .doOnSuccess(response -> log.info("<---| patchCustomer finished successfully"))
                .doOnError(error -> log.error("<---| patchCustomer - ERROR: An error occurred during the execution of the procedure. {}", error.getMessage()));
    }

    @Override
    public Flux<TransactionReport> getAccountTransactionReport(String accountNumber, String startDate, String endDate) {
        return transactionRepository.getTransactionReport(accountNumber, startDate, endDate)
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperToTransactionReport);
    }
}
