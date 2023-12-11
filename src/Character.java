import java.util.ArrayList;
/**
 * A class representing characters in the game.
 *
 * @author Ricky Chan (K23062540)
 * @version 1: 2023.12.04
 */

public class Character
{
    private String name;
    private String description;
    private Inventory inventory;
    private boolean isEnemy;
    private boolean isSeer;

    /**
     * Create a character and its attributes.
     */
    public Character(String name, boolean isEnemy, boolean isSeer)
    {
        this.name = name;
        this.description = description;
        this.inventory = new Inventory();
        this.isEnemy = isEnemy;
        this.isSeer = isSeer;
    }

    /**
     * Adds an item to the character's inventory.
     */
    public void addItem(Item item)
    {
        inventory.addItem(item);
    }

    /**
     * @return The name of the character.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The description of the character.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the character.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return The inventory of the character, which is an array list of items.
     */
    public ArrayList<Item> getInventory()
    {
        return inventory.getInventory();
    }

    //character states
    /**
     * @return True if the character is an enemy, otherwise false.
     */
    public boolean getIsEnemy()
    {
        return isEnemy;
    }

    /**
     * @return True if the character is a seer, otherwise false.
     */
    public boolean getIsSeer()
    {
        return isSeer;
    }
}
