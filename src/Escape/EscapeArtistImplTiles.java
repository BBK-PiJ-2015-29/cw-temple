package Escape;

import game.EscapeState;
import game.Node;
import game.Tile;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeArtistImplTiles extends EscapeArtistImpl{
    private EscapeState state;

    public EscapeArtistImplTiles(EscapeState state) {
        super(state);
        this.state = state;
    }

    @Override
    SortedSet<EscapeNode> rankNodes(Set<EscapeNode> map) {
        SortedSet<EscapeNode> goldNodes = new TreeSet<>(EscapeNode::compareGoldRank);
        for(EscapeNode e : map) {
            e.resetGoldRank();
            //The below line uses a heuristic for ranking gold tiles by distance
            e.setGoldRank(e.getGoldRank()/ (tileDistance(e.getNode())));

            goldNodes.add(e);
        }
        return goldNodes;
    }

    private double tileDistance(Node destination) {
        Tile currentTile = state.getCurrentNode().getTile();
        Tile destTile = destination.getTile();

        double colDif = (destTile.getColumn() - currentTile.getColumn());
        double rowDif = (destTile.getRow() - currentTile.getRow());

        return Math.sqrt((colDif * colDif) + (rowDif * rowDif));
    }


}


