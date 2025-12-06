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
}
