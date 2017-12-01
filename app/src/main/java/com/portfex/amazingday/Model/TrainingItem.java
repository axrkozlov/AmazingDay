package com.portfex.amazingday.Model;

/**
 * Created by alexanderkozlov on 11/29/17.
 */

public class TrainingItem {
    private Long id;
    private String name;
    private String description;
    private Long startTime;
    private Long totalTime;
    private Long lastDate;
    private Integer weekDaysComposed;

    public TrainingItem() {
    }

    public TrainingItem(Long id) {
        this.id = id;
    }

    public TrainingItem(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public Long getLastDate() {
        return lastDate;
    }

    public Integer getWeekDaysComposed() {
        return weekDaysComposed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public void setLastDate(Long totalTime) {
        this.lastDate = lastDate;
    }

    public void setWeekDaysComposed(Integer weekDaysComposed) {
        this.weekDaysComposed = weekDaysComposed;
    }
}
