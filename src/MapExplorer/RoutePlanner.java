package MapExplorer;

import game.ExplorationState;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Oliver Coulson on 10/02/2016.
 */
public class RoutePlanner {
    private ExplorationState state;
    private Set<MapNode> seen;
    private Stack<MapNode> route;

    private MapNode currentNode;
    private MapNode nextNode = null;

    //private Set<MapNode> nearest = null;





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


//            nearest = seen.stream().filter(mapNode -> !mapNode.isVisited()).sorted(MapNode::compareTo).collect(Collectors.toSet());


            nextNode = findNextUnvisited();

//            if (nearest.stream().findAny().get().getDistance() - nextNode.getDistance() >= 5) {
//                backtrackToNearest();
//            }

            if (nextNode != null) {
                state.moveTo(nextNode.getId());
                setCurrentNode();
                route.push(currentNode);
            } else {
                backtrack();
            }
        }
    }

//    private void backtrackToNearest() {
//        while(currentNode.getNeighbours().stream().noneMatch(
//                mapNode -> mapNode.getDistance() == nearest.stream().findAny().get().getDistance())
//                ) {
//            route.pop();
//            state.moveTo(route.peek().getId());
//        }
//        state.moveTo(currentNode.getNeighbours().stream().filter(
//                mapNode -> (!mapNode.isVisited())
//                        && mapNode.getDistance() == nearest.stream().findAny().get().getDistance()).findFirst().get().getId());
//        setCurrentNode();
//        route.push(currentNode);
//    }


    private MapNode findNextUnvisited() {
        Set<MapNode> neighbours = currentNode.getNeighbours();
        if (neighbours.stream().anyMatch(mapNode -> !mapNode.isVisited())) {

            return neighbours.stream().filter(mapNode -> !mapNode.isVisited()).min(MapNode::compareTo).get();

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
