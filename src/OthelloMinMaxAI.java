import java.awt.*;
import java.util.ArrayList;

//TODO: Improve heuristic method, (amount of markers placed, give value to more tiles)

public class OthelloMinMaxAI extends OthelloSuperAI {

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

        int maximumScore = -1000;
        Position bestPosition = listOfMoves.get(0);

        for (Position listOfMove : listOfMoves) {
            Board copyOfBoard = this.board.boardCopy();
            copyOfBoard.makeMoveFromPosition(listOfMove, this.color);
            Integer depth = 5;
            Integer score = minMaxAlgorithm(copyOfBoard, depth, false);
            if (maximumScore < score) {
                System.out.println("Previous high: " + maximumScore + ", New high: " + score);
                System.out.println("Position" + listOfMove);
                maximumScore = score;
                bestPosition = listOfMove;
            }
        }
        return bestPosition;
    }

    private Integer heuristicMethod(Board board) {

        int heuristicValue = 0;

        Position[] bestPositions = {new Position(0,0), new Position(7, 0),
                new Position(0,7), new Position(7,7)};

        for (Position pos: bestPositions) {
            if (board.getTile(pos).getColor() == this.color) {
                heuristicValue += 5;
            } else {
                heuristicValue -= 5;
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
                heuristicValue -= 10;
            } else {
                heuristicValue += 10;
            }
        }

        return heuristicValue;
    }

    private Integer minMaxAlgorithm(Board node, Integer depth, boolean maximizingPlayer) {
        if (depth == 0 || node.gameIsOver()) {
            return heuristicMethod(node);
        }

        int value;
        if (maximizingPlayer) {
            value = -1000;
            node.calculatePossibleMoves(this.color);
            ArrayList<Position> listOfMoves = node.listOfAllEnabledPositions();

            for (Position listOfMove : listOfMoves) {
                Board copyOfNode = node.boardCopy();
                copyOfNode.makeMoveFromPosition(listOfMove, this.color);
                Integer score = minMaxAlgorithm(copyOfNode, depth - 1, false);
                if (value < score) {
                    value = score;
                }
            }
        } else {
            value = 1000;
            node.calculatePossibleMoves(this.otherColor);
            ArrayList<Position> listOfMoves = node.listOfAllEnabledPositions();

            for (Position listOfMove : listOfMoves) {
                Board copyOfNode = node.boardCopy();
                copyOfNode.makeMoveFromPosition(listOfMove, this.otherColor);
                Integer score = minMaxAlgorithm(copyOfNode, depth - 1, true);
                if (value > score) {
                    value = score;
                }
            }
        }
        return value;
    }
}
