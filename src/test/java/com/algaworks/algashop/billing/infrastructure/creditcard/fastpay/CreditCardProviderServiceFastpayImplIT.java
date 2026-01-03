package com.algaworks.algashop.billing.infrastructure.creditcard.fastpay;

import com.algaworks.algashop.billing.domain.model.creditcard.LimitedCreditCard;
import com.algaworks.algashop.billing.infrastructure.AbstractFastpayIT;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@SpringBootTest
@Import(FastpayCreditCardTokenizationAPIClientConfig.class)
class CreditCardProviderServiceFastpayImplIT extends AbstractFastpayIT {

    @Autowired
    private CreditCardProviderServiceFastpayImpl creditCardProvider;

    @Autowired
    private FastpayCreditCardTokenizationAPIClient tokenizationAPIClient;

    @Test
    void shouldRegisterCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();
        Assertions.assertThat(limitedCreditCard.getGatewayCode()).isNotBlank();
    }

    @Test
    void shouldFindRegisteredCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();
        LimitedCreditCard limitedCreditCardFound = creditCardProvider.findById(limitedCreditCard.getGatewayCode()).orElseThrow();
        Assertions.assertThat(limitedCreditCardFound.getGatewayCode()).isEqualTo(limitedCreditCard.getGatewayCode());
    }

    @Test
    void shouldDeleteRegisteredCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();
        creditCardProvider.delete(limitedCreditCard.getGatewayCode());
        Optional<LimitedCreditCard> possibleCreditCard = creditCardProvider.findById(limitedCreditCard.getGatewayCode());
        Assertions.assertThat(possibleCreditCard).isEmpty();
    }
}
