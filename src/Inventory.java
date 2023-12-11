import java.util.ArrayList;
/**
 * A class representing inventories of items in the game. Characters and rooms will use this class.
 *
 * @author Ricky Chan (K23062540)
 * @version 1: 2023.12.04
 */

public class Inventory
{
    private ArrayList<Item> inventory = new ArrayList<>();
    private int weightLimit;

    /**
     * Creates and initialises an inventory, which is the array list of items.
     */
    public Inventory()
    {
        inventory = new ArrayList<>();
    }

    /**
     * @return The item reference by searching for it using its item reference,
     * otherwise returns null if the item doesn't exist.
     * @param itemToFind item reference of the item you are trying to find.
     */
    public Item getItem(Item itemToFind)
    {
        for (Item itemToLocate : inventory)
        {
            if (itemToLocate.equals(itemToFind))
            {
                return itemToFind;
            }
        }
        return null;
    }

    /**
     * @return The item reference by searching for it using its name,
     * otherwise returns null if the item doesn't exist.
     * @param name The name of the item you are trying to get.
     */
    public Item getItem(String name)
    {
        for (Item item : inventory)
        {
            if (item.getName().equals(name))
            {
                return item;
            }
        }
        return null;
    }

    /**
     * @return The item name by searching for it using its object reference,
     * otherwise returns null if the item doesn't exist.
     */
    public String getItemName(Item itemToFind)
    {
        for (Item itemToLocate : inventory)
        {
            if (itemToLocate.equals(itemToFind))
            {
                return itemToLocate.getName();
            }
        }
        return null;
    }

    /**
     * @return The weight of the item by searching for it using its object reference,
     * otherwise returns -1 if the item doesn't exist.
     */
    public int getItemWeight(Item itemToFind)
    {
        for (Item itemToLocate : inventory)
        {
            if (itemToLocate.equals(itemToFind))
            {
                return itemToLocate.getWeight();
            }
        }
        return -1;
    }

    /**
     * Sets the weight limit of the inventory.
     */
    public void setWeightLimit(int weightLimit)
    {
        this.weightLimit = weightLimit;
    }

    /**
     * @return The weight limit of the inventory.
     */
    public int getWeightLimit()
    {
        return weightLimit;
    }

    /**
     * @return The array list of items, which is the inventory.
     */
    public ArrayList getInventory()
    {
        return inventory;
    }

    /**
     * @return The total weight of the current items in the inventory.
     */
    public int getCurrentInventoryWeight()
    {
        int currentWeight = 0;
        for (Item item : inventory)
        {
            currentWeight += getItemWeight(item);
        }

        return currentWeight;
    }

    /**
     * For each item that exists in the inventory, prints their name and weight.
     */
    public void printInventory() {
        for (Item item : inventory)
        {
            System.out.println("[Item: " + getItemName(item) + ", Weight: " + getItemWeight(item) + " kg]");
        }
    }

    //add or remove items
    /**
     * Adds an item to the inventory.
     */
    public void addItem(Item item)
    {
        inventory.add(item);
    }

    /**
     * Removes an item to the inventory.
     */
    public void removeItem(Item item)
    {
        inventory.remove(item);
    }
}
