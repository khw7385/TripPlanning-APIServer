package me.khw7385.trip_planner.path.service;

import lombok.RequiredArgsConstructor;
import me.khw7385.trip_planner.path.api.GcpDistanceMatrixApi;
import me.khw7385.trip_planner.path.dto.PathDto;
import me.khw7385.trip_planner.path.dto.PlaceForGcpDto;
import me.khw7385.trip_planner.path.tsp.BBAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PathService {
    private final GcpDistanceMatrixApi gcpDistanceMatrixApi;
    private final BBAlgorithm bbAlgorithm;

    public PathDto.Response getOptimalDistance(PathDto.Command command){
        List<PathDto.Location> locations = Stream.concat(
                Stream.of(command.origin()),
                command.destinations().stream()
        ).toList();

        List<PlaceForGcpDto.Request> requests = locations.stream().map(location -> PlaceForGcpDto.Request
                .builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build()
        ).toList();

        List<List<Integer>> distances = gcpDistanceMatrixApi.getDistances(requests);

        System.out.println(distances);

        List<Integer> result = bbAlgorithm.solve(distances, command.isReturnToStart());

        return PathDto.Response.builder()
                .sequences(result)
                .build();
    }
}
