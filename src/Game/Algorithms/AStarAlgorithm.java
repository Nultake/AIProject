package Game.Algorithms;

import Game.State;
import Game.TileType;

public class AStarAlgorithm extends CostBasedAlgorithm {

    @Override
    public int calculatePathCost(State[] nodePath) {
        int cost = 0;

        for (State state : nodePath) {
            cost += state.getCost();
        }
        cost += this.heuristicCalculation(nodePath[nodePath.length - 1], this.goalState);

        return cost;
    }

    private int heuristicCalculation(State state, State goalState) {
        int cost = 0;

        int[] currentStateRedCoordinate = state.findTileCoordinate(TileType.RED);
        int[] currentStateGreenCoordinate = state.findTileCoordinate(TileType.GREEN);
        int[] currentStateBlueCoordinate = state.findTileCoordinate(TileType.BLUE);

        int[] goalStateRedCoordinate = goalState.findTileCoordinate(TileType.RED);
        int[] goalStateGreenCoordinate = goalState.findTileCoordinate(TileType.GREEN);
        int[] goalStateBlueCoordinate = goalState.findTileCoordinate(TileType.BLUE);

        if (!checkIfCoordinatesEqual(currentStateRedCoordinate, goalStateRedCoordinate))
            cost++;
        if (!checkIfCoordinatesEqual(currentStateGreenCoordinate, goalStateGreenCoordinate))
            cost++;
        if (!checkIfCoordinatesEqual(currentStateBlueCoordinate, goalStateBlueCoordinate))
            cost++;

        return cost;
    }

    private boolean checkIfCoordinatesEqual(int[] coor1, int[] coor2) {
        return (coor1[0] == coor2[0] && coor1[1] == coor2[1]);
    }
}
