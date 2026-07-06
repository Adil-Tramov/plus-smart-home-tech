package ru.yandex.practicum.controller.payment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.controller.payment.PaymentController;

@FeignClient(name = "payment", path = "/api/v1/payment")
public interface PaymentControllerFeign extends PaymentController {
}
