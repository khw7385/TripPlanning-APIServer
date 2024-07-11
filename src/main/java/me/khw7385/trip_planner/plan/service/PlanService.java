package me.khw7385.trip_planner.plan.service;


import me.khw7385.trip_planner.plan.dto.PlanDto;

import java.util.List;

public interface PlanService {
    List<PlanDto.ListResponse> getAllPlan(PlanDto.Command command);
    PlanDto.SingleResponse getPlan(PlanDto.Command command);
    PlanDto.SingleResponse save(PlanDto.CreateCommand command);
    void deletePlan(Long planId);
}
