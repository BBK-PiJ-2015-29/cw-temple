package Escape;

import game.Edge;
import game.Node;
import game.Tile;


import java.util.HashSet;
import java.util.Set;

/**
 * A wrapper class for nodes, holding distance and visited boolean flag used for Dijkstra's algorithm
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeNode implements Comparable<EscapeNode>{

    private final Node node;
    private double distance;
    private boolean visited;
    private EscapeNode previous;

    public double getGoldRank() {
        return goldRank;
    }

    public void setGoldRank(double goldRank) {
        this.goldRank = goldRank;
    }

    public void resetGoldRank() {
        goldRank = getTile().getGold();
        for(Node n : node.getNeighbours()) {
            goldRank += n.getTile().getGold();
        }
    }

    private double goldRank;

    public EscapeNode(Node node) {
        this.node = node;
        resetGoldRank();
    }

    public long getId() {
        return node.getId();
    }

    public Edge getEdge(EscapeNode q) {

        return node.getEdge(q.getNode());
    }

    public Set<Edge> getExits() {
        return node.getExits();
    }

    public Set<EscapeNode> getNeighbours(Set<EscapeNode> all) {
        Set<EscapeNode> output = new HashSet<>();
        for (Node n: node.getNeighbours()) {
            output.add(all.stream().filter(escapeNode -> escapeNode.getNode().equals(n)).findFirst().get());
        }
        return output;
    }

    public Tile getTile() {
        return node.getTile();
    }

    public Node getNode() {
        return node;
    }

    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public EscapeNode getPrevious() {return previous;}
    public void setPrevious(EscapeNode previous) {
        this.previous = previous;
    }

    @Override
    public boolean equals(Object ob) {
        return node.equals(ob);
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }

    @Override
    public int compareTo(EscapeNode o) {
        return Double.compare(getDistance(), o.getDistance());
        /*if(this.getDistance() == o.getDistance()) {
            return 0;
        } else return (this.getDistance() > o.getDistance()) ? 1 : -1;*/
    }

    public int compareGoldRank(EscapeNode o) {
        if(this.getGoldRank() == o.getGoldRank()) {
            return 0;
        } else {
            return (this.getGoldRank() > o.getGoldRank()) ? 1 : -1;
        }
    }
}
