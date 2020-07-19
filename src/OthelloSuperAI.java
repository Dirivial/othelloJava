import java.awt.*;
import java.util.ArrayList;

abstract class OthelloSuperAI implements OthelloAI {
    protected Board board;
    protected Color color;
    protected Color otherColor;
    public void makeMove(Position position) {
        board.makeMoveFromPosition(position, color);
    }
    public abstract Position getMove();
}
