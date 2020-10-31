package ir.hamiss.persianaddresspicker.Modul;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchLocation implements Serializable {
    @SerializedName("x")
    private double x;
    @SerializedName("y")
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public SearchLocation(Location l) {
        this.x = l.getLat();
        this.y = l.getLon();
    }

    public SearchLocation() {
    }

    @Override
    public String toString() {
        return "SearchLocation{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
