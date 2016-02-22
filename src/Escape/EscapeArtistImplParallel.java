package Escape;

import game.EscapeState;
import game.Node;
import game.Tile;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeArtistImplParallel extends EscapeArtistImpl{


    public EscapeArtistImplParallel(EscapeState state) {
        super(state);
    }

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


