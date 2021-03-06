package net.metro.iot.monitoring.iotMonitoring.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.metro.iot.monitoring.iotMonitoring.dao.SensorValueDao;
import net.metro.iot.monitoring.iotMonitoring.domain.SensorValue;
import net.metro.iot.monitoring.iotMonitoring.dto.SensorValueDto;
import net.metro.iot.monitoring.iotMonitoring.util.UnitsEnum;

@Service
public class SensorValueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorValueService.class);

    private final SensorValueDao sensorValueDao;

    @Autowired
    public SensorValueService(SensorValueDao sensorValueDao) {
        this.sensorValueDao = sensorValueDao;
    }

    public void save(SensorValueDto sensorValueDto) {
        Field[] fields = SensorValueDto.class.getDeclaredFields();
        LOGGER.info(fields.toString());
        Arrays.asList(fields).stream().filter(filter -> !filter.getName().equals("deviceId")).forEach(field -> {
            field.setAccessible(true);
            String value = "";
            try {
                value = (String) field.get(sensorValueDto);
                LOGGER.info(value);

                if (Objects.nonNull(value)) {
                    SensorValue sensorValue = new SensorValue(sensorValueDto.getDeviceId(), value, new Date(), UnitsEnum.getUnit(field.getName().toUpperCase()),
                            field.getName());
                    sensorValueDao.save(sensorValue);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                LOGGER.info(e.toString());
            }

        });
    }

}
