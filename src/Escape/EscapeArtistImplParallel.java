package Escape;

import game.EscapeState;
import game.Node;
import game.Tile;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeArtistImplParallel implements EscapeArtist{
    private EscapeState state;

    private Stack<Node> route;



    public EscapeArtistImplParallel(EscapeState state) {
        this.state = state;
        route = new Stack<>();
    }

    public void cheeseIt() {
        //Input check message
        System.out.println("Cheesing it...");
        //JOptionPane.showConfirmDialog(null, "Cheese it??!");
        if(state.getCurrentNode().getTile().getGold() > 0) {
            state.pickUpGold();
        }
        Set<EscapeNode> map = new HashSet<>();

        for(Node n : state.getVertices()) {
            if(n.getTile().getGold() != 0) {
                EscapeNode node = new EscapeNode(n);
                map.add(node);
            }
        }

        SortedSet<EscapeNode> goldNodes;

        EscapeNode next;
        int count = 0;
        while (state.getCurrentNode() != state.getExit()) {
            goldNodes = new TreeSet<>(EscapeNode::compareGoldRank);

            map.parallelStream()
                    .forEach(escapeNode1 -> {
                        escapeNode1.resetGoldRank();
                        escapeNode1.setGoldRank(escapeNode1.getGoldRank() /
                                (findShortestPath(escapeNode1.getNode(), false) * 2));
                    });

            for(EscapeNode e : map) {
                goldNodes.add(e);
            }

            /*for(EscapeNode e : map) {
                e.resetGoldRank();
                //The below line has commented out code which makes the solution better, but also take far longer
                e.setGoldRank(e.getGoldRank()/ (findShortestPath(e.getNode(), false)) * 2);

                goldNodes.add(e);
            }*/
            next = goldNodes.last();
            findShortestPath(next.getNode(), true);
            while(state.getCurrentNode() != next.getNode()) {
                if(state.getTimeRemaining() - findShortestPath(state.getExit(), false) < 30) {
                    findShortestPath(state.getExit(), true);
                    takeRoute();
                    break;
                }
                state.moveTo(route.pop());
                Tile thisTile = state.getCurrentNode().getTile();
                if(thisTile.getOriginalGold() != 0 && thisTile.getGold() != 0) {
                    state.pickUpGold();

                    map.remove(map.stream()
                            .filter(escapeNode -> escapeNode.getId() == state.getCurrentNode().getId())
                            .findFirst().get());

                }

            }
            count ++;
        }

    }

    private void takeRoute() {
        while(state.getCurrentNode() != state.getExit()) {
            state.moveTo(route.pop());
            Tile thisTile = state.getCurrentNode().getTile();
            if(thisTile.getOriginalGold() != 0 && thisTile.getGold() != 0) {
                state.pickUpGold();
            }
        }
    }
    private double findShortestPath(Node destinationNode, boolean plotRoute) {
        EscapeNode current;
        EscapeNode start = null;
        EscapeNode destination = null;

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

                    double routeDistanceToEscapeNode = current.getDistance() + current.getEdge(e).length();
                    if (e.getDistance() > routeDistanceToEscapeNode) {
                        e.setDistance(routeDistanceToEscapeNode);
                        e.setPrevious(current);
                    }
                }

            }

            current.setVisited(true);

            //In the case that the shortest unvisited has a tentative distance of the max, the algorithm is finished
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


