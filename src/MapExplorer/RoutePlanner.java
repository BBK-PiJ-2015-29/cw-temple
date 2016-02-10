package MapExplorer;

import game.ExplorationState;

import java.util.*;

/**
 * Created by Oliver Coulson on 10/02/2016.
 */
public class RoutePlanner {
    private ExplorationState state;
    private List<MapNode> seen;

    private MapNode currentNode;
    private MapNode nearestUnvisited;

    private Stack<MapNode> route;


    public RoutePlanner(ExplorationState state) {
        this.state = state;
        seen = new ArrayList<>();
        route = new Stack<>();
    }

    public void findOrb() {
        while (state.getDistanceToTarget() != 0) {
            setCurrentNode();

            MapNode next = null;
            if(nearestUnvisited != null) {
                next = nearestUnvisited;
            } else {
                next = chooseNext();
            }

            System.out.println(next.getId());
            System.out.println(currentNode.getId());

            if (currentNode.getNeighbours().contains(next)) {
                route.push(currentNode);
                state.moveTo(next.getId());
            } else {
                backtrack();
            }

        }

    }

    private void backtrack() {
            route.pop();
            state.moveTo(route.peek().getId());
    }

    private MapNode chooseNext() {
        Set<MapNode> neighbours = currentNode.getNeighbours();
        //If any neighbours are unvisited - choose the one closest to the exit
        if(neighbours.stream().anyMatch(mapNode -> !mapNode.isVisited())) {
            return currentNode.getNeighbours().stream().filter(mapNode ->
                    !mapNode.isVisited()).sorted(MapNode::compareTo).findFirst().get();
        } else {
            return findNearestUnvisited();
        }
    }

    private MapNode findNearestUnvisited() {
        MapNode next = seen.stream().filter(mapNode ->
                !mapNode.isVisited()).sorted(MapNode::compareTo).findFirst().get();
        nearestUnvisited = next;
        return next;
    }

    private void setCurrentNode() {
        if(!seen.isEmpty()) {
            if (seen.stream().anyMatch(mapNode -> mapNode.getId() == state.getCurrentLocation())) {

                currentNode = seen.stream().filter(mapNode ->
                        mapNode.getId() == state.getCurrentLocation()).findFirst().get();

                currentNode.setNeighbours(state.getNeighbours(), seen);
                currentNode.setVisited();
            } else {
                currentNode = createNode();
                seen.add(currentNode);
            }
        } else {
            currentNode = createNode();
            seen.add(currentNode);

        }
    }

    private MapNode createNode() {
        MapNode node = new MapNode(state.getCurrentLocation(), state.getDistanceToTarget());
        node.setNeighbours(state.getNeighbours(), seen);
        node.setVisited();
        return node;
    }
}
