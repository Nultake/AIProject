package Game.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import Game.State;

public abstract class CostBasedAlgorithm extends Algorithm {

    protected HashMap<State[], Integer> fringe = new HashMap<>();

    protected ArrayList<State> expansionOrder = new ArrayList<>();

    public abstract int calculatePathCost(State[] nodePath);

    public State[] findSolutionPath() {
        State lastState = null;
        State[] statePath = null;

        push(new State[] { initalState });

        do {
            statePath = pop();
            lastState = statePath[statePath.length - 1];

            State[] possibleNextStates = getPossibleNextStates(lastState);

            if (possibleNextStates == null) {
                State[] expOrder = new State[expansionOrder.size()];
                int i = 0;
                for (State state : expansionOrder) {
                    expOrder[i] = state;
                    i++;
                }
                JOptionPane.showMessageDialog(null, "Limit Exceed",
                        "Limit Exceed!", JOptionPane.ERROR_MESSAGE);
                return expOrder;
            }

            for (State state : possibleNextStates) {
                List<State> states = new LinkedList<State>(Arrays.asList(statePath));

                states.add(state);

                State[] pushState = new State[states.size()];

                for (int i = 0; i < states.size(); i++) {
                    pushState[i] = states.get(i);
                }

                push(pushState);
            }

        } while (!lastState.isEqual(goalState));

        // Finish game
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

        expansionOrder.add(targetStatePath[targetStatePath.length - 1]);

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
