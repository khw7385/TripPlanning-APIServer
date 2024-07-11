package me.khw7385.trip_planner.plan.dto;

import lombok.Builder;

public class VisitedPlaceDto {
    @Builder
    public record Request(String name, Double latitude, Double longitude){
        public Command toCommand(){
            return Command.builder()
                    .name(name)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
        }
    }


    @Builder
    public record Command(Long placeId, String name, Double latitude, Double longitude, boolean visited){}
    @Builder
    public record Response(Long placeId, String name, Double latitude, Double longitude, boolean visited){}
}
