package me.khw7385.trip_planner.path.api;

import me.khw7385.trip_planner.path.dto.PlaceForGcpDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GcpDistanceMatrixApi {
    private String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json";
    @Value("${gcp.api.key}")
    private String key;

    public List<List<Integer>> getDistances(List<PlaceForGcpDto.Request> requests){
        String queryString = requests.stream().map(PlaceForGcpDto.Request::toString)
                .collect(Collectors.joining("|"));
        PlaceForGcpDto.Response response = RestClient.builder()
                .baseUrl(baseUrl)
                .build()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", key)
                        .queryParam("origins", queryString)
                        .queryParam("destinations", queryString)
                        .queryParam("mode", "transit")
                        .build()
                ).retrieve()
                .toEntity(PlaceForGcpDto.Response.class)
                .getBody();
        System.out.println(response.rows().size());
        return parse(response);
    }


    private List<List<Integer>> parse(PlaceForGcpDto.Response response){
        return response.rows()
                .stream()
                .map(row -> row.getElements().stream()
                        .map(element -> {
                            if(element.getDistance() == null) return 0;
                            else return element.getDistance().getValue();
                        })
                        .toList()
                ).toList();
    }
}
