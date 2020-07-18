import java.awt.*;
import java.util.ArrayList;

abstract class OthelloSuperAI implements OthelloAI {
    protected Board board;
    protected Color color;
    public void makeMove(Position position) {
        board.makeMoveFromPosition(position, color);
    }
    public abstract Position evaluatePositions(ArrayList<Position> positions);
}
