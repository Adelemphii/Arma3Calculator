package tech.adelemphii.arma3calculator.objects.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum RangeTable {
    ID_1(150, 1482, 1, 16.2, 1.2f),
    ID_2(200, 1441, 1, 16.1, 1.2f),
    ID_3(250, 1440, 1, 16, 1.2f),
    ID_4(300, 1357, 1, 15.9, 1.3f),
    ID_5(350, 1312, 1, 15.7, 1.3f),
    ID_6(400, 1264, 1, 15.4, 1.4f),
    ID_7(450, 1213, 1, 15.1, 1.4f),
    ID_8(500, 1155, 1, 14.8, 1.5f),
    ID_9(550, 1089, 1, 14.3, 1.7f),
    ID_10(600, 1006, 1, 13.6, 2f),
    ID_11(650, 846, 1, 12, 3.1f),
    ID_12(651, 1366, 2, 23.8, 0.9f),
    ID_13(700, 1347, 2, 23.7, 0.9f),
    ID_14(750, 1327, 2, 23.6, 0.9f),
    ID_15(800, 1307, 2, 23.5, 0.9f),
    ID_16(850, 1286, 2, 23.3, 0.9f),
    ID_17(900, 1264, 2, 23.2, 1f),
    ID_18(950, 1242, 2, 23, 1f),
    ID_19(1000, 1218, 2, 22.8, 1f),
    ID_20(1050, 1194, 2, 22.6, 1f),
    ID_21(1100, 1169, 2, 22.3, 1.1f),
    ID_22(1150, 1142, 2, 22, 1.1f),
    ID_23(1200, 1113, 2, 21.7, 1.2f),
    ID_24(1250, 1081, 2, 21.4, 1.3f),
    ID_25(1300, 1046, 2, 20.9, 1.4f),
    ID_26(1350, 1006, 2, 20.4, 1.5f),
    ID_27(1400, 956, 2, 19.7, 1.8f),
    ID_28(1450, 881, 2, 8.6, 2.4f),
    ID_29(1451, 1300, 3, 31.2, 0.7f),
    ID_30(1500, 1288, 3, 31.1, 0.7f),
    ID_31(1550, 1276, 3, 31, 0.7f),
    ID_32(1600, 1264, 3, 30.9, 0.7f),
    ID_33(1650, 1252, 3, 30.7, 0.7f),
    ID_34(1700, 1239, 3, 30.6, 0.7f),
    ID_35(1750, 1226, 3, 30.5, 0.8f),
    ID_36(1800, 1213, 3, 30.3, 0.8f),
    ID_37(1850, 1199, 3, 30.1, 0.8f),
    ID_38(1900, 1185, 3, 30, 0.8f),
    ID_39(1950, 1170, 3, 29.8, 0.8f),
    ID_40(2000, 1155, 3, 29.6, 0.8f),
    ID_41(2050, 1140, 3, 29.4, 0.9f),
    ID_42(2100, 1124, 3, 29.1, 0.9f),
    ID_43(2150, 1107, 3, 28.9, 0.9f),
    ID_44(2200, 1089, 3, 28.6, 1f),
    ID_45(2250, 1071, 3, 28.3, 1f),
    ID_46(2300, 1051, 3, 28, 1.1f),
    ID_47(2350, 1029, 3, 27.6, 1.1f),
    ID_48(2400, 1006, 3, 27.2, 1.2f),
    ID_49(2450, 980, 3, 26.8, 1.3f),
    ID_50(2500, 949, 3, 26.2, 1.5f),
    ID_51(2550, 910, 3, 25.4, 1.8f),
    ID_52(2600, 846, 3, 24.1, 2.5f);

    private final int range;
    private final int elevation;
    private final int charge;

    private final double timeOfTravel;
    private final float elevationDiff;

    RangeTable(int range, int elevation, int charge, double timeOfTravel, float elevationDiff) {
        this.range = range;
        this.elevation = elevation;
        this.charge = charge;
        this.timeOfTravel = timeOfTravel;
        this.elevationDiff = elevationDiff;
    }

    public int getRange() {
        return range;
    }

    public int getElevation() {
        return elevation;
    }

    public int getCharge() {
        return charge;
    }

    public double getTimeOfTravel() {
        return timeOfTravel;
    }

    public float getElevationDiff() {
        return elevationDiff;
    }

    public static Map<Integer, List<RangeTable>> getMinMax() {
        Map<Integer, List<RangeTable>> ranges = new HashMap<>();

        ranges.put(1, Arrays.asList(ID_1, ID_11));
        ranges.put(2, Arrays.asList(ID_12, ID_28));
        ranges.put(3, Arrays.asList(ID_29, ID_52));

        return ranges;
    }

    public static int getCharge(int range) {
        Map<Integer, List<RangeTable>> rangeMap = getMinMax();

        if(range < 650 || rangeMap.isEmpty()) {
            return -1;
        }
        for(int i = 1; i < 4; i++) {
            List<RangeTable> rangeTables = rangeMap.get(i);
            if(rangeTables == null || rangeTables.isEmpty()) {
                return -1;
            }
            RangeTable minimum = rangeTables.get(0);
            RangeTable maximum = rangeTables.get(1);

            if(range >= minimum.getRange() && range <= maximum.getRange()) {
                return i;
            }
        }
        return -1;
    }

    public static List<RangeTable> getDefaultsByCharge(int charge) {
        if(charge == -1) {
            return null;
        }
        List<RangeTable> rangeTables = new ArrayList<>();
        for(RangeTable rangeTable : values()) {
            if(rangeTable.getCharge() == charge) {
                rangeTables.add(rangeTable);
            }
        }

        return rangeTables;
    }
}
