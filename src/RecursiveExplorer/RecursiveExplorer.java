package RecursiveExplorer;

import game.ExplorationState;
import game.NodeStatus;
import student.PriorityQueue;
import student.PriorityQueueImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * Created by Oliver Coulson on 11/02/2016.
 */
public class RecursiveExplorer {
    private ExplorationState state;
    private Stack<Long> parentNode;
    private List<Long> visited;
    private long startState;

    public RecursiveExplorer(ExplorationState state) {
        this.state = state;
        parentNode = new Stack<>();
        visited = new ArrayList<>();
    }

    public void findOrb() {
        startState = state.getCurrentLocation();
        //Stack to keep track of the parent node when visiting a child
        //backtracking will involve popping from this stack
        parentNode.push(state.getCurrentLocation());

        //List to keep track of visited states
        visited.add(state.getCurrentLocation());

        if (state.getDistanceToTarget() != 0) {
            visitNearest();
        }
    }
    /**
     * Recursive method to go down left subtree of ternary tree
     * @return 1 if current state is the target, 0 if the node is a dead end.
     */
    private int visitNearest() {
        //get the neighbours of this current state, prioritised by distance and times visited
        PriorityQueue<NodeStatus> neighbours = sortNeighbours(state.getNeighbours(), visited);

        if (state.getDistanceToTarget() == 0) {
            return 1;
        }

        if (neighbours.size() == 0) {
            state.moveTo(parentNode.pop());
            visited.add(state.getCurrentLocation());
            return visitNearest();
        } else {
            NodeStatus first = neighbours.poll();
            NodeStatus second = null;
            long next = first.getId();
            if (neighbours.size() > 1) {
                second = neighbours.poll();
            }
            if (second != null && first.compareTo(second) == 0) {
                double random = Math.random();
                if(random >= 0.5) {
                    next = second.getId();
                }
            }
            parentNode.push(state.getCurrentLocation());
            state.moveTo(next);
            visited.add(state.getCurrentLocation());

            return visitNearest();
        }

    }

    /**
     * A method to sort the Collection<NodeStatus> which is returned by getNeighbours
     * by distance from the goal and also by times visited.
     * @Param neighbours
     * @return a priority queue using the 'distanceToTarget' and the number of times that neighbour has already been
     * visited,
     */
    private PriorityQueue<NodeStatus> sortNeighbours(Collection<NodeStatus> neighbours, List<Long> visited) {
        PriorityQueue<NodeStatus> output = new PriorityQueueImpl<>();
        for (NodeStatus n: neighbours) {
            if (!visited.contains(n.getId())) {
                output.add(n, n.getDistanceToTarget());
            }
        }
        return output;

    }
}

