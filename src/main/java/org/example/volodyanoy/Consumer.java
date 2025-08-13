package org.example.volodyanoy;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;


public class Consumer
{
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    public static void main( String[] args ) {

        //Регистрация сенсора
        RestTemplate restTemplate = new RestTemplate();
        Random random = new Random();
        String sensorName = "Sensor from consumer" + random.nextInt(777);
        final String REGISTRATION_URL = "http://localhost:8080/sensors/registration";
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName(sensorName);
        try {
            String registrationResponse = restTemplate.postForObject(REGISTRATION_URL, sensorDTO, String.class);
            logger.info("Registration response: {}", registrationResponse);
        } catch (Exception e){
            logger.error("Error registration", e);
        }


        // Отправка 1000 измерений сенсора
        final String MEASUREMENT_ADD_URL = "http://localhost:8080/measurements/add";
        for(int i = 0; i < 33; i++){
            MeasurementDTO measurementDTO = new MeasurementDTO();
            enrichMeasurement(measurementDTO, sensorDTO);
            try {
                restTemplate.postForObject(MEASUREMENT_ADD_URL, measurementDTO, String.class);
                logger.debug("Measurement {} added: {}", i + 1, measurementDTO);
            } catch (Exception e){
                logger.error("Error adding measurement {}", i + 1, e);
            }
        }

        //Получение всех измерений
        final String MEASUREMENTS_URL = "http://localhost:8080/measurements";

        List<Integer> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        int i = 1;

        try {
            ResponseEntity<List<MeasurementDTO>> response = restTemplate.exchange(
                    MEASUREMENTS_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<MeasurementDTO>>() {
                    });
            List<MeasurementDTO> measurementDTOList = response.getBody();
            logger.info("Received measurements = {}", measurementDTOList.size());
            for(MeasurementDTO measurementDTO: measurementDTOList){

                xData.add(i++);
                yData.add(measurementDTO.getValue());
                logger.debug("Measurement: {}", measurementDTO);

            }
        } catch (Exception e){
            logger.error("Error getting measurements", e);
        }

        printWeatherGraph(xData, yData);

    }

    public static void enrichMeasurement(MeasurementDTO dto, SensorDTO sensorDTO){
        Random random = new Random();
        dto.setValue(random.nextDouble() * 200 - 100);
        dto.setRaining(random.nextBoolean());
        dto.setSensorDTO(sensorDTO);

    }

    public static void printWeatherGraph(List<Integer> xData, List<Double> yData){
        XYChart chart = new XYChartBuilder()
                .width(750)
                .height(500)
                .title("Temperature").xAxisTitle("Measurement").yAxisTitle("Value")
                .build();
        chart.addSeries("Weather", xData, yData);
        new SwingWrapper<>(chart).displayChart();

    }
}
