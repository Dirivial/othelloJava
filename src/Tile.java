import java.awt.*;

public class Tile {
    private Position position;
    private Color color = Color.DARK_GRAY;
    private boolean enabled = false;

    public Tile(Tile copy) {
        this.color = copy.color;
        this.position = copy.position;
    }

    public Tile(Position position, Color color) {
        this.color = color;
        this.position = position;
    }

    public Color getColor() {
        return this.color;
    }

    public Tile tileCopy() {
        return new Tile(this.position, this.color);
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
