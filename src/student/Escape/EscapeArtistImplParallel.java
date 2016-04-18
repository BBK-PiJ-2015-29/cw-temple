package student.Escape;

import game.EscapeState;

import java.util.*;


/**
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeArtistImplParallel extends EscapeArtistImpl{


    public EscapeArtistImplParallel(EscapeState state) {
        super(state);
    }
    /**
     * Essentially the same as the EscapeArtistImplFull implementation, but uses a parallel stream to perform the
     * heuristic concurrently.
     *
     * An implementation of the rankNodes method, using the findShortestPath method as the heuristic.
     * It uses the gold rank of each node (the total gold of the node and its neighbours) and divides it by double the
     * distance to it, according to the findShortestPath method.
     *
     * With large maps, this is very processor hungry and even with the parallelism, can take over 40 seconds to run.
     *
     * @param map A set of all EscapeNodes with gold on them
     * @return A sorted set of the nodes ranked amount of gold and actual distance from them
     */
    @Override
    SortedSet<EscapeNode> rankNodes(Set<EscapeNode> map) {
        SortedSet<EscapeNode> goldNodes = new TreeSet<>(EscapeNode::compareGoldRank);

        map.parallelStream()
                .forEach(escapeNode1 -> {
                    escapeNode1.resetGoldRank();
                    escapeNode1.setGoldRank(escapeNode1.getGoldRank() /
                            (findShortestPath(escapeNode1.getNode(), false) * 2));
                });

        for(EscapeNode e : map) {
            goldNodes.add(e);
        }
        return goldNodes;
    }

}


