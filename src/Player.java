import java.util.ArrayList;
/**
 * A class representing a player and their attributes (which is only inventory).
 *
 * @author Ricky Chan (K23062540)
 * @version 1: 2023.12.04
 */

public class Player
{
    private Inventory inventory;
    private static final int WEIGHT_LIMIT = 15;

    /**
     * Creates a player.
     */
    public Player()
    {
        this.inventory = new Inventory();
        this.inventory.setWeightLimit(WEIGHT_LIMIT);
    }

    /**
     * @return The item reference by searching for it in the inventory, using the item name.
     */
    public Item getItem(String item)
    {
        return inventory.getItem(item);
    }

    /**
     * @return The weight limit of the player's inventory.
     */
    public int getWeightLimit()
    {
        return inventory.getWeightLimit();
    }

    /**
     * @return The current weight of the player's current total items, in their inventory.
     */
    public int getCurrentInventoryWeight()
    {
        return inventory.getCurrentInventoryWeight();
    }

    /**
     * @return The array list of all the items, in the player's inventory.
     */
    public ArrayList<Item> getInventory()
    {
        return inventory.getInventory();
    }

    /**
     * For each item that exists in the player's inventory, prints their name and weight.
     */
    public void printInventory()
    {
        inventory.printInventory();
    }

    //add or remove items
    /**
     * Adds an item to the player inventory.
     */
    public void addItem(Item item)
    {
        inventory.addItem(item);
    }

    /**
     * Removes an item to the player inventory.
     */
    public void removeItem(Item item)
    {
        inventory.removeItem(item);
    }
}

