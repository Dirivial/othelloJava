import java.awt.*;
import java.util.ArrayList;

public class OthelloMinMaxAI extends OthelloSuperAI {

    private Integer depth = 3;

    public OthelloMinMaxAI(Board gameBoard, Color color) {
        this.board = gameBoard;
        this.color = color;
        if (this.color == Color.RED) {
            this.otherColor = Color.BLUE;
        } else {
            this.otherColor = Color.RED;
        }
    }

    @Override
    public Position getMove() {

        ArrayList<Position> listOfMoves = this.board.listOfAllEnabledPositions();

        Integer maximumScore = -1000;
        Position bestPosition = listOfMoves.get(0);

        for (int i = 0; i < listOfMoves.size(); i++) {
            Board copyOfBoard = new Board(board);
            copyOfBoard.makeMoveFromPosition(listOfMoves.get(i), this.color);
            Integer score = minMaxAlgorithm(copyOfBoard, this.depth, false);
            if (maximumScore < score) {
                maximumScore = score;
                bestPosition = listOfMoves.get(i);
            }
        }
        return bestPosition;
    }

    private Integer heuristicMethod(Board board, Color playerColor) {

        Integer heuristicValue = 0;

        Position[] bestPositions = {new Position(0,0), new Position(7, 0),
                new Position(0,7), new Position(7,7)};

        for (Position pos: bestPositions) {
            if (board.getTile(pos).getColor() == this.color) {
                heuristicValue += 100;
            } else {
                heuristicValue -= 100;
            }
        }

        ArrayList<Position> badPositions = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 1) {
                    badPositions.add(new Position(i, j));
                    badPositions.add(new Position(j, i));
                }
                if (i == 6) {
                    badPositions.add(new Position(i, j));
                    badPositions.add(new Position(j, i));
                }
            }
        }

        for (Position position: badPositions) {
            if (board.getTile(position).getColor() == this.color) {
                heuristicValue -= 20;
            } else {
                heuristicValue += 20;
            }
        }

        return heuristicValue;
    }

    private Integer minMaxAlgorithm(Board node, Integer depth, boolean maximizingPlayer) {
        if (depth == 0 || !node.containsColor(Color.DARK_GRAY)) {
            if (maximizingPlayer) {
                return heuristicMethod(node, this.color);
            } else {
                return heuristicMethod(node, this.otherColor);
            }
        }

        Integer value;
        if (maximizingPlayer) {
            value = -1000;
            node.calculatePossibleMoves(this.color);
            ArrayList<Position> listOfMoves = node.listOfAllEnabledPositions();

            for (int i = 0; i < listOfMoves.size(); i++) {
                Board copyOfNode = new Board(node);
                copyOfNode.makeMoveFromPosition(listOfMoves.get(i), this.color);
                Integer score = minMaxAlgorithm(copyOfNode, depth-1, false);
                if (value < score) {
                    value = score;
                }
            }
            return value;
        } else {
            value = 1000;
            node.calculatePossibleMoves(this.otherColor);
            ArrayList<Position> listOfMoves = node.listOfAllEnabledPositions();

            for (int i = 0; i < listOfMoves.size(); i++) {
                Board copyOfNode = new Board(node);
                copyOfNode.makeMoveFromPosition(listOfMoves.get(i), this.otherColor);
                Integer score = minMaxAlgorithm(copyOfNode, depth-1, true);
                if (value > score) {
                    value = score;
                }
            }
            return value;
        }
    }
}
