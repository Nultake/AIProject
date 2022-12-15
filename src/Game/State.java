package Game;

/**
 * State
 */
public class State {
    private TileType[][] tiles;
    private int cost;

    public boolean isEqual(State state) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tiles[i][j] != state.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    public TileType[][] getTiles() {
        return this.tiles;
    }

    public int[] findTileCoordinate(TileType type) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tiles[i][j] == type)
                    return new int[] { i, j };
            }
        }
        // this state will not happen just to avoid compile error
        return new int[0];
    }

    public TileType findTileTypeByCoordinate(int[] coordinate) {
        return tiles[coordinate[0]][coordinate[1]];
    }

    public State clone() {
        State state = new State();
        state.tiles = this.tiles;

        return state;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return this.cost;
    }

}
