import java.awt.*;
import java.util.ArrayList;

/*
/ TODO: Fix heuristic function to actually make the AI try to win. Make "bad" tiles less bad the longer into the game it is.
 */
public class OthelloABPruningAI extends OthelloSuperAI {

    public OthelloABPruningAI(Board gameBoard, Color color) {
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

        int maximumScore = -10000;
        Position bestPosition = listOfMoves.get(0);

        for (Position move : listOfMoves) {
            Board copyOfBoard = this.board.boardCopy();
            copyOfBoard.makeMoveFromPosition(move, this.color);
            Integer depth = 6;
            Integer score = alphaBetaPruning(copyOfBoard, depth, -1000, 1000, false);
            if (maximumScore < score) {
                maximumScore = score;
                bestPosition = move;
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
                heuristicValue += 1000;
            } else {
                heuristicValue -= 1000;
            }
        }

        ArrayList<Position> goodPositions = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 2; j < 6; j++) {
                goodPositions.add(new Position(0, j));
                goodPositions.add(new Position(7, j));
                goodPositions.add(new Position(j, 0));
                goodPositions.add(new Position(j, 7));
                goodPositions.add(new Position(2, j));
                goodPositions.add(new Position(5, j));
                goodPositions.add(new Position(j, 2));
                goodPositions.add(new Position(j, 5));
            }
        }

        for (Position pos: goodPositions) {
            if (board.getTile(pos).getColor() == this.color) {
                heuristicValue += 10;
            } else {
                heuristicValue -= 10;
            }
        }

        ArrayList<Position> mehPositions = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 2; j < 6; j++) {
                mehPositions.add(new Position(1, j));
                mehPositions.add(new Position(6, j));
                mehPositions.add(new Position(j, 1));
                mehPositions.add(new Position(j, 6));
            }
        }

        for (Position pos: mehPositions) {
            if (board.getTile(pos).getColor() == this.color) {
                heuristicValue -= 5;
            } else {
                heuristicValue += 5;
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
                heuristicValue -= 100;
            } else {
                heuristicValue += 100;
            }
        }

        return heuristicValue;
    }


    private Integer alphaBetaPruning(Board node, Integer depth, int alpha, int beta, boolean maximizingPlayer) {
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
                Integer score = alphaBetaPruning(copyOfNode, depth - 1, alpha, beta, false);

                if (value < score) {
                    value = score;
                }
                if (alpha < value) {
                    alpha = value;
                }
                if (alpha >= beta) {
                    return value;
                }
            }
        } else {
            value = 1000;
            node.calculatePossibleMoves(this.otherColor);
            ArrayList<Position> listOfMoves = node.listOfAllEnabledPositions();

            for (Position listOfMove : listOfMoves) {
                Board copyOfNode = node.boardCopy();
                copyOfNode.makeMoveFromPosition(listOfMove, this.otherColor);
                Integer score = alphaBetaPruning(copyOfNode, depth - 1, alpha, beta, true);
                if (value > score) {
                    value = score;
                }
                if (beta > value) {
                    beta = value;
                }
                if (beta <= alpha) {
                    return value;
                }
            }
        }
        return value;
    }
}
