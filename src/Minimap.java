import java.util.Arrays;
/**
 * A class representing a low-detail minimap that displays the current room the player is in.
 *
 * This class stores the minimap logic. The minimap works by printing out several lines of text
 * that group together to show a minimap. The #s help visualise the map by showing territory
 * that you can not go to. The O represents a room. The | and - represent paths between rooms.
 * The X represents which room the player is in.
 *
 * @author Ricky Chan (K23062540)
 * @version 1: 2023.12.04
 */
public class Minimap
{
    private String minimap;
    private String[] roomO;
    private int location;

    /**
     * Creates a minimap by calling the construct minimap method and initialises the location.
     */
    public Minimap()
    {
        location = 0;
        this.minimap = constructMinimap();
    }

    /**
     * Updates the minimap by calling the construct method to reconstruct it.
     */
    public String updateMinimap(int location)
    {
        this.location = location;
        return constructMinimap();
    }

    /**
     * Constructs the minimap.
     */
    public String constructMinimap()
    {
        roomO = new String[]{"O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O"};
        roomO[location] = "X";
        minimap =           "#####################\n" +
                "######### " + roomO[11-1] + " #########\n" +
                "######### | #########\n" +
                "##### " + roomO[9-1] + " - " + roomO[8-1] + " - " + roomO[10-1] + " #####\n" +
                "######### | #########\n" +
                "##### " + roomO[5-1] + " - " + roomO[4-1] + " - " + roomO[6-1] + " - " + roomO[7-1] + " #\n" +
                "######### | #########\n" +
                "# " + roomO[1-1] + " - " + roomO[2-1] + " - " + roomO[3-1] + " #########\n" +
                "#####################\n" +
                "#####################\n";
        return minimap;
    }

    /**
     * @return The current minimap.
     */
    public String viewMinimap()
    {
        return minimap;
    }
}
