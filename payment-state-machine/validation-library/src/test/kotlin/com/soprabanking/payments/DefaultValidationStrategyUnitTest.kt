package com.soprabanking.payments

import com.soprabanking.payments.domain.TestDomain
import com.soprabanking.payments.factory.ValidationFactory
import com.soprabanking.payments.strategy.DefaultValidationStrategy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier

@SpringBootTest
class DefaultValidationStrategyUnitTest(
        @Autowired val validationFactory: ValidationFactory,
        @Autowired val defaultValidationStrategy: DefaultValidationStrategy<TestDomain>) {

    @Test
    fun test1() {
        StepVerifier.create(
                validationFactory.getValidators<TestDomain>((TestDomain::class.java).typeName + "-1")
                        .collectList()
                        .flatMap {
                            defaultValidationStrategy.validate(TestDomain(), it)
                        })
                .expectNext(TestDomain("Hello World!"))
                .verifyComplete()
    }


    @SpringBootApplication
    internal class TestConfiguration
}