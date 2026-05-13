package ru.yandex.practicum.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * API для расчёта, проведения доставки
 */
public interface DeliveryOperations {
    /**
     * Создать новую доставку в БД
     * @param deliveryDto Доставка для сохранения
     * @return Указанная заявка с присвоенным идентификатором
     */
    @PutMapping
    DeliveryDto createDelivery(@RequestBody @Valid DeliveryDto deliveryDto);

    /**
     * Эмуляция успешной доставки товара
     * @param orderId Идентификатор заказа
     */
    @PostMapping("/successful")
    void successDelivery(@RequestBody @NotNull UUID orderId);

    /**
     * Эмуляция получения товара в доставку
     * @param orderId Идентификатор заказа
     */
    @PostMapping("/picked")
    void pickDelivery(@RequestBody @NotNull UUID orderId);

    /**
     * Эмуляция неудачного вручения товара
     * @param orderId Идентификатор заказа
     */
    @PostMapping("/failed")
    void failedDelivery(@RequestBody @NotNull UUID orderId);

    /**
     * Расчёт полной стоимости доставки заказа
     * @param orderDto Заказ для расчёта
     * @return Полная стоимость доставки заказа
     */
    @PostMapping("/cost")
    BigDecimal calculateCost(@RequestBody @Valid OrderDto orderDto);
}
