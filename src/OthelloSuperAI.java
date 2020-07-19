import java.awt.*;

abstract class OthelloSuperAI implements OthelloAI {
    protected Board board;
    protected Color color;
    protected Color otherColor;

    public abstract Position getMove();
}
