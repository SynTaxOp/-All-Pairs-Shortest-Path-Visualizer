package com.vaibhav.apslabproject;

public class Model {
    String pt1;
    String pt2;
    String dist;

    public Model(String pt1, String pt2, String dist) {
        this.pt1 = pt1;
        this.pt2 = pt2;
        this.dist = dist;
    }

    public String getPt1() {
        return pt1;
    }

    public void setPt1(String pt1) {
        this.pt1 = pt1;
    }

    public String getPt2() {
        return pt2;
    }

    public void setPt2(String pt2) {
        this.pt2 = pt2;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    @Override
    public String toString() {
        return "Model{" +
                "pt1='" + pt1 + '\'' +
                ", pt2='" + pt2 + '\'' +
                ", dist='" + dist + '\'' +
                '}';
    }
}
