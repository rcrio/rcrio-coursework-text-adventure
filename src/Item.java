/**
 * A class representing an item with a name, a weight, a description, and several booleans that
 * help decide which items that can be used by certain commands.
 *
 * @author Ricky Chan (K23062540)
 * @version 1: 2023.12.04
 */
public class Item
{
    private String name;
    private int weight;
    private String description;
    private boolean isPickable;
    private boolean isRelic;
    private boolean isWeapon;

    /**
     * Creates an item and its attributes.
     */
    public Item (String name, int weight, boolean isPickable, boolean isRelic, boolean isWeapon)
    {
        this.name = name;
        this.weight = weight;
        this.description = description;
        this.isPickable = isPickable;
        this.isRelic = isRelic;
        this.isWeapon = isWeapon;
    }

    /**
     * @return The name of the item.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The weight of the item.
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * @return The description of the item.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the item.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    //item states
    /**
     * @return True if the item is pickable, false otherwise.
     */
    public boolean getIsPickable()
    {
        return isPickable;
    }

    /**
     * @return True if the item is a relic, false otherwise.
     */
    public boolean getIsRelic()
    {
        return isRelic;
    }

    /**
     * @return True if the item is a weapon, false otherwise.
     */
    public boolean getIsWeapon()
    {
        return isWeapon;
    }
}
