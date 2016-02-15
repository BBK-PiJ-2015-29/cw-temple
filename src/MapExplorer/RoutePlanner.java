package MapExplorer;

import game.ExplorationState;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class to run the Explore part of the task. Using an iterative implementation of the depth-first graph traversal
 * algorithm.
 * Created by Oliver Coulson on 10/02/2016.
 */
public class RoutePlanner {
    private ExplorationState state;
    private Set<MapNode> seen;
    private Stack<MapNode> route;

    private MapNode currentNode;
    private MapNode nextNode = null;

    private MapNode nearest = null;





    public RoutePlanner(ExplorationState state) {
        this.state = state;
        seen = new HashSet<>();
        route = new Stack<>();
    }

    /**
     * The method which is called to find the orb, by the Explorer class.
     */
    public void findOrb() {
        setCurrentNode();
        route.push(currentNode);

        while (true) {
            if (state.getDistanceToTarget() == 0) {
                break;
            }


            nearest = seen.stream()
                    .filter(mapNode -> !mapNode.isVisited())
                    .sorted(MapNode::compareTo)
                    .findFirst()
                    .get();


            nextNode = findNextUnvisited();

            if (nextNode != null) {
                state.moveTo(nextNode.getId());
                setCurrentNode();
                route.push(currentNode);
            } else {
                backtrack();
            }
        }
    }


    /**
     * A method to search the current node's neighbours and find the nearest unvisited one
     * @return the nearest unvisited neighbour or null if all neighbours are visited.
     */
    private MapNode findNextUnvisited() {
        Set<MapNode> neighbours = currentNode.getNeighbours();
        if (neighbours.stream().anyMatch(mapNode -> !mapNode.isVisited())) {

            return neighbours.stream().filter(mapNode -> !mapNode.isVisited()).min(MapNode::compareTo).get();

        } else {

            return null;
        }

    }

    /**
     * A method which backtracks to the parent node of the current. It is called if there are no current unvisited
     * neighbours.
     */
    private void backtrack() {
        route.pop();
        state.moveTo(route.peek().getId());
        setCurrentNode();
    }


    /**
     * A method to assign the current MapNode node from the current state.getCurrentLocation().
     * If the node already exists in the set of all seen nodes, that one is used instead of creating a new one.
     *
     */
    private void setCurrentNode() {
        if(!seen.isEmpty()) {
            if (seen.stream().anyMatch(mapNode -> mapNode.getId() == state.getCurrentLocation())) {

                currentNode = seen.stream().filter(mapNode ->
                        mapNode.getId() == state.getCurrentLocation()).findFirst().get();

                currentNode.setNeighbours(state.getNeighbours(), seen);
                currentNode.setVisited();
            } else {
                currentNode = createNode();

            }
        } else {
            currentNode = createNode();

        }
    }

    /**
     * A method to create a new MapNode object for the current state.
     * @return
     */
    private MapNode createNode() {
        MapNode node = new MapNode(state.getCurrentLocation(), state.getDistanceToTarget());
        node.setNeighbours(state.getNeighbours(), seen);
        node.setVisited();
        seen.add(node);
        return node;
    }
}
