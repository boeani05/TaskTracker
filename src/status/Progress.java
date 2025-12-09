package status;

public enum Progress {
    DONE(1),
    IN_PROGRESS(2),
    NOT_DONE(3);

    private final int numVal;

    Progress(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }

    public static Progress fromNumVal(int numVal) {
        for (Progress p : Progress.values()) {
            if (p.getNumVal() == numVal) {
                return p;
            }
        }
        throw new IllegalArgumentException("Invalid progress number: " + numVal);
    }
}