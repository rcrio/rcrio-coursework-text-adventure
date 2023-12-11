import java.util.ArrayList;
import java.util.Random;
/**
 *  The main class of the "Tensura Labyrinth" application.
 *  "Tensura Labyrinth" is a simple, text-based adventure game.  Users
 *  can walk around the labyrinth and fight slimes, interact with items and NPCS, and give NPCs some stuff.
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms and their content, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 *  This class stores the game logic, creates the game environment, and all the command logic.
 *
 * @author  Michael Kölling and David J. Barnes and Ricky Chan (K23062540)
 * @version 1: 2016.02.29
 * @version 2: 2023.12.04
 */

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private ArrayList<Room> roomHistory;
    private ArrayList<Room> roomList;
    private Player player;
    private Minimap minimap;

    //variables for game environment
    private Room room1, room2, room3, room4, room5, room6, room7, room8, room9, room10, room11;
    private Character seer, blacksmith, slime, bossSlime;
    private Item sword, relic, furnace, anvil;

    private int counter = 0;
    private boolean turn = false;
    private boolean slimesNotDead = true;
    private boolean seerRelics = false;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        roomList = new ArrayList<>();
        createGameMap();
        parser = new Parser();
        roomHistory = new ArrayList<>();
        player = new Player();
        minimap = new Minimap();
        isSlimesDead();
        isSeerRelics();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createGameMap()
    {

        roomList.add(room1 = new Room("in the starting room (room 1)", 1));
        roomList.add(room2 = new Room("in a room (room 2)", 2));
        roomList.add(room3 = new Room("in a room (room 3)", 3));
        roomList.add(room4 = new Room("in a room (room 4).\n"
                + "Magic fills the air. There's something up with this room..", 4));
        roomList.add(room5 = new Room("in a room (room 5)", 5));
        roomList.add(room6 = new Room("in a room (room 6)", 6));
        roomList.add(room7 = new Room("in a room (room 7)", 7));
        roomList.add(room8 = new Room("in a room (room 8)", 8));
        roomList.add(room9 = new Room("in a room (room 9)", 9));
        roomList.add(room10 = new Room("in a room (room 10)", 10));
        roomList.add(room11 = new Room("in a room (room 11)", 11));

        //setting exits for rooms
        room1.setExit("east", room2);

        room2.setExit("west", room1);
        room2.setExit("east", room3);

        room3.setExit("north", room4);
        room3.setExit("west", room2);

        room4.setExit("north", room8);
        room4.setExit("south", room3);
        room4.setExit("west", room5);
        room4.setExit("east", room6);

        room5.setExit("east", room4);

        room6.setExit("west", room4);
        room6.setExit("east", room7);

        room7.setExit("west", room6);
        room7.setIsMagicRoom(true);

        room8.setExit("north", room11);
        room8.setExit("south", room4);
        room8.setExit("west", room9);
        room8.setExit("east", room10);

        room9.setExit("east", room8);

        room10.setExit("west", room8);

        room11.setExit("south", room8);

        currentRoom = room1;  // start game at room 1

        //adding items to rooms
        boolean isPickable = true;
        boolean isRelic = true;
        boolean isWeapon = true;


        //public Item (String name, int weight, boolean isPickable, boolean isRelic, boolean isWeapon)
        sword = new Item("Sword", 5, isPickable, !isRelic, isWeapon);
        sword.setDescription("\nIt's a weapon. Maybe I can use this to defend myself?\nMight be worth picking up.");
        room2.addItem(sword);

        relic = new Item("Relic", 5, isPickable, isRelic, !isWeapon);
        relic.setDescription("\nIt's a weird looking... artifact? Seems important.\nMight be worth picking up.");
        room4.addItem(relic);
        room9.addItem(relic);
        room10.addItem(relic);

        furnace = new Item("Furnace", 30, !isPickable, !isRelic, !isWeapon);
        furnace.setDescription("\nThat's a really hot furnace.\n...Picking that up is out of the question.");
        room5.addItem(furnace);

        anvil = new Item("Anvil", 50, !isPickable, !isRelic, !isWeapon);
        anvil.setDescription("\nLooks like an anvil. Seems pretty beat up.\nThere's no way I'm picking that up.");
        room5.addItem(anvil);

        //adding characters to rooms
        boolean isEnemy = true;
        boolean isSeer = true;

        slime = new Character("Slime", isEnemy, !isSeer);
        slime.setDescription("\nThat's a really cute slime. However... it's definitely trying to attack me.");
        room3.addCharacter(slime);
        room9.addCharacter(slime);
        room10.addCharacter(slime);

        bossSlime = new Character("BossSlime", isEnemy, !isSeer);
        bossSlime.setDescription("\nThat's a fast slime! It keeps trying to move to the next room...");
        room11.addCharacter(bossSlime);

        seer = new Character("Seer", !isEnemy, isSeer);
        seer.setDescription("\nSeer: Adventurer... slay all the slimes and give me the 3 relics and we will be able to escape!");
        room11.addCharacter(seer);

        blacksmith = new Character("Blacksmith", !isEnemy, !isSeer);
        blacksmith.setDescription("Blacksmith: Hey! Adventurer, can ya get us outta here? I've seen a buncha slimes around... and some odd looking bloke in a hood."
                + "\nBlacksmith: Hope that gives some sort of clue to figurin' out a way to escape this blimmin' labyrinth.");
        room5.addCharacter(blacksmith);
    }

    //game logic
    /**
     * Main play routine. Loops until end of play.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        // add win condition here and also add npc movement here

        boolean finished = false;

        while (!finished && !isWin())
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
            moveBossSlime();
            isWin();
        }

        if (isSeerRelics() && isSlimesDead())
        {
            System.out.println("Congratulations! You have won!");
        }

        System.out.println("Thank you for playing. Goodbye.");
    }

    /**
     * Checks if game has been won or not by checking if the Seer has all their relics and if the slimes are dead.
     */
    private boolean isWin()
    {
        isSeerRelics();
        isSlimesDead();
        return isSeerRelics() && isSlimesDead();
    }

    /**
     * Checks if the Seer has 3 relics for the win condition.
     */
    private boolean isSeerRelics()
    {
        int relics = 0;

        //loop to check if the relic item exists in the seer's inventory
        for (Item item : seer.getInventory())
        {
            if (item.getIsRelic())
            {
                relics++;
            }
        }

        if (relics >= 3)
        {
            return true;
        }

        return false;
    }

    /**
     * Checks if all slimes are dead for the win condition.
     */
    private boolean isSlimesDead()
    {
        //loop to check all rooms, and then all characters in each room, to see if an enemy exists
        for (Room room : roomList)
        {
            for (Character character : room.getCharacters())
            {
                if (character.getIsEnemy())
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Moves the boss slime between room 8 to room 11.
     */
    private void moveBossSlime()
    {
        if (turn == false && (room8.getCharacters().contains(bossSlime) || room11.getCharacters().contains(bossSlime)))
        {
            counter++;
            if (counter == 4)
            {
                turn = true;
                room8.addCharacter(bossSlime);
                room11.removeCharacter(bossSlime);
                System.out.println("Boss Slime has moved to room 8.");
            }
        }
        else if (turn == true && (room8.getCharacters().contains(bossSlime) || room11.getCharacters().contains(bossSlime)))
        {
            counter--;
            if (counter == 0)
            {
                turn = false;
                room11.addCharacter(bossSlime);
                room8.removeCharacter(bossSlime);
                System.out.println("Boss Slime has moved to room 11.");
            }
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Tensura Labyrinth!\n");
        System.out.println("Tensura Labyrinth is a text-based adventure game.\n");
        System.out.println("Type 'help' if you need help.\n");
        System.out.println(currentRoom.getLongDescription());
        viewMinimap();
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println("You are lost. Are you alone...?");
        System.out.println("You wonder around the labyrinth.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown())
        {
            System.out.println("I don't know what you mean...");
            return false;
        }
        String commandWord = command.getCommandWord();

        if (commandWord.equals("help"))
        {
            printHelp();
        }
        else if (commandWord.equals("go"))
        {
            goRoom(command);
        }
        else if (commandWord.equals("quit") || commandWord.equals("exit"))
        {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back"))
        {
            goBack();
        }
        else if (commandWord.equals("pickup"))
        {
            pickUp(command);
        }
        else if (commandWord.equals("drop"))
        {
            dropItem(command);
        }
        else if (commandWord.equals("viewInv"))
        {
            viewInv();
        }
        else if (commandWord.equals("viewRoom"))
        {
            viewRoom();
        }
        else if (commandWord.equals("viewMap"))
        {
            viewMinimap();
        }
        else if (commandWord.equals("interact"))
        {
            interact(command);
        }
        else if (commandWord.equals("give"))
        {
            give(command);
        }
        else if (commandWord.equals("attack"))
        {
            attack(command);
        }
        else if (commandWord.equals("objectives"))
        {
            viewObjectives();
        }

        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null)
        {
            System.out.println("There is no exit in that direction!");
            viewMinimap();
        }
        else
        {
            roomHistory.add(currentRoom);
            currentRoom = nextRoom;

            if (currentRoom.getIsMagicRoom())
            {
                //prevents random room being room 7 (index 6)
                int randomValue = 6;
                while (randomValue == 6)
                {
                    //Credit to Ian Kenny for idea and inspiration of random room logic (from Week 3, lecture 1b)
                    Random random = new Random();
                    randomValue = Math.abs(random.nextInt()%10);
                    randomValue ++;
                }
                //roomHistory.add(currentRoom);
                currentRoom = roomList.get(randomValue);
            }

            System.out.println(currentRoom.getLongDescription());
            viewRoom();
            viewMinimap();
        }
    }

    /**
     * Goes back to the previous room that the user was just in.
     */
    private void goBack()
    {
        if (!roomHistory.isEmpty())
        {
            Room previousRoom = roomHistory.get(roomHistory.size() - 1);
            currentRoom = previousRoom;
            System.out.println(currentRoom.getLongDescription());
            viewMinimap();
            roomHistory.remove(roomHistory.size() - 1);
        }
        else
        {
            System.out.println("You cannot go back any further!");
        }
    }

    /**
     * Allows the user to pick up a pickable item.
     */
    private void pickUp(Command command) {
        String itemName = command.getSecondWord();
        Item roomItem = currentRoom.getItem(itemName);

        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to pick up...
            System.out.println("Pick up what?");
            return;
        }
        else if (currentRoom.getInventory().isEmpty())
        {
            System.out.println("This room has no items!");
        }
        else if (roomItem == null)
        {
            System.out.println("This item does not exist or is not in the room!");
        }
        else if (roomItem.getIsPickable() == false)
        {
            System.out.println("This item cannot be picked up!");
        }
        else if (player.getCurrentInventoryWeight() + roomItem.getWeight() > player.getWeightLimit())
        {
            System.out.println("You cannot pick this item up! This is too heavy! Drop something to pick this up. ");
        }
        else
        {
            System.out.println("You picked up a " + roomItem.getName() + ".");
            currentRoom.removeItem(roomItem);
            player.addItem(roomItem);
        }

    }

    /**
     * Allows the user to drop an item in their inventory.
     */
    private void dropItem(Command command) {
        String itemName = command.getSecondWord();
        Item roomItem = player.getItem(itemName);

        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to pick up...
            System.out.println("Drop what?");
            return;
        }

        if (player.getInventory().isEmpty())
        {
            System.out.println("Your inventory is empty!");
        }
        else if (roomItem == null)
        {
            System.out.println("This item does not exist or is not in your inventory!");
        }
        else
        {
            System.out.println("You dropped a " + roomItem.getName() + ".");
            currentRoom.addItem(roomItem);
            player.removeItem(roomItem);
        }
    }

    /**
     * Allows the user to view their inventory.
     */
    private void viewInv()
    {
        if (player.getInventory().isEmpty())
        {
            System.out.println("#--------[Your items]--------#");
            System.out.println("It's empty...");
        }
        else
        {
            System.out.println("#--------[Your items]--------#");
            player.printInventory();
        }
    }

    /**
     * Allows the user to see the current NPCs and items in the room.
     */
    private void viewRoom()
    {
        if (currentRoom.getInventory().isEmpty() && currentRoom.getCharacters().isEmpty())
        {
            System.out.println("#--------[Room items]--------#");
            System.out.println("Nothing!");
            System.out.println("");
        }
        else if (currentRoom.getCharacters().isEmpty())
        {
            System.out.println("#--------[Room items]--------#");
            currentRoom.printInventory();
            System.out.println("");
            System.out.println("#---------[Room NPCs]--------#");
            System.out.println("None");
            System.out.println("");
        }
        else if (currentRoom.getInventory().isEmpty())
        {
            System.out.println("#--------[Room items]--------#");
            System.out.println("None");
            System.out.println("");
            System.out.println("#---------[Room NPCs]--------#");
            currentRoom.printCharacters();
            System.out.println("");
        }
        else
        {
            System.out.println("#--------[Room items]--------#");
            currentRoom.printInventory();
            System.out.println("");
            System.out.println("#---------[Room NPCs]--------#");
            currentRoom.printCharacters();
            System.out.println("");
        }
    }

    /**
     * Allows the user to see where they are on the minimap.
     */
    private void viewMinimap()
    {
        minimap.updateMinimap(currentRoom.getRoomNumber() - 1);
        System.out.println(minimap.viewMinimap());
    }

    /**
     * Allows the user to interact with NPCs or items.
     */
    private void interact(Command command)
    {
        try
        {
            String itemName = command.getSecondWord();
            Item roomItem = currentRoom.getItem(itemName);
            if(!command.hasSecondWord())
            {
                // if there is no second word, we don't know what to interact with...
                System.out.println("Interact with what?");
                return;
            }
            else if (roomItem != null)
            {
                System.out.println(roomItem.getDescription());
                return;
            }
        }
        catch(Exception e)
        {

        }

        try
        {
            String characterName = command.getSecondWord();
            Character roomCharacter = currentRoom.getCharacter(characterName);
            if (!command.hasSecondWord())
            {
                // if there is no second word, we don't know what to interact with...
                System.out.println("Interact with what?");
                return;
            }
            else if (roomCharacter != null)
            {
                System.out.println(roomCharacter.getDescription());
                return;
            }
            else
            {
                System.out.println("Invalid!");
                return;
            }
        }
        catch(Exception e)
        {
            System.out.println("Invalid!");
        }
    }

    /**
     * Allows the user to attack a slime or a boss slime.
     */
    private void attack(Command command) {
        try
        {
            String characterName = command.getSecondWord();
            Character roomCharacter = currentRoom.getCharacter(characterName);
            boolean hasWeapon = false;

            //checks if player has a weapon. if not, we do not let the player attack
            for (Item item : player.getInventory())
            {
                if (item.getIsWeapon())
                {
                    hasWeapon = true;
                }
            }

            if(!command.hasSecondWord() && hasWeapon == true)
            {
                // if there is no second word, we don't know what to attack...
                System.out.println("Attack what?");
                return;
            }
            else if (roomCharacter.getIsEnemy() == true && hasWeapon == true)
            {
                System.out.println("You have slain the " + roomCharacter.getName() + "!");
                currentRoom.removeCharacter(roomCharacter);
                return;
            }
            else if (hasWeapon == false)
            {
                System.out.println("You do not have a weapon!");
                return;
            }
            else
            {
                System.out.println("Invalid!");
                return;
            }
        }
        catch (Exception e)
        {
            System.out.println("Invalid!");
            return;
        }
    }

    /**
     * Allows the user to give items to certain NPCs.
     */
    private void give(Command command)
    {
        try
        {
            String characterName = command.getSecondWord();
            String itemName = command.getThirdWord();
            Character roomCharacter = currentRoom.getCharacter(characterName);
            Item yourItem = player.getItem(itemName);

            if(!command.hasSecondWord())
            {
                // if there is no second word, we don't know what to interact with...
                System.out.println("Give what to who?");
                return;
            }
            else if(!command.hasThirdWord() && currentRoom.getCharacter(roomCharacter) != null)
            {
                // if there is no second word, we don't know what to interact with...
                System.out.println("Give " + characterName + " what?");
                return;
            }
            else if (roomCharacter.getIsEnemy())
            {
                System.out.println("You cannot give items to this NPC!");
                return;
            }
            else if (roomCharacter.getIsSeer() && yourItem.getIsRelic())
            {
                System.out.println("Seer: Thank you... Please give me 3 relics and slay all the monsters, and we will all be free.");
                roomCharacter.addItem(yourItem);
                player.removeItem(yourItem);
                System.out.println("You gave your relic to the Seer.");
                return;
            }
            else
            {
                System.out.println("Invalid NPC, item, or command. Try again.");
                return;
            }
        }
        catch(Exception e)
        {
            System.out.println("Invalid NPC, item, or command. Try again.");
        }
    }

    /**
     * "objectives" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private void viewObjectives()
    {
        if (!isSlimesDead())
        {
            System.out.println("You still have to defeat all the slimes!");
        }

        if (!isSeerRelics())
        {
            System.out.println("You still have to give all 3 relics to the Seer!");
        }
    }

    /**
     * "quit" or "exit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord())
        {
            System.out.println("Quit what?");
            return false;
        }
        else
        {
            return true;  // signal that we want to quit
        }
    }
}
