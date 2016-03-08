package Escape;

/**
 * An interface for the EscapeArtist class
 *
 * Different implementations use different methods for escaping the maze
 *
 * Created by Oliver Coulson on 14/02/2016.
 */
public interface EscapeArtist {
    /**
     * The single important method which is called by the Explorer class escape method.
     */
     void cheeseIt();
}
