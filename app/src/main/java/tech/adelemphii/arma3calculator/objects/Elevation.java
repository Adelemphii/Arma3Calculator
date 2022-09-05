package tech.adelemphii.arma3calculator.objects;

public class Elevation {

    private final int range;
    private int elevation;
    private final int charge;

    private double flightTime;
    private int elevationDiff;

    public Elevation(int range, int elevation, int charge) {
        this.range = range;
        this.elevation = elevation;
        this.charge = charge;
    }

    public int getRange() {
        return range;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public int getCharge() {
        return charge;
    }

    public void setFlightTime(double flightTime) {
        this.flightTime = flightTime;
    }

    public double getFlightTime() {
        return flightTime;
    }

    public int getElevationDiff() {
        return elevationDiff;
    }

    public void setElevationDiff(int elevationDiff) {
        this.elevationDiff = elevationDiff;
    }

    @Override
    public String toString() {
        return "Elevation{" +
                "range=" + range +
                ", elevation=" + elevation +
                ", charge=" + charge +
                ", flightTime (approx.)=" + flightTime +
                ", elevationDiff=" + elevationDiff +
                '}';
    }
}
