package student.Escape;

import game.EscapeState;
import game.Node;
import game.Tile;

import java.util.*;


/**
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeArtistImplTiles extends EscapeArtistImpl{
    private EscapeState state;

    public EscapeArtistImplTiles(EscapeState state) {
        super(state);
        this.state = state;
    }
    /**
     * An implementation of the rankNodes method, using cartesian coordinates of row and column as the heuristic for
     * ranking the nodes.
     * This is significantly faster than the other two implementations but scores lower, basically because it is less
     * efficient and doesn't take walls into account, which can lead to poor performance.
     *
     * @param map A set of all EscapeNodes with gold on them
     * @return A sorted set of the nodes ranked amount of gold and actual distance from them
     */
    @Override
    SortedSet<EscapeNode> rankNodes(Set<EscapeNode> map) {
        SortedSet<EscapeNode> goldNodes = new TreeSet<>(EscapeNode::compareGoldRank);
        for(EscapeNode e : map) {
            e.resetGoldRank();
            //The below line uses a heuristic for ranking gold tiles by distance
            e.setGoldRank(e.getGoldRank()/ (tileDistance(e.getNode()) *2));

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


