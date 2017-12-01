package tableadvanced;

/**
 * Table value class.
 *
 * @author skorkmaz
 */
public class TableValue {

    private int indexValType = -1;
    private double val1X = Double.NaN;
    private double val2X = Double.NaN;

    public TableValue(int indexValType) {
        this.indexValType = indexValType;
    }

    public TableValue(int indexValType, double val) {
        this.indexValType = indexValType;
        if (indexValType == 0) { //input val is 1X
            this.val1X = val;
            this.val2X = calcVal2X(val);
        } else { //input val is 2X
            this.val1X = calcVal1X(val);
            this.val2X = val;
        }
    }

    public int getIndexValType() {
        return indexValType;
    }

    public double getVal1X() {
        return val1X;
    }

    public void setVal1X(double val1X) {
        this.val1X = val1X;
        this.val2X = calcVal2X(val1X);
    }

    public double getVal2X() {
        return val2X;
    }

    public void setVal2X(double val2X) {
        this.val2X = val2X;
        this.val1X = calcVal1X(val2X);
    }

    public static double calcVal1X(double val2X) {
        return val2X / 2;
    }

    public static double calcVal2X(double val1X) {
        return val1X * 2;
    }

    @Override
    public String toString() {
        return "indexValType = " + indexValType + ", val1X = " + val1X + ", val2X = " + val2X;
    }

    private boolean isEqualDoubles(double val1, double val2) {
        return Math.abs(val1 - val2) < 1e-14;
    }

    /**
     * Check if this value is equal to input value.
     *
     * @param tableVal
     * @return
     */
    public boolean isEqual(TableValue tableVal) {
        if (tableVal == null) {
            return false;
        }
        return isEqualDoubles(this.val1X, tableVal.val1X) && isEqualDoubles(this.val2X, tableVal.val2X) && (this.indexValType == tableVal.indexValType);
    }

}
