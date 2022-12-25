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

    public abstract State[] findSolutionPath();

    protected State[] getPossibleNextStates(State state) {
        if (stepLimitCounter == STEP_LIMIT) {
            return null;
        }

        TileType currentType = order[findCursor(state)];

        int[] currentTypeCoordinate = state.findTileCoordinate(currentType);

        // check if tile is correct possition for goal state
        if ((currentTypeCoordinate[0] == goalState.findTileCoordinate(currentType)[0])
                && (currentTypeCoordinate[1] == goalState
                        .findTileCoordinate(currentType)[1])
                && !state.isEqual(goalState)) {
            state.setLastMovedTile(currentType);
            return getPossibleNextStates(state);
        }

        stepLimitCounter++;

        ArrayList<State> possibleStates = new ArrayList<>();

        if (checkUpMove(state, currentTypeCoordinate)) {
            State newState = state.clone();

            newState.setCost(this.decideCost(currentType, MoveDirection.VERTICAL));

            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1]] = TileType.EMPTY;
            newState.getTiles()[currentTypeCoordinate[0] - 1][currentTypeCoordinate[1]] = currentType;
            newState.setLastMovedTile(currentType);

            possibleStates.add(newState);
        }
        if (checkDownMove(state, currentTypeCoordinate)) {
            State newState = state.clone();

            newState.setCost(this.decideCost(currentType, MoveDirection.VERTICAL));

            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1]] = TileType.EMPTY;
            newState.getTiles()[currentTypeCoordinate[0] + 1][currentTypeCoordinate[1]] = currentType;
            newState.setLastMovedTile(currentType);

            possibleStates.add(newState);
        }
        if (checkRightMove(state, currentTypeCoordinate)) {
            State newState = state.clone();

            newState.setCost(this.decideCost(currentType, MoveDirection.HORIZANTAL));

            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1]] = TileType.EMPTY;
            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1] + 1] = currentType;
            newState.setLastMovedTile(currentType);

            possibleStates.add(newState);
        }
        if (checkLeftMove(state, currentTypeCoordinate)) {
            State newState = state.clone();

            newState.setCost(this.decideCost(currentType, MoveDirection.HORIZANTAL));

            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1]] = TileType.EMPTY;
            newState.getTiles()[currentTypeCoordinate[0]][currentTypeCoordinate[1] - 1] = currentType;
            newState.setLastMovedTile(currentType);

            possibleStates.add(newState);
        }

        State[] returnArray = new State[possibleStates.size()];

        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = possibleStates.get(i);
        }
        return returnArray;

    }

    private boolean checkUpMove(State state, int[] coordinate) {
        if ((coordinate[0] - 1) == -1)
            return false;

        int[] upCoordinate = new int[] { coordinate[0] - 1, coordinate[1] };

        if (coordinate[0] > 0 && state.findTileTypeByCoordinate(upCoordinate) == TileType.EMPTY)
            return true;

        return false;
    }

    private boolean checkDownMove(State state, int[] coordinate) {
        if ((coordinate[0] + 1) == 3)
            return false;

        int[] downCoordinate = new int[] { coordinate[0] + 1, coordinate[1] };

        if (coordinate[0] < 2 && state.findTileTypeByCoordinate(downCoordinate) == TileType.EMPTY)
            return true;

        return false;
    }

    private boolean checkRightMove(State state, int[] coordinate) {
        if ((coordinate[1] + 1) == 3)
            return false;

        int[] rightCoordinate = new int[] { coordinate[0], coordinate[1] + 1 };

        if (coordinate[1] < 2 && state.findTileTypeByCoordinate(rightCoordinate) == TileType.EMPTY)
            return true;

        return false;
    }

    private boolean checkLeftMove(State state, int[] coordinate) {
        if ((coordinate[1] - 1) == -1)
            return false;

        int[] leftCoordinate = new int[] { coordinate[0], coordinate[1] - 1 };

        if (coordinate[1] > 0 && state.findTileTypeByCoordinate(leftCoordinate) == TileType.EMPTY)
            return true;

        return false;
    }

    private int decideCost(TileType type, MoveDirection direction) {
        switch (type) {
            case RED:
                return 1;
            case GREEN:
                return direction == MoveDirection.HORIZANTAL ? 1 : 2;
            case BLUE:
                return direction == MoveDirection.HORIZANTAL ? 2 : 1;
            default:
                break;
        }

        return 0;
    }

    public void setInitialState(State initialState) {
        this.initalState = initialState;
    }

    public void setGoalState(State goalState) {
        this.goalState = goalState;
    }

    public void setOrder(TileType[] order) {
        this.order = order;
    }

    private int findCursor(State state) {
        if (state.getLastMovedTile() == null)
            return 0;

        int i = 0;

        for (TileType tileType : order) {
            if (tileType == state.getLastMovedTile()) {
                i++;
                return i == 3 ? 0 : i;
            }

            i++;
        }

        return 0;
    }
}
