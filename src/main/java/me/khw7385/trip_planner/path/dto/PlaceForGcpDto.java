package me.khw7385.trip_planner.path.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class PlaceForGcpDto {
    @Builder
    public record Request(Double latitude, Double longitude){
        @Override
        public String toString() {
            return latitude + "," +  longitude;
        }
    }

    public record Response(List<Row> rows){}

    @Getter
    @NoArgsConstructor
    public static class Row{
        List<Element> elements;
    }

    @Getter
    @NoArgsConstructor
    public static class Element{
        private Distance distance;
    }
    @Getter
    @NoArgsConstructor
    public static class Distance{
        private int value;
    }
}
