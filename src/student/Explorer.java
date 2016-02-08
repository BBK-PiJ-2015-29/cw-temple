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
        //recursiveTraversal1(state);
        depthFirst(state);

    }

    /**
     * An implementation of the Depth First graph traversal algorithm
     * @param state the current state of the game, i.e. where the explorer is at the start
     */
    private void depthFirst(ExplorationState state) {
        Stack<Long> stack = new Stack<>();
        List<Long> visited = new ArrayList<>();
        stack.push(state.getCurrentLocation());
        visited.add(state.getCurrentLocation());
        NodeStatus next;
        while (!stack.isEmpty()) {
            if (state.getDistanceToTarget() == 0) {
                break;
            }
            Collection<NodeStatus> neighbours = state.getNeighbours();
            next = null;

            //Filter for unvisited child nodes, and sort by distance to target
            if (neighbours.stream().filter(nodeStatus1 -> !visited.contains(nodeStatus1.getId())).count() != 0) {
                next = neighbours.stream().filter(nodeStatus -> !visited.contains(nodeStatus.getId())).sorted((a,b) -> {
                    int compare = Long.compare(a.getDistanceToTarget(), b.getDistanceToTarget());
                    if (compare == 0) {
                        double random = Math.random();
                        return (random >= 0.5) ? 1 : -1;
                    } else {
                        return compare;
                    }
                }).findFirst().get();
            }

            if (next != null) {
                state.moveTo(next.getId());
                visited.add(state.getCurrentLocation());
                System.out.println("Current: " + state.getCurrentLocation());
                stack.push(state.getCurrentLocation());
            } else {
                stack.pop();
                state.moveTo(stack.peek());
            }

        }
    }
    private void recursiveTraversal1(ExplorationState state) {

        //Stack to keep track of the parent node when visiting a child
        //backtracking will involve popping from this stack
        Stack<Long> parentNode = new Stack<>();
        parentNode.push(state.getCurrentLocation());

        //List to keep track of visited states to avoid loops
        List<Long> visited = new ArrayList<>();
        visited.add(state.getCurrentLocation());

        //move to first square which is always startId + 1
        state.moveTo(state.getCurrentLocation() + 1);
        visited.add(state.getCurrentLocation());

        if (state.getDistanceToTarget() == 0) {

        } else {
            visitNearest(state, parentNode, visited);
        }

    }

    /**
     * Recursive method to go down left subtree of ternary tree
     * @param state the current state before moving
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

        if (neighbours.size() == 1 ) {
            state.moveTo(parentNode.pop());
            visited.add(state.getCurrentLocation());
            return visitNearest(state, parentNode, visited);
        } else {
            parentNode.push(state.getCurrentLocation());
            state.moveTo(neighbours.peek().getId());
            visited.add(state.getCurrentLocation());

            return visitNearest(state,parentNode,visited);
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
            long timesVisited = visited.stream().filter(aLong -> aLong == n.getId()).count();
            // the priority is the distance to the target multiplied by times visited (+1 in case visited is zero -
            // making the priority of nearest and never visited 1)
            output.add(n, (n.getDistanceToTarget() * timesVisited) + 1);
        }
        return output;

    }

//    /**
//     * A debugging method to print current state's neighbours
//     * @param state the current state
//     */
//    private void printState(ExplorationState state) {
//        PriorityQueue<NodeStatus> neighbours = sortNeighbours(state.getNeighbours());
//        if (neighbours.size() == 1) {
//            System.out.println("Dead End");
//        } else if (neighbours.size() == 2) {
//            System.out.println("Number of Neighbours: " + neighbours.size());
//            System.out.println("Nearest Neighbour: : " + neighbours.peek().getId() + " Distance: "
//                    + neighbours.poll().getDistanceToTarget());
//            System.out.println("Next Neighbour : " + neighbours.peek().getId() + " Distance: "
//                    + neighbours.poll().getDistanceToTarget());
//        } else if (neighbours.size() == 3) {
//            System.out.println("Number of Neighbours: " + neighbours.size());
//            System.out.println("Nearest Neighbour: : " + neighbours.peek().getId() + " Distance: "
//                    + neighbours.poll().getDistanceToTarget());
//            System.out.println("Next Neighbour: " + neighbours.peek().getId() + " Distance: "
//                    + neighbours.poll().getDistanceToTarget());
//            System.out.println("Last Neighbour: " + neighbours.peek().getId() + " Distance: "
//                    + neighbours.poll().getDistanceToTarget());
//        }
//
//    }

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
