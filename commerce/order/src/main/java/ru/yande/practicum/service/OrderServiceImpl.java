package ru.yande.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yande.practicum.mapper.OrderMapper;
import ru.yande.practicum.model.Order;
import ru.yande.practicum.repository.OrderRepository;
import ru.yandex.practicum.client.DeliveryClient;
import ru.yandex.practicum.client.PaymentClient;
import ru.yandex.practicum.client.WarehouseClient;
import ru.yandex.practicum.dto.cart.BookedProductsDto;
import ru.yandex.practicum.dto.order.CreateNewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.OrderState;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.dto.warehouse.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WarehouseClient warehouseClient;
    private final PaymentClient paymentClient;
    private final DeliveryClient deliveryClient;

    @Transactional(readOnly = true)
    @Override
    public List<OrderDto> getOrders(String username) {
        log.debug("Получаем из DB все заказы пользователя {}", username);
        if (username.isEmpty()) {
            throw new NotAuthorizedUserException(username);
        }
        List<OrderDto> orderDtos = orderMapper.toOrderDto(orderRepository.findAllByUsername(username));
        log.debug("Получили из DB {} заказов пользователя {}", orderDtos.size(), username);
        return orderDtos;
    }

    @Transactional
    @Override
    public OrderDto addOrder(String username, CreateNewOrderRequest request) {
        log.debug("Сохраняем в DB новый заказ пользователя {}. ID корзины: {}",
                username, request.shoppingCart().shoppingCartId());
        if (username.isEmpty()) {
            throw new NotAuthorizedUserException(username);
        }
        Order order = orderMapper.toOrder(username, request);
        AssemblyProductsForOrderRequest assemblyRequest =
                orderMapper.toAssemblyRequest(order.getId(), request.shoppingCart().products());
        BookedProductsDto bookedProducts = warehouseClient.assemblyProducts(assemblyRequest);
        order.setDeliveryWeight(bookedProducts.deliveryWeight());
        order.setDeliveryVolume(bookedProducts.deliveryVolume());
        order.setFragile(bookedProducts.fragile());
        order = orderRepository.save(order);
        log.debug("Сохранили в DB новый заказ: {}", order);
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto returnOrder(ProductReturnRequest request) {
        log.debug("Обрабатываем возврат товаров по заказу с ID: {}", request.orderId());
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new NoOrderFoundException(request.orderId()));
        order.setState(OrderState.PRODUCT_RETURNED);
        warehouseClient.returnProductsToWarehouse(request.products());
        log.debug("Обработали возврат товаров по заказу c ID: {}. Корзина ID: {}. Статус заказа: {}",
                order.getId(), order.getShoppingCartId(), order.getState());
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto orderPayment(UUID orderId) {
        log.debug("Обрабатываем успешный платеж по заказу с ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.PAID);
        log.debug("Обработали успешную оплату заказа c ID: {}. Корзина ID: {}. Статус заказа: {}",
                order.getId(), order.getShoppingCartId(), order.getState());
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto orderPaymentFailed(UUID orderId) {
        log.debug("Обрабатываем ошибку оплаты заказа с ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.PAYMENT_FAILED);
        log.debug("Обработали ошибку оплаты заказа c ID: {}. Корзина ID: {}. Статус заказа: {}",
                order.getId(), order.getShoppingCartId(), order.getState());
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto orderDelivered(UUID orderId) {
        log.debug("Обрабатываем успешную доставку заказа с ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.DELIVERED);
        log.debug("Обработали успешную доставку заказа c ID: {}. Корзина ID: {}. Статус заказа: {}",
                order.getId(), order.getShoppingCartId(), order.getState());
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto orderDeliveryFailed(UUID orderId) {
        log.debug("Обрабатываем ошибку при доставке заказа с ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.DELIVERY_FAILED);
        log.debug("Обработали ошибку при доставке заказа c ID: {}. Корзина ID: {}. Статус заказа: {}",
                order.getId(), order.getShoppingCartId(), order.getState());
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto orderCompleted(UUID orderId) {
        log.debug("Обрабатываем завершение заказа с ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.COMPLETED);
        log.debug("Обработали завершение заказа c ID: {}. Корзина ID: {}. Статус заказа: {}",
                order.getId(), order.getShoppingCartId(), order.getState());
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto calculateTotalPrice(UUID orderId) {
        log.debug("Рассчитываем полную стоимость заказа с ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(orderId));
        BigDecimal totalPrice = paymentClient.calculateTotalCost(orderMapper.toOrderDto(order));
        order.setTotalPrice(totalPrice);
        log.debug("Рассчитали полную стоимость заказа c ID: {}. Корзина ID: {}. Полная стоимость: {}",
                order.getId(), order.getShoppingCartId(), order.getTotalPrice());
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto calculateDeliveryPrice(UUID orderId) {
        log.debug("Рассчитываем стоимость доставки заказа с ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(orderId));
        BigDecimal deliveryPrice = deliveryClient.calculateCost(orderMapper.toOrderDto(order));
        order.setDeliveryPrice(deliveryPrice);
        log.debug("Рассчитали стоимость доставки заказа c ID: {}. Корзина ID: {}. Стоимость доставки: {}",
                order.getId(), order.getShoppingCartId(), order.getDeliveryPrice());
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto orderAssemblyCompleted(UUID orderId) {
        log.debug("Обрабатываем успешное завершение сборки заказа с ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.ASSEMBLED);
        log.debug("Обработали успешное завершение сборки заказа c ID: {}. Корзина ID: {}. Статус заказа: {}",
                order.getId(), order.getShoppingCartId(), order.getState());
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    @Override
    public OrderDto orderAssemblyFailed(UUID orderId) {
        log.debug("Обрабатываем ошибку сборки заказа с ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException(orderId));
        order.setState(OrderState.ASSEMBLY_FAILED);
        log.debug("Обработали ошибку сборки заказа c ID: {}. Корзина ID: {}. Статус заказа: {}",
                order.getId(), order.getShoppingCartId(), order.getState());
        return orderMapper.toOrderDto(order);
    }
}
