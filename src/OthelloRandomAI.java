import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class OthelloRandomAI extends OthelloSuperAI {

    public OthelloRandomAI(Board gameBoard, Color color) {
        this.board = gameBoard;
        this.color = color;
    }

    @Override
    public Position getMove() {
        Random rand = new Random();
        ArrayList<Position> list = this.board.listOfAllEnabledPositions();
        return list.get(rand.nextInt(list.size()));
    }
}
