package Escape;

import MapExplorer.MapNode;
import game.EscapeState;
import game.Node;

import java.util.Set;


/**
 * Created by Oliver Coulson on 11/02/2016.
 */
public class EscapeArtist {
    private EscapeState state;
    private Set<EscapeNode> allNodes;



    public EscapeArtist(EscapeState state) {
        this.state = state;
    }

    public void cheeseIt() {
        System.out.println("Cheesing it...");

        //Create a set of all nodes using wrapper class;
        for (Node n : state.getVertices()) {
            EscapeNode escapeNode = new EscapeNode(n);
            escapeNode.setVisited(false);
            if(n.equals(state.getCurrentNode())) {
                escapeNode.setDistance(0);
            } else {
                escapeNode.setDistance(Double.MAX_VALUE);
            }
            allNodes.add(escapeNode);

        }




    }
}


