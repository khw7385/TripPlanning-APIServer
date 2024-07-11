package me.khw7385.trip_planner.plan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.khw7385.trip_planner.plan.dto.PlanDto;
import me.khw7385.trip_planner.plan.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
public class PlanController {
    private final PlanService planService;

    @GetMapping("")
    public ResponseEntity<List<PlanDto.ListResponse>> getAllPlans(@RequestParam Map<String, String> params){
        ObjectMapper mapper = new ObjectMapper();
        PlanDto.Request request = mapper.convertValue(params, PlanDto.Request.class);

        return ResponseEntity.ok(planService.getAllPlan(request.toCommand()));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanDto.SingleResponse> getPlan(@RequestParam Map<String, String> params, @PathVariable Long planId){
        ObjectMapper mapper = new ObjectMapper();
        PlanDto.Request request = mapper.convertValue(params, PlanDto.Request.class);
        return ResponseEntity.ok(planService.getPlan(request.toCommand(planId)));
    }

    @PostMapping("/save")
    public ResponseEntity<PlanDto.SingleResponse> savePlan(@RequestBody PlanDto.CreateRequest request){
        return ResponseEntity.ok(planService.save(request.toCommand()));
    }

    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long planId){
        planService.deletePlan(planId);
        return ResponseEntity.noContent().build();
    }
}
