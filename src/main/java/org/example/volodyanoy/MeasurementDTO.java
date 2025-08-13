package org.example.volodyanoy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeasurementDTO {

    private Double value;
    private Boolean raining;

    @JsonProperty("sensor")
    private SensorDTO sensorDTO;


    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensorDTO() {
        return sensorDTO;
    }

    public void setSensorDTO(SensorDTO sensorDTO) {
        this.sensorDTO = sensorDTO;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensorDTO=" + sensorDTO +
                '}';
    }
}
