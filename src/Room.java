import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * A class representing rooms in the game.
 *
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  For each existing exit, the room
 * stores a reference to the neighboring room.
 *
 * @author Michael Kölling and David J. Barnes and Ricky Chan (K23062540)
 * @version 1: 2016.02.29
 * @version 2: 2023.12.04
 */

public class Room
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private Inventory inventory;                // stores the items of the room
    private ArrayList<Character> characters;    // stores the characters of the room
    private int roomNumber;                     // used for minimap location
    private boolean isMagicRoom;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, int roomNumber)
    {
        this.inventory = new Inventory();
        this.description = description;
        exits = new HashMap<>();
        this.characters = new ArrayList<>();
        this.roomNumber = roomNumber;
        this.isMagicRoom = isMagicRoom;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys)
        {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * @return The room number.
     */
    public int getRoomNumber()
    {
        return roomNumber;
    }

    /**
     * @return True if the room is the magic teleporter room, false otherwise.
     */
    public boolean getIsMagicRoom()
    {
        return isMagicRoom;
    }

    /**
     * Sets the isMagicRoom boolean. Set it to true if the room is the magic room, otherwise set it to false.
     */
    public void setIsMagicRoom(boolean isMagicRoom)
    {
        this.isMagicRoom = isMagicRoom;
    }

    //item management
    /**
     * @return The item reference by searching for it by its name.
     */
    public Item getItem(String item)
    {
        return inventory.getItem(item);
    }

    /**
     * @return The item reference by searching for it by its name.
     */
    public ArrayList<Item> getInventory()
    {
        return inventory.getInventory();
    }

    /**
     * Prints the inventory of the room, which is the array list of the items.
     */
    public void printInventory()
    {
        inventory.printInventory();
    }

    /**
     * Adds an item to the inventory of the room.
     */
    public void addItem(Item item)
    {
        inventory.addItem(item);
    }

    /**
     * Removes an item to the inventory of the room.
     */
    public void removeItem(Item item)
    {
        inventory.removeItem(item);
    }

    //Character management
    /**
     * Adds a character to the room, by adding it to the character list.
     */
    public void addCharacter(Character character)
    {
        characters.add(character);
    }

    /**
     * Removes a character to the room, by removing it to the character list.
     */
    public void removeCharacter(Character character)
    {
        characters.remove(character);
    }

    /**
     * @return The character reference by searching for it using its reference, otherwise returns null if the item doesn't exist.
     */
    public Character getCharacter(Character characterToFind)
    {
        for (Character currentCharacter : characters)
        {
            if (currentCharacter.equals(characterToFind))
            {
                return currentCharacter;
            }
        }
        return null;
    }

    /**
     * @return The character reference by searching for it using its name, otherwise returns null if the item doesn't exist.
     */
    public Character getCharacter(String name)
    {
        for (Character currentCharacter : characters)
        {
            if(currentCharacter.getName().equals(name))
            {
                return currentCharacter;
            }
        }
        return null;
    }

    /**
     * @return The list of characters in the room.
     */
    public ArrayList<Character> getCharacters()
    {
        return characters;
    }

    /**
     * Prints each character in the room, by iterating over the character list.
     */
    public void printCharacters() {
        for (Character character : characters)
        {
            System.out.println("[" + character.getName() + "]");
        }
    }
}

