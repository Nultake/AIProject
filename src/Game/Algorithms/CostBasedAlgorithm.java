package Game.Algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Game.State;

public abstract class CostBasedAlgorithm extends Algorithm {

    protected HashMap<State[], Integer> fringe = new HashMap<>();

    protected abstract int calculatePathCost(State[] nodePath);

    public State[] findSolutionPath() {
        State lastState = null;
        State[] statePath = null;

        push(new State[] { initalState });

        do {
            statePath = pop();
            lastState = statePath[statePath.length - 1];

            State[] possibleNextStates = getPossibleNextStates(lastState);

            for (State state : possibleNextStates) {
                List<State> states = Arrays.asList(statePath);

                states.add(state);

                push((State[]) states.toArray());
            }
        } while (!lastState.isEqual(goalState));

        // TODO:Finish game

        return statePath;
    }

    private State[] pop() {
        int minValue = Integer.MAX_VALUE;

        for (Integer cost : this.fringe.values()) {
            if (cost < minValue)
                minValue = cost;
        }

        State[] targetStatePath = null;

        for (State[] statePath : this.fringe.keySet()) {
            if (fringe.get(statePath) == minValue) {
                targetStatePath = statePath;
                break;
            }
        }

        fringe.remove(targetStatePath);

        return targetStatePath;
    }

    private void push(State[] nodePath) {
        if (this.fringe.size() == this.MEMORY_LIMIT) {
            int maxValue = Integer.MIN_VALUE;

            for (int cost : this.fringe.values()) {
                if (cost > maxValue)
                    maxValue = cost;
            }

            State[] targetStatePath = null;

            for (State[] statePath : this.fringe.keySet()) {
                if (fringe.get(statePath) == maxValue) {
                    targetStatePath = statePath;
                    break;
                }
            }

            fringe.remove(targetStatePath);
        }

        fringe.put(nodePath, calculatePathCost(nodePath));
    }

}
