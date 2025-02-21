package com.challenge.services.application.output.port;

import com.challenge.services.input.server.models.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {

}
