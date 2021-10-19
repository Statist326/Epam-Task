package com.company.model;
import java.util.List;

public class Root {
    private String name;
    private List<Aplications> Aplications;



    public String getName() {
        return name;
    }

    public List<Aplications> getAplications() {
        return Aplications;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAplications(List<Aplications> aplications) {
       this.Aplications = aplications;
    }

    @Override
    public String toString() {
        return "Root{" +
                "name='" + name + '\'' +
                ", Aplications=" + Aplications +
                '}';
    }
}
