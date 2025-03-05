package com.challenge.services.application.service;

import com.challenge.services.application.input.port.AccountService;
import com.challenge.services.domain.dto.Account;
import com.challenge.services.domain.dto.Customer;
import com.challenge.services.domain.dto.Status;
import com.challenge.services.domain.dto.Type;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisSubscriber implements MessageListener {
    private final AccountService accountService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messageString = message.toString();
        log.info("Receiving from customer: {} ", new Gson().toJson(messageString));
        Customer customer = new Gson().fromJson(messageString, Customer.class);

        Account account = new Account();
        account.setCustomerId(BigDecimal.valueOf(customer.getId()));

        Type type = new Type();
        type.setCode("A");
        type.setDescription("AHORRO");
        account.setType(type);

        Status status = new Status();
        status.setCode("Enable");
        account.setStatus(status);
        log.info("account: {} ", new Gson().toJson(account));

        accountService.createAccount(account).subscribe();
    }
}
