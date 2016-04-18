package student.MapExplorer;

import game.NodeStatus;
import student.PriorityQueue;
import student.PriorityQueueImpl;

import java.util.*;

/**
 * A class to hold the information about a node, gathered by exploring the map.
 * Each object represents a single node and holds information about its Id, distance
 * from target, the known neighbours and also whether it has been visited or not.
 *
 * @author Ollie Coulson
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

    /**
     * Method to set the boolean flag indicating if the node has been visited to true;
     */
    public void setVisited() {
        visited = true;
    }

    /**
     * In the event that a new MapNode object is made for an unvisited node (i.e. a neighbour)
     * this method is used to set that nodes only known neighbour to the current node
     * @param neighbour the MapNode known to be a neighbour of this node.
     */
    public void addNeighbour(MapNode neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * A method to set the Set of neighbours of the current node, if the neighbour node has already been seen,
     * it will use that node from the set, otherwise a new one is created.
     *
     * @param neighbourStatuses The NodeStatuses of the neighbours as a result of a state.getNeighbours() call
     * @param seen, the set of all seen nodes
     */
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

    /**
     * Returns the neighbours of the current node as a Set of MapNodes, or null if no neighbours are known.
     * @return the set of MapNodes of neighbours.
     */
    public Set<MapNode> getNeighbours() {
        if (neighbours != null) {
        return neighbours;
        } else return null;
    }

    /**
     * sorts the current set of Neighbours by distance to target and returns them as a PriorityQueue of nodes.
     * @return the priority queue of neighbours, the front of which is the neighbour with the shortest distance to
     * target.
     */
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

    /**
     * Getter for the Id field
     * @return the id as a long
     */
    public long getId() {

        return id;
    }

    /**
     * Getter for the distance from target field
     * @return the distance from the target as an int
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Returns whether the node has been visited or not
     * @return true if visited, false otherwise.
     */
    public boolean isVisited() {
        return visited;
    }


    /**
     * Compares this MapNode to another, and returns true if the Id values match.
     * @param other the other MapNode to compare with
     * @return true if the Id values match (or if other is this) or false otherwise.
     */
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

    /**
     * returns the hash code of this object based on the Id value
     * @return the hash code as an int
     */
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
