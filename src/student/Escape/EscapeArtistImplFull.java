package student.Escape;

import game.EscapeState;

import java.util.*;


/**
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeArtistImplFull extends EscapeArtistImpl{

    public EscapeArtistImplFull(EscapeState state) {
        super(state);
    }

    /**
     * An implementation of the rankNodes method, using the findShortestPath method as the heuristic.
     * It uses the gold rank of each node (the total gold of the node and its neighbours) and divides it by double the
     * distance to it, according to the findShortestPath method.
     *
     * With large maps, this is very processor hungry and can take over a minute to run.
     *
     * @param map A set of all EscapeNodes with gold on them
     * @return A sorted set of the nodes ranked amount of gold and actual distance from them
     */
    @Override
    SortedSet<EscapeNode> rankNodes(Set<EscapeNode> map) {
        SortedSet<EscapeNode> goldNodes = new TreeSet<>(EscapeNode::compareGoldRank);
        for(EscapeNode e : map) {
            e.resetGoldRank();
            //The below line has commented out code which makes the solution better, but also take far longer
            e.setGoldRank(e.getGoldRank()/ (findShortestPath(e.getNode(), false)) * 2);

            goldNodes.add(e);
        }
        return goldNodes;
    }


}


