package Game.GUI;

import javax.swing.JButton;

import Game.TileType;

public class ColorButton extends JButton {
    private TileType tileType;

    public ColorButton() {
        super();
        tileType = TileType.EMPTY;
    }

    public TileType getTileType() {
        return this.tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

}
