package Escape;

import game.EscapeState;
import game.Node;
import game.Tile;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;


/**
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeArtist {
    private EscapeState state;

    private EscapeNode current;
    private EscapeNode start;
    private EscapeNode destination;

    private Stack<Node> route;



    public EscapeArtist(EscapeState state) {
        this.state = state;
        route = new Stack<>();
    }

    public void cheeseIt() {
        //Input check message
        System.out.println("Cheesing it...");

        if(state.getTimeRemaining() - findShortestPath(state.getExit(), false) < 10) {
            findShortestPath(state.getExit(), true);
            takeRoute();
        } else {
            findShortestPath(state.getExit(), true);
            takeRoute();

        }

    }

    private void takeRoute() {
        while(!route.isEmpty()) {
            state.moveTo(route.pop());
            Tile thisTile = state.getCurrentNode().getTile();
            if(thisTile.getOriginalGold() != 0 && thisTile.getGold() != 0) {
                state.pickUpGold();
            }
        }
    }
    private double findShortestPath(Node destinationNode, boolean plotRoute) {
        //Create a set of all nodes using wrapper class;
        //Set the distance from start to max, or 0 if current
        //Set visited to false, or true if current
        Set<EscapeNode> allNodes = new HashSet<>();

        for (Node n : state.getVertices()) {
            EscapeNode escapeNode = new EscapeNode(n);
            if(n.equals(state.getCurrentNode())) {
                escapeNode.setDistance(0);
                escapeNode.setVisited(true);
                start = escapeNode;

            } else {
                if (n.equals(destinationNode)) {
                    destination = escapeNode;
                }
                escapeNode.setDistance(Double.MAX_VALUE);
                escapeNode.setVisited(false);

            }
            allNodes.add(escapeNode);
        }

        current = start;


        while (!destination.isVisited()) {
            //check distances to each neighbour and if bigger than current node distance plus distance to neighbour,
            //set neighbour distance to that.

            Set<EscapeNode> neighbours = current.getNeighbours(allNodes);

            if(neighbours != null) {
                for (EscapeNode e : neighbours) {
                    if (e.isVisited()) {
                        continue;
                    }

                    double routeDistanceToEscapeNode = current.getDistance() + current.getEdge(e).length;
                    if (e.getDistance() > routeDistanceToEscapeNode) {
                        e.setDistance(routeDistanceToEscapeNode);
                        e.setPrevious(current);
                    }
                }

            }

            current.setVisited(true);

            EscapeNode shortestUnvisited = null;
            double distance = Double.MAX_VALUE;
            for(EscapeNode e : allNodes.stream().filter(escapeNode -> !escapeNode.isVisited()).collect(Collectors.toSet())) {
                if (e.getDistance() < distance) {
                    shortestUnvisited = e;
                    distance = e.getDistance();
                }
            }

            if (distance == Double.MAX_VALUE) {
                break;
            } else {
                current = shortestUnvisited;
            }

        }
        if (plotRoute) {
            EscapeNode routeNode = destination;
            route.push(routeNode.getNode());
            while(routeNode.getPrevious() != start) {
                route.push(routeNode.getPrevious().getNode());
                routeNode = routeNode.getPrevious();
            }
        }

        return destination.getDistance();
    }
}


