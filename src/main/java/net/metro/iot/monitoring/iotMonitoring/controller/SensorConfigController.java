package net.metro.iot.monitoring.iotMonitoring.controller;

import java.util.List;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import net.metro.iot.monitoring.iotMonitoring.dto.SensorConfigDto;
import net.metro.iot.monitoring.iotMonitoring.service.SensorConfigService;

@RestController
@RequestMapping("/sensor_config")
public class SensorConfigController {

    private final MeterRegistry registry;

    private final SensorConfigService sensorConfigService;

    @Autowired
    public SensorConfigController(MeterRegistry registry, SensorConfigService sensorConfigService) {
        this.registry = registry;
        this.sensorConfigService = sensorConfigService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void save(@RequestBody SensorConfigDto sensorConfigDto) {

        Counter.builder("iot.sensorConfigService.retrieveALLCounter")
                //.tags("countryCode", countryCode, "salesLine", salesLine, "storeNumber", storeNumber.toString())
                .register(registry)
                .increment();

        sensorConfigService.save(sensorConfigDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SensorConfigDto getById(@PathVariable("id") String id) throws Exception {
        return sensorConfigService.getByPrimaryKey(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<SensorConfigDto> getAll() {
        return sensorConfigService.getAll();
    }

}