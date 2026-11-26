package com.algaworks.algashop.billing.infrastructure.listener;

import com.algaworks.algashop.billing.application.invoice.event.InvoiceIntegrationEventPublisher;
import com.algaworks.algashop.billing.application.invoice.event.InvoiceIssuedIntegrationEvent;
import com.algaworks.algashop.billing.application.utility.Mapper;
import com.algaworks.algashop.billing.domain.model.invoice.InvoiceCanceledEvent;
import com.algaworks.algashop.billing.domain.model.invoice.InvoiceIssuedEvent;
import com.algaworks.algashop.billing.domain.model.invoice.InvoicePaidEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class InvoiceEventListener {

    private final Mapper mapper;
    private final InvoiceIntegrationEventPublisher invoiceIntegrationEventPublisher;

    @TransactionalEventListener
    public void listen(InvoiceIssuedEvent event) {
        invoiceIntegrationEventPublisher.send(mapper.convert(event, InvoiceIssuedIntegrationEvent.class));
    }

    @EventListener
    public void listen(InvoiceCanceledEvent event) {
        log.info("Invoice canceled: {}", event.getInvoiceId());
    }

    @EventListener
    public void listen(InvoicePaidEvent event) {
        log.info("Invoice paid: {}", event.getInvoiceId());
    }
}
