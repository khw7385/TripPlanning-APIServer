package me.khw7385.trip_planner.path.controller;

import lombok.RequiredArgsConstructor;
import me.khw7385.trip_planner.path.dto.PathDto;
import me.khw7385.trip_planner.path.service.PathService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/path")
@RequiredArgsConstructor
public class PathController {
    private final PathService pathService;

    @PostMapping("/optimal_path")
    public ResponseEntity<PathDto.Response> getOptimalPath(@RequestBody PathDto.Request request){
        return ResponseEntity.ok(pathService.getOptimalDistance(request.toCommand()));
    }
}
