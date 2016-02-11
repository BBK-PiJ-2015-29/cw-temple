package Escape;

import game.Edge;
import game.Node;
import game.Tile;

import java.util.Set;

/**
 * A wrapper class for nodes, holding distance and visited boolean flag used for Dijkstra's algorithm
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeNode {

    private final Node node;
    private double distance;
    private boolean visited;
    private EscapeNode previous;

    public EscapeNode getPrevious() {
        return previous;
    }

    public void setPrevious(EscapeNode previous) {
        this.previous = previous;
    }

    public EscapeNode(Node node) {
        this.node = node;
    }

    public long getId() {
        return node.getId();
    }

    public Edge getEdge(Node q) {
        return node.getEdge(q);
    }

    public Set<Edge> getExits() {
        return node.getExits();
    }

    public Set<Node> getNeighbours() {
        return node.getNeighbours();
    }

    public Tile getTile() {
        return node.getTile();
    }

    @Override
    public boolean equals(Object ob) {
        return node.equals(ob);
    }

    @Override
    public int hashCode() {
        return node.hashCode();
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


}
