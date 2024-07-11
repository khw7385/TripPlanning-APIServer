package me.khw7385.trip_planner.path.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PathDto {
    @Builder
    public record Request(Location origin, List<Location> destinations, Boolean isReturnToStart){
        public Command toCommand(){
            return Command.builder()
                    .origin(origin)
                    .destinations(destinations)
                    .isReturnToStart(isReturnToStart)
                    .build();
        }
    }
    @Builder
    public record Command(Location origin, List<Location> destinations, Boolean isReturnToStart){
    }

    @Builder
    public record Response(List<Integer> sequences){}
    @Getter
    @NoArgsConstructor
    public static class Location{
        private Double latitude;
        private Double longitude;
    }
}
