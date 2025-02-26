//package com.challenge.services.application.service;
//
//import com.challenge.services.application.input.port.AccountService;
//import com.challenge.services.domain.Customer;
//import com.challenge.services.input.server.models.PostAccountRequest;
//import com.challenge.services.input.server.models.Status;
//import com.challenge.services.input.server.models.Type;
//import com.google.gson.Gson;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.math.BigDecimal;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class RedisSubscriber implements MessageListener {
//    private final AccountService accountService;
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        String messageString = message.toString();
//        log.info("Receiving from customer: {} ", new Gson().toJson(messageString));
//        Customer customer = new Gson().fromJson(messageString, Customer.class);
//
//        PostAccountRequest postAccountRequest = new PostAccountRequest();
//        postAccountRequest.customerId(BigDecimal.valueOf(customer.getId()));
//        postAccountRequest.type(new Type()
//                .code("A")
//                .description("AHORRO"));
//        postAccountRequest.status(new Status().code("Enable"));
//        accountService.createAccount(Mono.just(postAccountRequest));
//    }
//}
