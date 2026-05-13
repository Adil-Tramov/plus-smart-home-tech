package ru.yandex.practicum.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

/**
 * API для работы с заказами
 */
public interface OrderOperations {
    /**
     * Получить заказы пользователя
     *
     * @param username Имя пользователя
     * @return Список всех заказов пользователя
     */
    @GetMapping
    List<OrderDto> getOrders(@RequestParam String username);

    /**
     * Создать новый заказ в системе
     *
     * @param request Запрос на создание заказа
     * @return Оформленный заказ пользователя
     */
    @PutMapping
    OrderDto addOrder(@RequestParam String username, @RequestBody @Valid CreateNewOrderRequest request);

    /**
     * Возврат заказа
     *
     * @param request Запрос на возврат заказа
     * @return Заказ пользователя после сборки
     */
    @PostMapping("/return")
    OrderDto returnOrder(@RequestBody @Valid ProductReturnRequest request);

    /**
     * Оплата заказа
     *
     * @param orderId Идентификатор заказа
     * @return Заказ пользователя после оплаты
     */
    @PostMapping("/payment")
    OrderDto orderPayment(@RequestBody UUID orderId);

    /**
     * Оплата заказа произошла с ошибкой
     *
     * @param orderId Идентификатор заказа
     * @return Заказ пользователя после ошибки оплаты
     */
    @PostMapping("/payment/failed")
    OrderDto orderPaymentFailed(@RequestBody UUID orderId);

    /**
     * Доставка заказа
     *
     * @param orderId Идентификатор заказа
     * @return Заказ пользователя после доставки
     */
    @PostMapping("/delivery")
    OrderDto orderDelivered(@RequestBody UUID orderId);

    /**
     * Доставка заказа произошла с ошибкой
     *
     * @param orderId Идентификатор заказа
     * @return Заказ пользователя после ошибки доставки
     */
    @PostMapping("/delivery/failed")
    OrderDto orderDeliveryFailed(@RequestBody UUID orderId);

    /**
     * Завершение заказа
     *
     * @param orderId Идентификатор заказа
     * @return Заказ пользователя после всех стадий и завершенный
     */
    @PostMapping("/completed")
    OrderDto orderCompleted(@RequestBody UUID orderId);

    /**
     * Расчёт стоимости заказа
     *
     * @param orderId Идентификатор заказа
     * @return Заказ пользователя с расчётом общей стоимости
     */
    @PostMapping("/calculate/total")
    OrderDto calculateTotalPrice(@RequestBody UUID orderId);

    /**
     * Расчёт стоимости доставки заказа
     *
     * @param orderId Идентификатор заказа
     * @return Заказ пользователя с расчётом доставки
     */
    @PostMapping("calculate/delivery")
    OrderDto calculateDeliveryPrice(@RequestBody UUID orderId);

    /**
     * Сборка заказа завершена
     *
     * @param orderId Идентификатор заказа
     * @return Заказ пользователя после сборки
     */
    @PostMapping("/assembly")
    OrderDto orderAssemblyCompleted(@RequestBody UUID orderId);

    /**
     * Сборка заказа произошла с ошибкой
     *
     * @param orderId Идентификатор заказа
     * @return Заказ пользователя после ошибки сборки
     */
    @PostMapping("/assembly/failed")
    OrderDto orderAssemblyFailed(@RequestBody UUID orderId);
}
