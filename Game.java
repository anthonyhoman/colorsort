import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Game {

    public Game(String initialState) {
        this.bottles = parseGameState(initialState);
    };

    private List<Bottle> bottles;

    public boolean isComplete() {
        for (Bottle bottle : bottles) {
            if (!bottle.isComplete())
                return false;
        }
        return true;
    }

    public String getState() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bottles.size(); i++) {
            sb.append(i + ":" + bottles.get(i).getState());
        }
        return sb.toString();
    }

    public Stack<Move> solve() {
        Stack<Move> moves = new Stack<Move>();
        Set<String> gameStates = new HashSet<String>();
        Stack<Move> solution = null;
        while(solution == null) {
            solution = this.solve(moves, gameStates);
        }
        return solution;
    }

    private Stack<Move> solve(Stack<Move> moves,
            Set<String> gameStates) {
        if(this.isComplete()) {
            return moves;
        }
        //Optimization: Fill every unencumbered bottle first
        for(int i = 0; i < this.bottles.size(); i++) {
            Bottle targetBottle = this.bottles.get(i);
            if(targetBottle.isUnencumbered()) {
                for(int j = 0; j < this.bottles.size(); j++) {
                    if(i == j) continue;
                    Bottle sourceBottle = this.bottles.get(j);
                    Move move = targetBottle.attemptFill(sourceBottle, j, i);
                    if(move != null) {
                        String newState = this.getState();
                        if (!gameStates.contains(newState) && move.numUnits < 4) {
                            gameStates.add(newState);
                            moves.push(move);
                            return solve(moves, gameStates);
                        } else {
                            undoMove(move);
                        }
                    }
                }
            }
        }
        for(int i = 0; i < this.bottles.size(); i++) {
            Bottle sourceBottle = this.bottles.get(i);
            if(sourceBottle.isEmpty()) {
                continue;
            }
            for(int j = 0; j < this.bottles.size(); j++) {
                if(i == j) continue;
                Bottle targetBottle = this.bottles.get(j);
                //Optimization: If there is a previous empty slot, don't fill this empty slot
                boolean hasPreviousEmptyBottle = false;
                for(int k = 0; k < j; k++) {
                    if(this.bottles.get(k).isEmpty()) {
                        hasPreviousEmptyBottle = true;
                        break;
                    }
                }
                if(targetBottle.isEmpty() && hasPreviousEmptyBottle) {
                    continue;
                }
                if(targetBottle.hasOpening()) {
                    Move move = targetBottle.attemptFill(sourceBottle, i, j);
                    if(move != null) {
                        String newState = this.getState();
                        if (!gameStates.contains(newState) && move.numUnits < 4) {
                            gameStates.add(newState);
                            moves.push(move);
                            return solve(moves, gameStates);
                        } else {
                            undoMove(move);
                        }
                    }
                }
            }
        }
        // no possible moves at this state. Undo last move and try again
        Move lastMove = moves.pop();
        undoMove(lastMove);
        return null;
    }

    private void undoMove(Move move) {
        Bottle sourceBottle = bottles.get(move.sourceBottleIdx);
        Bottle targetBottle = bottles.get(move.targetBottleIdx);
        for(int i = 0; i < move.numUnits; i++) {
            sourceBottle.executeOneUnitFill(targetBottle);
        }
    }

    // expected state format: dg,dg,pk,lb;emp,emp,emp,emp
    private List<Bottle> parseGameState(String state) {
        return Collections.list(new StringTokenizer(state, ";")).stream()
                .map(token -> parseOneBottle((String) token))
                .collect(Collectors.toList());
    }

    private Bottle parseOneBottle(String bottleState) {
        return new Bottle(Collections.list(new StringTokenizer(bottleState, ",")).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList()));
    }

}
