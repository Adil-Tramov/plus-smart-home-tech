package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.client.OrderClient;
import ru.yandex.practicum.client.ShoppingStoreClient;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.product.ProductDto;
import ru.yandex.practicum.exception.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.exception.PaymentNotFoundException;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.model.PaymentStatus;
import ru.yandex.practicum.repository.PaymentRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final BigDecimal FEE = BigDecimal.valueOf(0.1);
    private static final int PRICE_SCALE = 2;

    private final ShoppingStoreClient shoppingStoreClient;
    private final OrderClient orderClient;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    @Override
    public PaymentDto createPayment(OrderDto orderDto) {
        log.debug("Создаем платеж для заказа с ID: {}", orderDto.orderId());
        Payment payment = new Payment();
        payment.setOrderId(orderDto.orderId());
        payment.setTotalPayment(orderDto.totalPrice());
        payment.setDeliveryPrice(orderDto.deliveryPrice());
        payment.setProductsPrice(orderDto.productPrice());
        payment.setFeePrice(orderDto.totalPrice()
                .subtract(orderDto.deliveryPrice())
                .subtract(orderDto.productPrice()));
        Payment savedPayment = paymentRepository.save(payment);
        log.debug("Сохранили в DB платеж: {}", savedPayment);
        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public BigDecimal calculateTotalCost(OrderDto orderDto) {
        log.debug("Рассчитываем полную стоимость заказа с ID: {}", orderDto.orderId());

        if (orderDto.deliveryPrice() == null || orderDto.productPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException(orderDto.orderId());
        }
        BigDecimal feePrice = orderDto.productPrice().multiply(FEE);
        log.debug("Рассчитали сумму НДС: {}", feePrice);
        return feePrice.add(orderDto.deliveryPrice())
                .add(orderDto.productPrice())
                .setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
    }

    @Transactional
    @Override
    public void confirmPayment(UUID paymentId) {
        log.debug("Устанавливаем признак успешной оплаты для платежа с ID: {}", paymentId);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        payment.setStatus(PaymentStatus.SUCCESS);
        orderClient.orderPayment(payment.getOrderId());
        log.debug("Установили признак успешного платежа. ID платежа: {}. ID заказа: {}", paymentId, payment.getOrderId());
    }

    @Override
    public BigDecimal calculateProductsCost(OrderDto orderDto) {
        log.debug("Рассчитываем стоимость товаров в заказе с ID: {}", orderDto.orderId());

        if (orderDto.products() == null || orderDto.products().isEmpty()) {
            log.warn("В заказе с ID: {} нет товаров", orderDto.orderId());
            return BigDecimal.ZERO;
        }

        BigDecimal productsCost = orderDto.products().entrySet().stream()
                .map(entry -> {
                    UUID productId = entry.getKey();
                    Long quantity = entry.getValue();
                    ProductDto productDto = shoppingStoreClient.getProductById(productId);
                    return productDto.price().multiply(new BigDecimal(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
        log.debug("Рассчитали стоимость товаров: {}", productsCost);
        return productsCost;
    }

    @Transactional
    @Override
    public void failPayment(UUID paymentId) {
        log.debug("Устанавливаем признак отказа в оплате с ID: {}", paymentId);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        payment.setStatus(PaymentStatus.FAILED);
        orderClient.orderPaymentFailed(payment.getOrderId());
        log.debug("Установили признак отказа в оплате. ID платежа: {}. ID заказа: {}", paymentId, payment.getOrderId());
    }
}
