package me.khw7385.trip_planner.plan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlanDto {
    @Builder
    public record Request(String userId){
        public Command toCommand(){
            return Command.builder()
                    .userId(userId)
                    .build();
        }
        public Command toCommand(Long planId){
            return Command.builder()
                    .userId(userId)
                    .planId(planId)
                    .build();
        }
    }
    @Builder
    public record CreateRequest(String userId, String title, Integer memberNum, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd") LocalDate startDate, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd") LocalDate endDate, List<VisitedPlaceDto.Request> places){
        public CreateCommand toCommand(){
            List<VisitedPlaceDto.Command> visitPlaceDtoCommand = new ArrayList<>();
            if(places != null){
                visitPlaceDtoCommand = places.stream().map(
                        place -> VisitedPlaceDto.Command.builder()
                                .name(place.name())
                                .latitude(place.latitude())
                                .longitude(place.longitude())
                                .build()
                ).toList();
            }

            return CreateCommand.builder()
                    .userId(userId)
                    .title(title)
                    .memberNum(memberNum)
                    .startDate(startDate)
                    .endDate(endDate)
                    .places(visitPlaceDtoCommand)
                    .build();
        }
    }
    @Builder
    public record Command(String userId, Long planId){
    }

    @Builder
    public record CreateCommand(String userId, String title, Integer memberNum, LocalDate startDate, LocalDate endDate, List<VisitedPlaceDto.Command> places){

    }
    @Builder
    public record SingleResponse(Long planId, String title, Integer memberNum, LocalDate startDate, LocalDate endDate, List<VisitedPlaceDto.Response> places){
    }
    @Builder
    public record ListResponse(Long planId, String title, Integer memberNum, LocalDate startDate, LocalDate endDate){
    }
}
