package com.kchandrakant.payments.action;

import com.kchandrakant.payments.domain.Message;
import com.kchandrakant.payments.factory.ValidationFactory;
import com.kchandrakant.payments.producer.PaymentEventProducer;
import com.kchandrakant.payments.strategy.ValidationStrategy;
import com.kchandrakant.payments.domain.Message;
import com.sapient.payments.factory.ValidationFactory;
import com.kchandrakant.payments.producer.PaymentEventProducer;
import com.sapient.payments.strategy.ValidationStrategy;
import com.kchandrakant.payments.domain.PaymentObject;
import com.sapient.payments.domain.Transition;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public abstract class BaseAction implements Action<PaymentObject> {

    @Autowired
    ValidationFactory validationFactory;

    @Autowired
    ValidationStrategy<PaymentObject> validationStrategy;

    @Autowired
    PaymentEventProducer paymentEventProducer;

    Mono<PaymentObject> validate(PaymentObject paymentObject, Transition<PaymentObject> transition) {

        return Mono.just(paymentObject)
                .flatMap(payment -> {
                    return validationFactory.<PaymentObject>getValidators(
                            PaymentObject.class.getName()
                                    + "-" + transition.getId())
                            .collectList()
                            .flatMap(controls -> validationStrategy.validate(payment, controls));
                });
    }

    Mono<PaymentObject> post(PaymentObject paymentObject, Transition<PaymentObject> transition) {

        return Mono.just(paymentObject).doOnNext(payment -> paymentEventProducer.generate(new Message(paymentObject, transition)));

    }

}
