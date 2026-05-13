package ru.yandex.practicum.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * API для расчёта, проведения оплат
 */
public interface PaymentOperations {
    /**
     * Формирование оплаты для заказа (переход в платежный шлюз)
     *
     * @param orderDto Заказ для формирования оплаты
     * @return Сформированная оплата заказа (переход в платежный шлюз)
     */
    @PostMapping
    PaymentDto createPayment(@RequestBody @Valid OrderDto orderDto);

    /**
     * Расчёт полной стоимости заказа
     *
     * @param orderDto Заказ для расчёта
     * @return Полная стоимость заказа
     */
    @PostMapping("/totalCost")
    BigDecimal calculateTotalCost(@RequestBody @Valid OrderDto orderDto);

    /**
     * Метод для эмуляции успешной оплаты в платежном шлюзе
     *
     * @param paymentId Идентификатор платежа
     */
    @PostMapping("/refund")
    void confirmPayment(@RequestBody UUID paymentId);

    /**
     * Расчёт стоимости товаров в заказе
     *
     * @param orderDto Заказ для расчёта
     * @return Стоимость товаров в заказе
     */
    @PostMapping("/productCost")
    BigDecimal calculateProductsCost(@RequestBody @Valid OrderDto orderDto);

    /**
     * Метод для эмуляции отказа в оплате платежного шлюза
     *
     * @param paymentId Идентификатор платежа
     */
    @PostMapping("/failed")
    void failPayment(@RequestBody UUID paymentId);
}
