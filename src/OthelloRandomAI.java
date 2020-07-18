import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class OthelloRandomAI extends OthelloSuperAI {

    public OthelloRandomAI(Board gameBoard, Color color) {
        this.board = gameBoard;
        this.color = color;
    }

    @Override
    public Position evaluatePositions(ArrayList<Position> positions) {
        Random rand = new Random();
        return positions.get(rand.nextInt(positions.size()));
    }
}
