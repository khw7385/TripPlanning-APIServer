package me.khw7385.trip_planner.path.api;

import me.khw7385.trip_planner.path.dto.PlaceForGcpDto;
import me.khw7385.trip_planner.plan.entity.Place;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GcpDistanceMatrixApiTest {
    @Test
    void test(){
        PlaceForGcpDto.Request request1 = PlaceForGcpDto.Request.builder()
                .latitude(40.6655101)
                .longitude(-73.89188969999998)
                .build();
        PlaceForGcpDto.Request request2 = PlaceForGcpDto.Request.builder()
                .latitude(40.659569)
                .longitude(-73.933783)
                .build();
        List<PlaceForGcpDto.Request> requests = Arrays.asList(request1, request2);

        String queryString = requests.stream().map(PlaceForGcpDto.Request::toString)
                .collect(Collectors.joining("|"));
        PlaceForGcpDto.Response response = RestClient.builder()
                .baseUrl("https://maps.googleapis.com/maps/api/distancematrix/json")
                .build()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key","AIzaSyAhee5N9MWN1xogYzFMVeWvRM-BzR8waF0")
                        .queryParam("origins", queryString)
                        .queryParam("destinations", queryString)
                        .build()
                ).retrieve()
                .toEntity(PlaceForGcpDto.Response.class)
                .getBody();

        List<List<Integer>> result = response.rows()
                .stream()
                .map(row -> row.getElements().stream()
                        .map(element -> element.getDistance().getValue())
                        .toList()
                ).toList();

        System.out.println(result);
    }
}