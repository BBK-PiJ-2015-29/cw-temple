package MapExplorer;

import game.NodeStatus;
import student.PriorityQueue;
import student.PriorityQueueImpl;

import java.util.*;

/**
 * A class to hold the information about a node
 */
public class MapNode implements Comparable<MapNode> {
    private final long id;
    private final int distance;
    private boolean visited = false;

    private Set<MapNode> neighbours;

    public MapNode(long id, int distance) {
        this.id = id;
        this.distance = distance;
        neighbours = new HashSet<>();
    }

    public void setVisited() {
        visited = true;
    }

    public void addNeighbour(MapNode neighbour) {
        neighbours.add(neighbour);
    }
    public void setNeighbours(Collection<NodeStatus> neighbourStatuses, Set<MapNode> seen) {
        for (NodeStatus n : neighbourStatuses) {
            if (seen.stream().anyMatch(mapNode -> mapNode.getId() == n.getId())) {
                neighbours.add(seen.stream().filter(mapNode -> mapNode.getId() == n.getId()).findFirst().get());
            } else {
                MapNode newNode = new MapNode(n.getId(), n.getDistanceToTarget());
                newNode.addNeighbour(this);
                neighbours.add(newNode);
                seen.add(newNode);
            }
        }
    }

    public Set<MapNode> getNeighbours() {
        if (neighbours != null) {
        return neighbours;
        } else return null;
    }

    public PriorityQueue<MapNode> getSortedNeighbours() {
        if (neighbours != null) {
            PriorityQueue<MapNode> nodes = new PriorityQueueImpl<>();
            for (MapNode m : neighbours) {
                nodes.add(m, (double) m.getDistance());
            }
            return nodes;
        } else {
            return null;
        }
    }

    public long getId() {

        return id;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isVisited() {
        return visited;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MapNode)) {
            return false;
        } else {
            if (other == this) {
                return true;
            }
            return this.getId() == ((MapNode) other).getId();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Compares nodes by distance from orb, if the distance is the same, it randomly chooses one to go first.
     * @param o the other node to compare to
     * @return positive if this node is nearer, negative if not
     */
    @Override
    public int compareTo(MapNode o) {
        if(this.getDistance() == o.getDistance()) {
            double random = Math.random();
            return(random > 0.5) ? 1 : -1;
        } else return (this.getDistance() > o.getDistance()) ? 1 : -1;
    }
}
