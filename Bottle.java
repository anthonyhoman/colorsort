import java.util.List;

public class Bottle {

    private Color[] colorSlots;

    public Bottle(List<String> colors) {
        this.colorSlots = new Color[4];
        colorSlots[0] = Color.fromString(colors.get(0));
        colorSlots[1] = Color.fromString(colors.get(1));
        colorSlots[2] = Color.fromString(colors.get(2));
        colorSlots[3] = Color.fromString(colors.get(3));
    }

    public boolean isComplete() {
        return (colorSlots[0] == colorSlots[1]) && (colorSlots[0] == colorSlots[2]) && (colorSlots[0] == colorSlots[3]);
    }

    public boolean isEmpty() {
        return colorSlots[0] == Color.EMPTY;
    }

    public boolean hasOpening() {
        return colorSlots[3] == Color.EMPTY;
    }

    public boolean isUnencumbered() {
        if(isEmpty() || !hasOpening()) return false;
        Color color0 = colorAt(0);
        Color color1 = colorAt(1);
        Color color2 = colorAt(2);
        if(color1 == Color.EMPTY) return true;
        if(color1 != color0) return false;
        return (color2 == Color.EMPTY || color2 == color0);
    }

    public int numEmptySlots() {
        int num = 0;
        for(int i = 0; i < 4; i++) {
            if(colorSlots[i] == Color.EMPTY) {
                num++;
            }
        }
        return num;
    }

    public int numTopColorUnits() {
        Integer firstIdx = this.highestNonEmptySlot();
        if(firstIdx == null) return 0;
        int num = 1;
        for(int i = firstIdx - 1; i >= 0; i--) {
            if(colorAt(i) != colorAt(firstIdx)) {
                break;
            }
            num++;
        }
        return num;
    }

    public Color topNonEmptyColor() {
        if(colorSlots[3] != Color.EMPTY) return colorSlots[3];
        if(colorSlots[2] != Color.EMPTY) return colorSlots[2];
        if(colorSlots[1] != Color.EMPTY) return colorSlots[1];
        if(colorSlots[0] != Color.EMPTY) return colorSlots[0];
        return null;
    }

    public boolean canFill(Bottle source) {
        if(!this.hasOpening()) return false;
        Color targetColor = (this.topNonEmptyColor() != null) ? this.topNonEmptyColor() : Color.EMPTY;
        Color sourceColor = (source.topNonEmptyColor() != null) ? source.topNonEmptyColor() : Color.EMPTY;
        return sourceColor != Color.EMPTY && (targetColor == Color.EMPTY || targetColor == sourceColor);
    }

    public Color colorAt(int slot) {
        return colorSlots[slot];
    }

    public void emptySlot(int slot) {
        this.colorSlots[slot] = Color.EMPTY;
    }

    public Integer lowestEmptySlot() {
        if(colorSlots[0] == Color.EMPTY) return 0;
        if(colorSlots[1] == Color.EMPTY) return 1;
        if(colorSlots[2] == Color.EMPTY) return 2;
        if(colorSlots[3] == Color.EMPTY) return 3;
        return null;
    }

    public Integer highestNonEmptySlot() {
        if(colorSlots[3] != Color.EMPTY) return 3;
        if(colorSlots[2] != Color.EMPTY) return 2;
        if(colorSlots[1] != Color.EMPTY) return 1;
        if(colorSlots[0] != Color.EMPTY) return 0;
        return null;
    }

    public Move attemptFill(Bottle sourceBottle, int sourceBottleIdx, int targetBottleIdx) {
        //Optimization: Never pour an unencumbered bottle into another one unless it is a
        // matching unencumbered bottle
        if(sourceBottle.isUnencumbered() && !this.isUnencumbered()) {
            return null;
        }
        //Optimization: If the target doesn't have enough empty slots for the top color units
        // then do nothing
        if(this.numEmptySlots() < sourceBottle.numTopColorUnits()) {
            return null;
        }
        Move move = null;
        int numUnits = 0;
        Color color = null;
        while(this.canFill(sourceBottle)) {
            numUnits++;
            color = executeOneUnitFill(sourceBottle);
        }
        if(numUnits > 0) {
            move = new Move(sourceBottleIdx, targetBottleIdx, numUnits, color);
        }
        return move;
    }

    // Assumes all precondition checks have been made
    // Fills one slot only
    public Color executeOneUnitFill(Bottle source) {
        int sourceIdx = source.highestNonEmptySlot();
        int targetIdx = this.lowestEmptySlot();
        this.colorSlots[targetIdx] = source.colorAt(sourceIdx);
        source.emptySlot(sourceIdx);
        return this.colorAt(targetIdx);
    }

    public String getState() {
        return colorSlots[0].toString() + "," + colorSlots[1].toString() + ","
             + colorSlots[2].toString() + "," + colorSlots[3].toString() + ";";
    }

    public String toString() {
        return getState();
    }
}
