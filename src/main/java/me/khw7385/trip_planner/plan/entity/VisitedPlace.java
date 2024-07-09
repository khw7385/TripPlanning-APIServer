package me.khw7385.trip_planner.plan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class VisitedPlace extends Place{
    private Boolean visited;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
}
