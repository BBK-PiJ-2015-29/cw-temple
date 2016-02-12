package Escape;

import game.EscapeState;
import game.Node;
import game.Tile;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;


/**
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeArtist {
    private EscapeState state;
    private Set<EscapeNode> allNodes;
    private EscapeNode current;
    private EscapeNode start;
    private EscapeNode exit;

    private Stack<Node> route;



    public EscapeArtist(EscapeState state) {
        this.state = state;
        route = new Stack<>();
        allNodes = new HashSet<>();
    }

    public void cheeseIt() {
        //Input check message
        System.out.println("Cheesing it...");

        findShortestPath();
        System.out.println(route.size());

        while(!route.isEmpty()) {
            state.moveTo(route.pop());
            Tile thisTile = state.getCurrentNode().getTile();
            if(thisTile.getOriginalGold() != 0 && thisTile.getGold() != 0) {
                state.pickUpGold();
            }
        }
    }
    private void findShortestPath() {
        //Create a set of all nodes using wrapper class;
        //Set the distance from start to max, or 0 if current
        //Set visited to false, or true if current
        System.out.println(1);
        for (Node n : state.getVertices()) {
            EscapeNode escapeNode = new EscapeNode(n);
            if(n.equals(state.getCurrentNode())) {
                escapeNode.setDistance(0);
                escapeNode.setVisited(true);
                start = escapeNode;

            } else {
                escapeNode.setDistance(Double.MAX_VALUE);
                escapeNode.setVisited(false);
                if (n.equals(state.getExit())) {
                    exit = escapeNode;
                }
            }
            allNodes.add(escapeNode);
        }
        System.out.println(2);
        current = start;
        EscapeNode closest = null;

        while (!exit.isVisited()) {
            //check distances to each neighbour and if bigger than current node distance plus distance to neighbour,
            //set neighbour distance to that.
            System.out.println("a");
            Set<EscapeNode> neighbours = current.getNeighbours(allNodes);

            if(neighbours != null) {
                for (EscapeNode e : neighbours) {
                    if (e.isVisited()) {
                        continue;
                    }
                    System.out.println("b");
                    double routeDistanceToEscapeNode = current.getDistance() + current.getEdge(e).length;
                    if (e.getDistance() > routeDistanceToEscapeNode) {
                        e.setDistance(routeDistanceToEscapeNode);
                        e.setPrevious(current);
                    }
                }

            }
            System.out.println("c");
            current.setVisited(true);

            current = allNodes.stream().filter(escapeNode -> !escapeNode.isVisited()).min(EscapeNode::compareTo).get();
        }
        System.out.println(3);
        EscapeNode routeNode = exit;
        route.push(routeNode.getNode());
        while(routeNode.getPrevious() != start) {
            route.push(routeNode.getPrevious().getNode());
            routeNode = routeNode.getPrevious();
        }
        System.out.println(4);
    }
}


