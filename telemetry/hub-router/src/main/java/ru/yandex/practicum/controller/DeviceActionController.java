package ru.yandex.practicum.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;

@Slf4j
@GrpcService
public class DeviceActionController extends HubRouterControllerGrpc.HubRouterControllerImplBase {
    @Override
    public void handleDeviceAction(DeviceActionRequest request, StreamObserver<Empty> responseObserver) {
        log.info("Получили запрос на выполнение действия для устройства с id: {}", request.getAction().getSensorId());
        log.debug("Получили запрос: {}", request);
        try {
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Ошибка при обработке запроса: {}", e.getMessage(), e);
            responseObserver.onError(new StatusRuntimeException(Status.fromThrowable(e)));
        }
    }
}
