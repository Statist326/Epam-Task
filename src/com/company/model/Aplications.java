package com.company.model;

public class Aplications {
    private String name;
    private int cost;
    private int size;

    public Aplications(String name, int cost, int size) {
        this.name = name;
        this.cost = cost;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getSize() {
        return size;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    public void SetSize(int size){
        this.size = size;
    }

    @Override
    public String toString() {
        return "Aplications{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", size=" + size +
                '}';
    }
}
