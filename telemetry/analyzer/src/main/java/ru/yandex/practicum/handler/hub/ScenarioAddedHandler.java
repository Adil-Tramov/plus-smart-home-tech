package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedAvro;
import ru.yandex.practicum.mapper.DeviceActionMapper;
import ru.yandex.practicum.mapper.ScenarioConditionMapper;
import ru.yandex.practicum.model.DeviceAction;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.model.ScenarioCondition;
import ru.yandex.practicum.repository.ScenarioRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScenarioAddedHandler implements HubEventHandler {
    private final ScenarioRepository scenarioRepository;
    private final DeviceActionMapper deviceActionMapper;
    private final ScenarioConditionMapper scenarioConditionMapper;

    @Override
    public void handle(Object payload, String hubId) {
        ScenarioAddedAvro scenarioAddedAvro = (ScenarioAddedAvro) payload;

        String scenarioName = scenarioAddedAvro.getName();
        Optional<Scenario> optionalScenario = scenarioRepository.findByHubIdAndName(hubId, scenarioName);

        Scenario scenario = new Scenario();
        optionalScenario.ifPresent(value -> scenario.setId(value.getId()));
        scenario.setHubId(hubId);
        scenario.setName(scenarioName);

        Set<DeviceAction> deviceActions = deviceActionMapper
                .toActions(new HashSet<>(scenarioAddedAvro.getActions()), scenario);
        scenario.setActions(deviceActions);
        Set<ScenarioCondition> conditions = scenarioConditionMapper
                .toConditions(new HashSet<>(scenarioAddedAvro.getConditions()), scenario);
        scenario.setConditions(conditions);

        scenarioRepository.save(scenario);
    }

    @Override
    public Class<? extends SpecificRecordBase> getPayloadType() {
        return ScenarioAddedAvro.class;
    }
}
