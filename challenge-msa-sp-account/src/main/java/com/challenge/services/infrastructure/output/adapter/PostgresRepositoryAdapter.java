package com.challenge.services.infrastructure.output.adapter;

import com.challenge.services.application.output.port.RepositoryPort;
import com.challenge.services.domain.dto.Account;
import com.challenge.services.infrastructure.exception.AccountException;
import com.challenge.services.infrastructure.output.adapter.mapper.PostgreSQLRepositoryAdapterMapper;
import com.challenge.services.infrastructure.output.repository.AccountRepository;
import com.challenge.services.input.server.models.GetAccountByIdResponse;
import com.challenge.services.input.server.models.PatchAccountRequest;
import com.challenge.services.input.server.models.PutAccountRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.challenge.services.infrastructure.input.adapter.rest.error.resolver.DefaultError.error_002_Account_Not_Fount;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostgresRepositoryAdapter implements RepositoryPort {
    private final AccountRepository accountRepository;

//    @Override
//    public Mono<Void> createAccount(PostAccountRequest postAccountRequest) {
//        PostAccountTransactionRequest transactionRequest = createInitialTransactionRequest();
//        AccountEntity accountEntity = mapToAccountEntity(postAccountRequest);
//        return saveAccountAndCreateTransaction(accountEntity, transactionRequest);
//    }
//
//    private AccountEntity mapToAccountEntity(PostAccountRequest postAccountRequest) {
//        return PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperToAccountEntity(postAccountRequest);
//    }
//
//    private PostAccountTransactionRequest createInitialTransactionRequest() {
//        PostAccountTransactionRequest request = new PostAccountTransactionRequest();
//        TransactionType type = new TransactionType();
//        request.setValue(BigDecimal.ZERO); // Use BigDecimal.ZERO for clarity
//        type.code(Constants.TYPE_DEPOSIT);
//        type.description("CREATE");
//        request.setType(type);
//        return request;
//    }
//
//    private Mono<Void> saveAccountAndCreateTransaction(AccountEntity accountEntity, PostAccountTransactionRequest transactionRequest) {
//        return accountRepository.saveAccount(accountEntity)
//                .then(createTransaction(accountEntity.getAccountNumber(), transactionRequest));
//    }

    @Override
    public Mono<Void> createAccount(Account account) {
        return accountRepository
                .saveAccount(PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperAccountToAccountEntity(account));
    }

    @Override
    public Flux<Account> getAccountByFilter(String accountNumber, String customerId) {
        if (isNull(accountNumber) && isNull(customerId)) {
            return getAccounts();
        }
        if (isNull(accountNumber)) {
            return getByCustomerId(customerId);
        }
        return getByAccountNumber(accountNumber);

    }

    private Flux<Account> getAccounts() {
        return accountRepository.findAllAccounts()
                .map(accountEntity -> {
                    log.info("accounts: {}", new Gson().toJson(accountEntity));
                    return accountEntity;
                })
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperAccountEntityToAccountDto);
    }

    private Flux<Account> getByCustomerId(String customerId) {
        return accountRepository.findAccountByCustomerId(customerId)
                .map(accountEntity -> {
                    log.info("account by customerId: {}", new Gson().toJson(accountEntity));
                    return accountEntity;
                })
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperAccountEntityToAccountDto)
                .flux();
    }


    private Flux<Account> getByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new AccountException(error_002_Account_Not_Fount)))
                .map(accountEntity -> {
                    log.info("account by number: {}", new Gson().toJson(accountEntity));
                    return accountEntity;
                })
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperAccountEntityToAccountDto)
                .flux();
    }

    @Override
    public Mono<Account> getAccountById(String accountId) {
        return accountRepository.findByAccountId(accountId)
                .map(accountEntity -> {
                    log.info("account: {}", new Gson().toJson(accountEntity));
                    return accountEntity;
                })
                .map(PostgreSQLRepositoryAdapterMapper.INSTANCE::mapperToAccountDtoById);
    }

    @Override
    public Mono<Void> deleteAccount(String accountId) {
        return accountRepository.deleteAccount(accountId);
    }

    @Override
    public Mono<Void> putAccount(String accountId, Account account) {
        return accountRepository.updateAccount(
                accountId,
                PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperPutAccountToAccountEntity(account));
    }

    @Override
    public Mono<Void> patchAccount(String accountId, Account account) {
        return accountRepository
                .updatePartialAccount(
                        accountId,
                        PostgreSQLRepositoryAdapterMapper.INSTANCE.mapperPatchAccountToAccountEntity(account));
    }
}
