package com.challenge.services.infrastructure.output.repository;

import com.challenge.services.infrastructure.output.repository.entity.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountReactiveRepository extends ReactiveCrudRepository<Account, Integer> {

    @Query("SELECT c.* FROM account c WHERE c.accountnumber = :accountnumber")
    Mono<Account> findByAccountNumber(@Param("accountnumber") String accountNumber);
}
