package me.khw7385.trip_planner.plan.service.impl;

import lombok.RequiredArgsConstructor;
import me.khw7385.trip_planner.plan.dto.PlanDto;
import me.khw7385.trip_planner.plan.dto.VisitedPlaceDto;
import me.khw7385.trip_planner.plan.entity.Plan;
import me.khw7385.trip_planner.plan.entity.VisitedPlace;
import me.khw7385.trip_planner.plan.repository.PlanRepository;
import me.khw7385.trip_planner.plan.repository.VisitedPlaceRepository;
import me.khw7385.trip_planner.plan.service.PlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final VisitedPlaceRepository visitedPlaceRepository;

    @Transactional(readOnly = true)
    @Override
    public List<PlanDto.ListResponse> getAllPlan(PlanDto.Command command) {
        List<Plan> plans = planRepository.findALLByUserId(command.userId());

        return plans.stream().map(
                plan ->
                        PlanDto.ListResponse.builder()
                                .planId(plan.getId())
                                .title(plan.getTitle())
                                .memberNum(plan.getMemberNum())
                                .startDate(plan.getStartDate())
                                .endDate(plan.getEndDate())
                                .build()
        ).toList();
    }
    @Transactional(readOnly = true)
    @Override
    public PlanDto.SingleResponse getPlan(PlanDto.Command command) {
        Plan findPlan = planRepository.findById(command.planId()).get();
        List<VisitedPlace> visitedPlaces = findPlan.getVisitedPlaces();

        List<VisitedPlaceDto.Response> visitedPlacesDto = visitedPlaces.stream().map(
                place -> VisitedPlaceDto.Response.builder()
                        .placeId(place.getId())
                        .name(place.getName())
                        .latitude(place.getLatitude())
                        .longitude(place.getLongitude())
                        .visited(place.getVisited())
                        .build()
        ).toList();

        return PlanDto.SingleResponse.builder()
                .planId(findPlan.getId())
                .title(findPlan.getTitle())
                .memberNum(findPlan.getMemberNum())
                .startDate(findPlan.getStartDate())
                .endDate(findPlan.getEndDate())
                .places(visitedPlacesDto)
                .build();
    }
    @Transactional
    @Override
    public PlanDto.SingleResponse save(PlanDto.CreateCommand command) {
        Plan plan = Plan.builder()
                .userId(command.userId())
                .memberNum(command.memberNum())
                .title(command.title())
                .startDate(command.startDate())
                .endDate(command.endDate())
                .build();

        List<VisitedPlaceDto.Command> commandPlaces = command.places();

        List<VisitedPlace> visitedPlaces = commandPlaces.stream().map(
                commandPlace -> VisitedPlace.builder()
                        .name(commandPlace.name())
                        .latitude(commandPlace.latitude())
                        .longitude(commandPlace.longitude())
                        .visited(false)
                        .plan(plan)
                        .build()
        ).toList();

        Plan savePlan = planRepository.save(plan);
        visitedPlaceRepository.saveAll(visitedPlaces);

        return PlanDto.SingleResponse.builder()
                .planId(savePlan.getId())
                .title(savePlan.getTitle())
                .memberNum(savePlan.getMemberNum())
                .startDate(savePlan.getStartDate())
                .endDate(savePlan.getEndDate())
                .build();
    }

    @Override
    public void deletePlan(Long planId) {
        planRepository.deleteById(planId);
    }
}
