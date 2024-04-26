package com.concurrency.jpa.customer.payment.service;

import com.concurrency.jpa.customer.common.BaseException;
import com.concurrency.jpa.customer.common.BaseResponse;
import com.concurrency.jpa.customer.payment.dto.PaymentInitialRequestDto;
import com.concurrency.jpa.customer.payment.dto.PaymentStatusDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 외부 결제 서버에 결제 요청, 결과 확인, 결제 취소를 요청하는 부분
 */
@Service
public class PaymentServiceImpl implements PaymentService{
    private static final String paymentURI = "http://localhost:8082/payments";
    @Override
    public PaymentStatusDto pay(PaymentInitialRequestDto dto) {
        Mono<PaymentStatusDto> mono = WebClient.create()
                .post()
                .uri(getUri(""))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(PaymentStatusDto.class);
        return mono.block();
    }

    private URI getUri(String uri) {
        return URI.create(paymentURI+uri);
    }

    @Override
    public PaymentStatusDto confirm(PaymentStatusDto dto) {
//        Mono<PaymentStatusDto> mono = WebClient.create()
//                .put()
//                .uri(getUri("/confirm"))
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(dto)
//                .retrieve()
//                .bodyToMono(PaymentStatusDto.class)
//                .onErrorResume(e -> {
//                    if (e instanceof WebClientResponseException) {
//                        var responseException = (WebClientResponseException) e;
//                        if (responseException.getStatusCode().is4xxClientError()) {
//                            cancel(dto);
//                            System.out.println(e.getMessage());
//                        }
//                    }
//                    return Mono.error(e);
//                });
//        PaymentStatusDto result = mono.block();
        System.out.println("결제 결과 : "+dto);
        return dto;
    }

    @Override
    public PaymentStatusDto cancel(PaymentStatusDto dto) {
        System.out.println("결제 취소");
        return null;
    }
}