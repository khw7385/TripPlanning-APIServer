package me.khw7385.trip_planner.plan.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class VisitedPlace extends Place{
    private Boolean visited;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Builder
    public VisitedPlace(String name, Double latitude, Double longitude, Boolean visited, Plan plan) {
        super(name, latitude, longitude);
        this.visited = visited;
        this.plan = plan;
    }
}
