package com.vaibhav.apslabproject;

import android.os.Parcelable;

import java.util.ArrayList;

public class Model {
    String pt1;
    String pt2;
    String dist;
    ArrayList<Integer> path = null;

    public Model(String pt1, String pt2, String dist, ArrayList<Integer> path) {
        this.pt1 = pt1;
        this.pt2 = pt2;
        this.dist = dist;
        this.path = path;
    }

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

    public ArrayList<Integer> getPath() {
        return path;
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Model{" +
                "pt1='" + pt1 + '\'' +
                ", pt2='" + pt2 + '\'' +
                ", dist='" + dist + '\'' +
                ", path=" + path +
                '}';
    }
}
