public class Move {

    public int sourceBottleIdx;
    public int targetBottleIdx;
    public int numUnits;
    public Color color;

    public Move(int sourceBottleIdx, int targetBottleIdx, int numUnits, Color color) {
        this.sourceBottleIdx = sourceBottleIdx;
        this.targetBottleIdx = targetBottleIdx;
        this.numUnits = numUnits;
        this.color = color;
    }

    public String toString() {
        return color + " " + sourceBottleIdx + " -> " + targetBottleIdx + " : " + numUnits + " units";
    }

}
