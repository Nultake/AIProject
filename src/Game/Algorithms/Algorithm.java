package Game.Algorithms;

import java.util.ArrayList;

import Game.MoveDirection;
import Game.State;
import Game.TileType;

public abstract class Algorithm {

    protected final int MEMORY_LIMIT = 25;
    protected final int STEP_LIMIT = 10;

    protected State initalState;
    protected State goalState;

    protected int stepLimitCounter = 0;

    private TileType[] order;

    private int cursorForOrder = 0;

    public abstract State[] findSolutionPath();

    protected State[] getPossibleNextStates(State state) {
        if (stepLimitCounter == 10)
            return null; // TODO : FINISH GAME OR RETURN ERROR MESSAGE TO USER;

        TileType[][] tiles = state.getTiles();

        TileType currentType = order[cursorForOrder];

        int[] currentTypeCoordinate = state.findTileCoordinate(currentType);
        // check if tile is correct possition for goal state
        if (currentTypeCoordinate == goalState.findTileCoordinate(currentType)) {
            updateCursor();
            return getPossibleNextStates(state);
        }

        stepLimitCounter++;

        ArrayList<State> possibleStates = new ArrayList<>();

        if (checkUpMove(state, currentTypeCoordinate)) {
            State newState = state.clone();

            newState.setCost(this.decideCost(currentType, MoveDirection.VERTICAL));

            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1]] = TileType.EMPTY;
            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1] - 1] = currentType;

            possibleStates.add(newState);
        }
        if (checkDownMove(state, currentTypeCoordinate)) {
            State newState = state.clone();

            newState.setCost(this.decideCost(currentType, MoveDirection.VERTICAL));

            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1]] = TileType.EMPTY;
            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1] + 1] = currentType;

            possibleStates.add(newState);
        }
        if (checkRightMove(state, currentTypeCoordinate)) {
            State newState = state.clone();

            newState.setCost(this.decideCost(currentType, MoveDirection.HORIZANTAL));

            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1]] = TileType.EMPTY;
            newState.getTiles()[currentTypeCoordinate[0] + 1][currentTypeCoordinate[1]] = currentType;

            possibleStates.add(newState);
        }
        if (checkLeftMove(state, currentTypeCoordinate)) {
            State newState = state.clone();

            newState.setCost(this.decideCost(currentType, MoveDirection.HORIZANTAL));

            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1]] = TileType.EMPTY;
            newState.getTiles()[currentTypeCoordinate[0] - 1][currentTypeCoordinate[1]] = currentType;

            possibleStates.add(newState);
        }

        return (State[]) possibleStates.toArray();

    }

    private boolean checkUpMove(State state, int[] coordinate) {
        int[] upCoordinate = new int[] { coordinate[0], coordinate[1] - 1 };

        if (coordinate[1] > 0 && state.findTileTypeByCoordinate(upCoordinate) == TileType.EMPTY)
            return true;

        return false;
    }

    private boolean checkDownMove(State state, int[] coordinate) {
        int[] downCoordinate = new int[] { coordinate[0], coordinate[1] + 1 };

        if (coordinate[1] < 2 && state.findTileTypeByCoordinate(downCoordinate) == TileType.EMPTY)
            return true;

        return false;
    }

    private boolean checkRightMove(State state, int[] coordinate) {
        int[] rightCoordinate = new int[] { coordinate[0] + 1, coordinate[1] };

        if (coordinate[0] > 0 && state.findTileTypeByCoordinate(rightCoordinate) == TileType.EMPTY)
            return true;

        return false;
    }

    private boolean checkLeftMove(State state, int[] coordinate) {
        int[] leftCoordinate = new int[] { coordinate[0] - 1, coordinate[1] };

        if (coordinate[0] < 2 && state.findTileTypeByCoordinate(leftCoordinate) == TileType.EMPTY)
            return true;

        return false;
    }

    private void updateCursor() {
        cursorForOrder++;
        if (cursorForOrder == 3)
            cursorForOrder = 0;
    }

    private int decideCost(TileType type, MoveDirection direction) {
        switch (type) {
            case RED:
                return 1;
            case GREEN:
                return direction == MoveDirection.VERTICAL ? 1 : 2;
            case BLUE:
                return direction == MoveDirection.VERTICAL ? 2 : 1;
        }

        return 0;
    }

}
