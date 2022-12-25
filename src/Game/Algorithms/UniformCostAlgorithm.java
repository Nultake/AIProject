package Game.Algorithms;

import Game.State;

public class UniformCostAlgorithm extends CostBasedAlgorithm {

    @Override
    public int calculatePathCost(State[] nodePath) {
        int cost = 0;
        for (State state : nodePath)
            cost += state.getCost();

        return cost;

    }

}
