TRUNCATE TABLE actions CASCADE;
TRUNCATE TABLE conditions CASCADE;
TRUNCATE TABLE devices CASCADE;
TRUNCATE TABLE scenarios CASCADE;

INSERT INTO scenarios (hub_id, name) VALUES
    ('TestHubId', 'Включить свет, если движение обнаружено'),
    ('TestHubId', 'Выключить свет, если нет движения'),
    ('TestHubId', 'Задать температуру кондиционера, если жарко');

INSERT INTO devices (id, hub_id) VALUES
    ('temperature-1', 'TestHubId'),
    ('motion-1', 'TestHubId'),
    ('cooler-1', 'TestHubId'), -- Кондиционер
    ('switcher-1', 'TestHubId'); -- Выключатель

INSERT INTO conditions (type, operation, value, device_id, scenario_id) VALUES
    ('MOTION', 'EQUALS', 1, 'motion-1', (SELECT id FROM scenarios WHERE name = 'Включить свет, если движение обнаружено')),
    ('MOTION', 'EQUALS', 0, 'motion-1', (SELECT id FROM scenarios WHERE name = 'Выключить свет, если нет движения')),
    ('TEMPERATURE', 'GREATER_THAN', 25, 'temperature-1', (SELECT id FROM scenarios WHERE name = 'Задать температуру кондиционера, если жарко')); -- Температура выше 25 градусов

INSERT INTO actions (type, value, scenario_id, device_id) VALUES
    ('ACTIVATE', 1, (SELECT id FROM scenarios WHERE name = 'Включить свет, если движение обнаружено'), 'switcher-1'),
    ('DEACTIVATE', 0, (SELECT id FROM scenarios WHERE name = 'Выключить свет, если нет движения'), 'switcher-1'),
    ('SET_VALUE', 18, (SELECT id FROM scenarios WHERE name = 'Задать температуру кондиционера, если жарко'), 'cooler-1');
