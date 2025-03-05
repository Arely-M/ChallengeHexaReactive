package com.challenge.services.infrastructure.output.repository;

import com.challenge.services.infrastructure.output.repository.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerReactiveRepository extends ReactiveCrudRepository<Customer, Integer> {

}
