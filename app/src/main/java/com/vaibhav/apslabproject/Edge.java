package com.vaibhav.apslabproject;

public class Edge {
    private int u;
    private int v;
    private int wei;

    public Edge(int u, int v, int wei) {
        this.u = u;
        this.v = v;
        this.wei = wei;
    }

    public int getU() {
        return u;
    }

    public void setU(int u) {
        this.u = u;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getWei() {
        return wei;
    }

    public void setWei(int wei) {
        this.wei = wei;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "u=" + u +
                ", v=" + v +
                ", wei=" + wei +
                '}';
    }
}
