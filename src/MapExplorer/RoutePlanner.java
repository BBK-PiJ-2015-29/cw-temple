package MapExplorer;

import game.ExplorationState;

import java.util.*;

/**
 * Created by Oliver Coulson on 10/02/2016.
 */
public class RoutePlanner {
    private ExplorationState state;
    private Set<MapNode> seen;
    private Stack<MapNode> route;

    private MapNode currentNode;
    private MapNode nextNode = null;




    public RoutePlanner(ExplorationState state) {
        this.state = state;
        seen = new HashSet<>();
        route = new Stack<>();
    }

    public void findOrb() {
        setCurrentNode();
        route.push(currentNode);

        while (true) {
            if (state.getDistanceToTarget() == 0) {
                break;
            }

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


    private MapNode findNextUnvisited() {
        Set<MapNode> neighbours = currentNode.getNeighbours();
        if (neighbours.stream().anyMatch(mapNode -> !mapNode.isVisited())) {
            return neighbours.stream().filter(mapNode -> !mapNode.isVisited()).sorted(MapNode::compareTo).findFirst().get();

        } else {
            return null;
        }

    }

    private void backtrack() {
        route.pop();
        state.moveTo(route.peek().getId());
        setCurrentNode();
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
