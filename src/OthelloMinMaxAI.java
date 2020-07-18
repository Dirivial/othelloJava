import java.awt.*;
import java.util.ArrayList;

public class OthelloMinMaxAI extends OthelloSuperAI {

    private Integer depth = 3;

    public OthelloMinMaxAI(Board gameBoard, Color color) {
        this.board = gameBoard;
        this.color = color;
    }

    @Override
    public Position evaluatePositions(ArrayList<Position> positions) {
        return new Position(0,0);
    }

    private Integer heuristicMethod(Board board) {
        return 0;
    }

    private Integer minMaxAlgorithm(Board node, Integer depth, boolean maximizingPlayer) {
        if (depth == 0 || node.containsColor(Color.DARK_GRAY)) {
            return heuristicMethod(node);
        }
        Integer value;
        if (maximizingPlayer) {
            value = -1000;
            node.calculatePossibleMoves(this.color);
            ArrayList<Position> listOfPossibleMoves = node.listOfAllEnabledPositions();

            for (:) {

            }

        } else {
            value = 1000;
        }
    }
}
