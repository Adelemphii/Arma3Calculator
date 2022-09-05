package tech.adelemphii.arma3calculator.utility;

import java.util.Arrays;
import java.util.List;

import tech.adelemphii.arma3calculator.objects.Elevation;
import tech.adelemphii.arma3calculator.objects.enums.RangeTable;

public class RangeUtility {

    public static Elevation calculateElevation(int range) {
        int charge = RangeTable.getCharge(range);
        if(charge == -1) {
            return null;
        }
        List<RangeTable> aboveBelow = getAboveBelow(range, charge);
        if(aboveBelow == null || aboveBelow.isEmpty()) {
            return null;
        }

        RangeTable below = aboveBelow.get(0);
        RangeTable above = aboveBelow.get(1);

        // if your distance is 1120 meters, take the elevations for ranges 1100 and 1150, calculate the difference,
        // divide that difference by 5 (for 50 meters between the ranges on the table), multiply by 2 (for the 20 we need)
        // and subtract (!) it from the elevation for 1100
        double elevation = below.getElevation() - above.getElevation();
        elevation = elevation / 5;
        elevation = elevation * 2;
        elevation = below.getElevation() - elevation;

        elevation = Math.floor(elevation);
        return new Elevation(range, (int) elevation, charge);
    }

    public static List<RangeTable> getAboveBelow(int range, int charge) {
        List<RangeTable> defaults = RangeTable.getDefaultsByCharge(charge);
        RangeTable below = null;
        RangeTable above = null;

        assert defaults != null;
        for(RangeTable table : defaults) {
            if(range >= table.getRange()) {
                below = table;
            }

            if(range <= table.getRange()) {
                above = table;
                break;
            }
        }

        if(above == null || below == null) {
            return null;
        }
        return Arrays.asList(below, above);
    }

    public static double calculateFlightTime(Elevation elevation) {
        List<RangeTable> aboveBelow = getAboveBelow(elevation.getRange(), elevation.getCharge());
        if(aboveBelow == null) {
            return -1;
        }

        RangeTable below = aboveBelow.get(0);
        RangeTable above = aboveBelow.get(1);
        if(below == null || above == null) {
            return -1;
        }

        double belowFlightTime = below.getTimeOfTravel();
        double aboveFlightTime = above.getTimeOfTravel();

        double average = belowFlightTime + aboveFlightTime;
        average = average / 2;
        return average;
    }

    public static double calculateElevationDiff(Elevation elevation, int heightDifference) {
        List<RangeTable> aboveBelow = getAboveBelow(elevation.getRange(), elevation.getCharge());
        if(aboveBelow == null) {
            return 0;
        }

        RangeTable below = aboveBelow.get(0);
        RangeTable above = aboveBelow.get(1);
        if(below == null || above == null) {
            return 0;
        }

        double elevationDiff = below.getTimeOfTravel();
        elevationDiff = elevationDiff / 100;
        elevationDiff = heightDifference * elevationDiff;
        return elevationDiff;
    }
}
