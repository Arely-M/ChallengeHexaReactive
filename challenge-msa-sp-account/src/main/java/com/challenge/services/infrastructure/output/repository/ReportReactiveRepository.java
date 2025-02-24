package com.challenge.services.infrastructure.output.repository;

import com.challenge.services.infrastructure.output.repository.entity.RepostEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ReportReactiveRepository extends ReactiveCrudRepository<RepostEntity, String> {

    @Query("SELECT t.date, c.name, a.accountNumber, a.accountType, a.initialBalance, a.status, t.value, t.balance\n" +
            "FROM public.transactions t\n" +
            "INNER JOIN account a ON a.accountid = t.accountid\n" +
            "INNER JOIN customer c ON c.customerid = a.customerid\n" +
            "WHERE a.accountNumber = :accountnumber\n" +
            "AND t.date BETWEEN :startdate AND :enddate")
    Flux<RepostEntity> findReport(@Param("accountnumber") String accountNumber, @Param("startdate") String startDate, @Param("enddate") String endDate);
}
