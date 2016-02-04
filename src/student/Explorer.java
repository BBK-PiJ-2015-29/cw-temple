package student;

import game.EscapeState;
import game.ExplorationState;
import game.Node;
import game.NodeStatus;

import java.util.*;

public class Explorer {

    /**
     * Explore the cavern, trying to find the orb in as few steps as possible.
     * Once you find the orb, you must return from the function in order to pick
     * it up. If you continue to move after finding the orb rather
     * than returning, it will not count.
     * If you return from this function while not standing on top of the orb,
     * it will count as a failure.
     * <p>
     * There is no limit to how many steps you can take, but you will receive
     * a score bonus multiplier for finding the orb in fewer steps.
     * <p>
     * At every step, you only know your current tile's ID and the ID of all
     * open neighbor tiles, as well as the distance to the orb at each of these tiles
     * (ignoring walls and obstacles).
     * <p>
     * To get information about the current state, use functions
     * getCurrentLocation(),
     * getNeighbours(), and
     * getDistanceToTarget()
     * in ExplorationState.
     * You know you are standing on the orb when getDistanceToTarget() is 0.
     * <p>
     * Use function moveTo(long id) in ExplorationState to move to a neighboring
     * tile by its ID. Doing this will change state to reflect your new position.
     * <p>
     * A suggested first implementation that will always find the orb, but likely won't
     * receive a large bonus multiplier, is a depth-first search.
     *
     * @param state the information available at the current state
     */
    public void explore(ExplorationState state) {
        //TODO : Explore the cavern and find the orb
        depthFirst(state);

    }
    private void depthFirst(ExplorationState state) {

        Stack<Long> parentNode = new Stack<>();
        parentNode.push(state.getCurrentLocation());
        state.moveTo(state.getCurrentLocation() + 1);

        if (state.getDistanceToTarget() == 0) {

        } else {
            int left = visitLeft(state, parentNode);
            //visitMiddle(state);
            //visitRight(state);
        }

    }

    /**
     * Recursive method to go down left subtree of ternary tree
     * @param state the current state before moving
     * @param parentNode the stack of parent nodes, push current state before moving.
     * @return 1 if current state is the target, 0 if the node is a dead end.
     */
    private int visitLeft(ExplorationState state, Stack<Long> parentNode) {
        PriorityQueue<NodeStatus> neighbours = sortNeighbours(state.getNeighbours());

        if (state.getDistanceToTarget() == 0) {
            return 1;
        }

        if (neighbours.size() == 1) {
            state.moveTo(parentNode.pop());
            return 0;
        } else {
            if (neighbours.peek().getId() == parentNode.peek()) {
                neighbours.poll();
            }
            parentNode.push(state.getCurrentLocation());
            state.moveTo(neighbours.peek().getId());
            return visitLeft(state, parentNode);
        }
    }


    /**
     * A method to sort the Collection<NodeStatus> which is returned by getNeighbours
     * by distance from the goal
     * @Param neighbours
     * @return a priority queue using the 'distanceToTarget' element as the priority
     */
    private PriorityQueue<NodeStatus> sortNeighbours(Collection<NodeStatus> neighbours) {
        PriorityQueue<NodeStatus> output = new PriorityQueueImpl<>();
        for (NodeStatus n: neighbours) {
            output.add(n, n.getDistanceToTarget());
        }
        return output;

    }

    /**
     * A debugging method to print current state's neighbours
     * @param state the current state
     */
    private void printState(ExplorationState state) {
        PriorityQueue<NodeStatus> neighbours = sortNeighbours(state.getNeighbours());
        if (neighbours.size() == 1) {
            System.out.println("Dead End");
        } else if (neighbours.size() == 2) {
            System.out.println("Number of Neighbours: " + neighbours.size());
            System.out.println("Nearest Neighbour: : " + neighbours.peek().getId() + " Distance: "
                    + neighbours.poll().getDistanceToTarget());
            System.out.println("Next Neighbour : " + neighbours.peek().getId() + " Distance: "
                    + neighbours.poll().getDistanceToTarget());
        } else if (neighbours.size() == 3) {
            System.out.println("Number of Neighbours: " + neighbours.size());
            System.out.println("Nearest Neighbour: : " + neighbours.peek().getId() + " Distance: "
                    + neighbours.poll().getDistanceToTarget());
            System.out.println("Next Neighbour: " + neighbours.peek().getId() + " Distance: "
                    + neighbours.poll().getDistanceToTarget());
            System.out.println("Last Neighbour: " + neighbours.peek().getId() + " Distance: "
                    + neighbours.poll().getDistanceToTarget());
        }

    }

    /**
     * Escape from the cavern before the ceiling collapses, trying to collect as much
     * gold as possible along the way. Your solution must ALWAYS escape before time runs
     * out, and this should be prioritized above collecting gold.
     * <p>
     * You now have access to the entire underlying graph, which can be accessed through EscapeState.
     * getCurrentNode() and getExit() will return you Node objects of interest, and getVertices()
     * will return a collection of all nodes on the graph.
     * <p>
     * Note that time is measured entirely in the number of steps taken, and for each step
     * the time remaining is decremented by the weight of the edge taken. You can use
     * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
     * on your current tile (this will fail if no such gold exists), and moveTo() to move
     * to a destination node adjacent to your current node.
     * <p>
     * You must return from this function while standing at the exit. Failing to do so before time
     * runs out or returning from the wrong location will be considered a failed run.
     * <p>
     * You will always have enough time to escape using the shortest path from the starting
     * position to the exit, although this will not collect much gold.
     *
     * @param state the information available at the current state
     */
    public void escape(EscapeState state) {
        //TODO: Escape from the cavern before time runs out
    }

}
