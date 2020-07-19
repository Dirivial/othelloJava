import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {

    private ArrayList<ArrayList<Tile>> gameBoard = new ArrayList<>();
    private int cols;
    private int rows;


    public Board(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        for (int i = 0; i < cols; i++) {
            ArrayList<Tile> column = new ArrayList<>();
            gameBoard.add(column);
            for (int j = 0; j < rows; j++) {
                Position tilePos = new Position(i, j);
                Tile row = new Tile(tilePos, Color.DARK_GRAY);
                gameBoard.get(i).add(row);
            }
        }
    }

    /*
    public Board(Board copy) {
        this.gameBoard = copy.gameBoard;
        for (int i = 0; i < this.cols; i++) {
            copy.gameBoard.clear();
            for (int j = 0; j < this.rows; j++) {
                copy.gameBoard.get(i).add(j, new Tile(this.gameBoard.get(i).get(j)));
            }
        }
    }
     */

    public Board boardCopy() {
        Board copy = new Board(this.cols, this.rows);

        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                Position pos = new Position(i, j);
                copy.getTile(pos).setColour(this.getTile(pos).getColor());
            }
        }
        return copy;
    }



    /**
     * Updates @this.gameBoard from a different @gameBoard.
     * @param gameBoard board to update tiles from
     */
    public void updateBoardFromGameBoard(ArrayList<ArrayList<JButton>> gameBoard) {
        for (int i = 0; i < gameBoard.size(); i++) {
            for (int j = 0; j < gameBoard.get(i).size(); j++) {

                JButton button = gameBoard.get(i).get(j);
                Tile tile = this.gameBoard.get(i).get(j);

                tile.setColour(button.getBackground());
                tile.setEnabled(button.isEnabled());
            }
        }
    }

    /**
     * Updates the enabled status on all buttons from the tiles
     * @param gameBoard
     */
    public void updateEnableStatus(ArrayList<ArrayList<JButton>> gameBoard) {
        for (int i = 0; i < gameBoard.size(); i++) {
            for (int j = 0; j < gameBoard.get(i).size(); j++) {

                JButton button = gameBoard.get(i).get(j);
                Tile tile = this.gameBoard.get(i).get(j);

                button.setEnabled(tile.getEnabled());
            }
        }
    }

    /**
     * Updates the colors on a board to the ones on @this.gameBoard
     * @param gameBoard
     */
    public void updateColorsOnGameBoard(ArrayList<ArrayList<JButton>> gameBoard) {
        for (int i = 0; i < gameBoard.size(); i++) {
            for (int j = 0; j < gameBoard.get(i).size(); j++) {
                gameBoard.get(i).get(j).setBackground(getTile(new Position(i, j)).getColor());
            }
        }
    }

    /**
     * Finds tiles to paint from the given buttons position and paints them
     * @param playerColor
     * @param gameBoard
     * @param button
     */
    public void makeMoveFromButton(Color playerColor, ArrayList<ArrayList<JButton>> gameBoard, JButton button) {
        for (int i = 0; i < gameBoard.size(); i++) {
            for (int j = 0; j < gameBoard.get(i).size(); j++) {
                if (gameBoard.get(i).get(j).equals(button)) {
                    calculateTile(new Position(i, j), playerColor, true);
                    getTile(new Position(i, j)).setColour(playerColor);
                }
            }
        }
    }

    /**
     * Finds tiles to paint from the given position and paints them
     * @param position
     * @param playerColor
     */
    public void makeMoveFromPosition(Position position, Color playerColor) {
        calculateTile(position, playerColor, true);
        getTile(position).setColour(playerColor);
    }

    /**
     * Enables all tiles that are possible as a move.
     * @param playerColor
     */
    public void calculatePossibleMoves(Color playerColor) {
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                if (this.gameBoard.get(i).get(j).getColor() == Color.DARK_GRAY) {
                    calculateTile(new Position(i, j), playerColor, false);
                }
            }
        }
    }

    /**
     *
     */
    public ArrayList<Position> listOfAllEnabledPositions() {
        ArrayList<Position> enabledPositions = new ArrayList<>();

        for (ArrayList<Tile> arrList: this.gameBoard) {
            for (Tile tile: arrList) {
                if (tile.getEnabled()) {
                    enabledPositions.add(tile.getPosition());
                }
            }
        }
        return enabledPositions;
    }


    /**
     * calculates if a tile is a possible move, if paint == true the method finds the tiles to paint and paints them.
     * @param position original position.
     * @param playerColor color of the current player.
     * @param paint true if method should calculate tiles to paint and paint them.
     */
    private void calculateTile(Position position, Color playerColor, boolean paint) {
        ArrayList<String> possibleDirections = possibleDirections(position);
        ArrayList<Tile> tilesToPaint = new ArrayList<>();
        Color otherColor;
        if (playerColor == Color.BLUE) {
            otherColor = Color.RED;
        } else {
            otherColor = Color.BLUE;
        }

        Tile nextTile;

        for (String dir: possibleDirections) {

            if (dir.equals("NORTH")) {
                nextTile = getTile(position.getPosToNorth());
                tilesToPaint.add(nextTile);
                while (nextTile.getPosition().getY() > 0 && nextTile.getColor() == otherColor) {
                    nextTile = getTile(nextTile.getPosition().getPosToNorth());
                    if (nextTile.getColor() == playerColor) {
                        if (paint) {
                            paintTiles(tilesToPaint, playerColor);
                        } else {
                            getTile(position).setEnabled(true);
                            return;
                        }
                    }
                    tilesToPaint.add(nextTile);
                }
                tilesToPaint.clear();
            }
            if (dir.equals("SOUTH")) {
                nextTile = getTile(position.getPosToSouth());
                tilesToPaint.add(nextTile);
                while (nextTile.getPosition().getY()+1 < this.rows && nextTile.getColor() == otherColor) {
                    nextTile = getTile(nextTile.getPosition().getPosToSouth());
                    if (nextTile.getColor() == playerColor) {
                        if (paint) {
                            paintTiles(tilesToPaint, playerColor);
                        } else {
                            getTile(position).setEnabled(true);
                            return;
                        }
                    }
                    tilesToPaint.add(nextTile);
                }
                tilesToPaint.clear();
            }
            if (dir.equals("EAST")) {
                nextTile = getTile(position.getPosToEast());
                tilesToPaint.add(nextTile);
                while (nextTile.getPosition().getX()+1 < this.cols && nextTile.getColor() == otherColor) {
                    nextTile = getTile(nextTile.getPosition().getPosToEast());
                    if (nextTile.getColor() == playerColor) {
                        if (paint) {
                            paintTiles(tilesToPaint, playerColor);
                        } else {
                            getTile(position).setEnabled(true);
                            return;
                        }
                    }
                    tilesToPaint.add(nextTile);
                }
                tilesToPaint.clear();
            }
            if (dir.equals("WEST")) {
                nextTile = getTile(position.getPosToWest());
                tilesToPaint.add(nextTile);
                while (nextTile.getPosition().getX() > 0 && nextTile.getColor() == otherColor) {
                    nextTile = getTile(nextTile.getPosition().getPosToWest());
                    if (nextTile.getColor() == playerColor) {
                        if (paint) {
                            paintTiles(tilesToPaint, playerColor);
                        } else {
                            getTile(position).setEnabled(true);
                            return;
                        }
                    }
                    tilesToPaint.add(nextTile);
                }
                tilesToPaint.clear();
            }
            if (dir.equals("NORTHWEST")) {
                nextTile = getTile(position.getPosToNorthWest());
                tilesToPaint.add(nextTile);
                while (nextTile.getPosition().getX() > 0 && nextTile.getPosition().getY() > 0 && nextTile.getColor() == otherColor) {
                    nextTile = getTile(nextTile.getPosition().getPosToNorthWest());
                    if (nextTile.getColor() == playerColor) {
                        if (paint) {
                            paintTiles(tilesToPaint, playerColor);
                        } else {
                            getTile(position).setEnabled(true);
                            return;
                        }
                    }
                    tilesToPaint.add(nextTile);
                }
                tilesToPaint.clear();
            }
            if (dir.equals("NORTHEAST")) {
                nextTile = getTile(position.getPosToNorthEast());
                tilesToPaint.add(nextTile);
                while (nextTile.getPosition().getX()+1 < this.cols && nextTile.getPosition().getY() > 0 && nextTile.getColor() == otherColor) {
                    nextTile = getTile(nextTile.getPosition().getPosToNorthEast());
                    if (nextTile.getColor() == playerColor) {
                        if (paint) {
                            paintTiles(tilesToPaint, playerColor);
                        } else {
                            getTile(position).setEnabled(true);
                            return;
                        }
                    }
                    tilesToPaint.add(nextTile);
                }
                tilesToPaint.clear();
            }
            if (dir.equals("SOUTHWEST")) {
                nextTile = getTile(position.getPosToSouthWest());
                tilesToPaint.add(nextTile);
                while (nextTile.getPosition().getX() > 0 && nextTile.getPosition().getY()+1 < this.rows && nextTile.getColor() == otherColor) {
                    nextTile = getTile(nextTile.getPosition().getPosToSouthWest());
                    if (nextTile.getColor() == playerColor) {
                        if (paint) {
                            paintTiles(tilesToPaint, playerColor);
                        } else {
                            getTile(position).setEnabled(true);
                            return;
                        }
                    }
                    tilesToPaint.add(nextTile);
                }
                tilesToPaint.clear();
            }
            if (dir.equals("SOUTHEAST")) {
                nextTile = getTile(position.getPosToSouthEast());
                tilesToPaint.add(nextTile);
                while (nextTile.getPosition().getX()+1 < this.cols && nextTile.getPosition().getY()+1 < this.rows && nextTile.getColor() == otherColor) {
                    nextTile = getTile(nextTile.getPosition().getPosToSouthEast());
                    if (nextTile.getColor() == playerColor) {
                        if (paint) {
                            paintTiles(tilesToPaint, playerColor);
                        } else {
                            getTile(position).setEnabled(true);
                            return;
                        }
                    }
                    tilesToPaint.add(nextTile);
                }
                tilesToPaint.clear();
            }
        }
    }

    /**
     * paints given tiles to a color.
     * @param tilesToPaint array of tiles to paint to a color.
     * @param color color to use.
     */
    private void paintTiles(ArrayList<Tile> tilesToPaint, Color color) {
        for (Tile tile : tilesToPaint) {
            tile.setColour(color);
        }
    }

    /**
     * Creates an array with two elements, arr[0] contains number
     * of blue markers, arr[1] contains number of red markers.
     * @return an array according to description.
     */
    
    public ArrayList<Integer> calculateMarkers() {
        ArrayList<Integer> numOfAColor = new ArrayList<>();
        numOfAColor.add(0);
        numOfAColor.add(0);
        for (ArrayList<Tile> arrList: this.gameBoard) {
            for (Tile tile: arrList) {
                if (tile.getColor() == Color.BLUE) {
                    numOfAColor.set(0, numOfAColor.get(0) + 1);

                }
                if (tile.getColor() == Color.RED) {
                    numOfAColor.set(1, numOfAColor.get(1) + 1);
                }
            }
        }
        return numOfAColor;
    }

    /**
     * Looks for interesting directions e.g. a direction that worthy of checking out.
     * @param position a position to investigate
     * @return Array of possible directions
     */
    public ArrayList<String> possibleDirections(Position position) {
        ArrayList<String> directions = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        if (y-1 > 0) {
            directions.add("NORTH");
            if (x-1 > 0) {
                directions.add("NORTHWEST");
            }
            if (x+2 < this.cols) {
                directions.add("NORTHEAST");
            }
        }
        if (y+2 < this.rows) {
            directions.add("SOUTH");
            if (x-1 > 0) {
                directions.add("SOUTHWEST");
            }
            if (x+2 < this.cols) {
                directions.add("SOUTHEAST");
            }
        }
        if (x-1 > 0) {
            directions.add("WEST");
        }
        if (x+2 < this.cols) {
            directions.add("EAST");
        }
        return directions;
    }

    /**
     * returns true if there are any playable moves
     * @return boolean
     */
    public boolean playerHasMoves() {
        for (ArrayList<Tile> arrList: this.gameBoard) {
            for (Tile tile: arrList) {
                if (tile.getEnabled()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean gameIsOver() {
        boolean[] boolArray = {true, true, true};
        for (ArrayList<Tile> arrList: this.gameBoard) {
            for (Tile tile: arrList) {
                if (tile.getColor().equals(Color.DARK_GRAY)) {
                    boolArray[0] = false;
                }
                if (tile.getColor().equals(Color.BLUE)) {
                    boolArray[1] = false;
                }
                if (tile.getColor().equals(Color.RED)) {
                    boolArray[2] = false;
                }
            }
        }
        if (boolArray[0] || boolArray[1] || boolArray[2]) {
            return true;
        } else {
            return false;
        }
    }

    public Tile getTile(Position position) {
        return gameBoard.get(position.getX()).get(position.getY());
    }

}
