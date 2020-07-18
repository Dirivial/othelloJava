import java.awt.*;

public class Tile {
    private Position position;
    private Color color = Color.DARK_GRAY;
    private boolean enabled = false;

    public Tile(Position position) {
        this.color = Color.DARK_GRAY;
        this.position = position;
    }
    public Tile(Position position, Color color) {
        this.color = color;
        this.position = position;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColour(Color colour) {
        this.color = colour;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Position getPosition() {
        return this.position;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

}
