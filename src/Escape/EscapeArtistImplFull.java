package Escape;

import game.EscapeState;
import game.Node;
import game.Tile;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeArtistImplFull extends EscapeArtistImpl{

    public EscapeArtistImplFull(EscapeState state) {
        super(state);
    }


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


