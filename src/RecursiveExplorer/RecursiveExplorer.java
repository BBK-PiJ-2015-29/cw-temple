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
            visitNearest(state, parentNode, visited);
        }
    }
    /**
     * Recursive method to go down left subtree of ternary tree
     * @param parentNode the stack of parent nodes, push current state before moving.
     * @param visited a List of visited node ids
     * @return 1 if current state is the target, 0 if the node is a dead end.
     */
    private int visitNearest(ExplorationState state, Stack<Long> parentNode, List<Long> visited) {
        //get the neighbours of this current state, prioritised by distance and times visited
        PriorityQueue<NodeStatus> neighbours = sortNeighbours(state.getNeighbours(), visited);

        if (state.getDistanceToTarget() == 0) {
            return 1;
        }

        if (neighbours.size() == 0) {
            state.moveTo(parentNode.pop());
            visited.add(state.getCurrentLocation());
            return visitNearest(state, parentNode, visited);
        } else {
            parentNode.push(state.getCurrentLocation());
            state.moveTo(neighbours.peek().getId());
            visited.add(state.getCurrentLocation());

            return visitNearest(state, parentNode,visited);
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

