package ui;

import exceptions.InvalidPokemon;
import exceptions.MoveNotExist;
import exceptions.NotCaught;
import model.Database;
import model.Pokemon;
import model.Roster;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Pokedex application
public class Pokedex {

    private Pokemon bulbasaur;
    private Pokemon ivySaur;
    private Pokemon charmander;
    private Pokemon charmeleon;
    private Pokemon squirtle;
    private Pokemon wartortle;
    private static final String JSON_STORE_ROSTER = "./data/roster.json";
    private static final String JSON_STORE_DATABASE = "./data/database.json";
    private Scanner input;
    private Database database;
    private Roster roster;
    private JsonWriter jsonWriterDatabase;
    private JsonWriter jsonWriterRoster;
    private JsonReader jsonReaderDatabase;
    private JsonReader jsonReaderRoster;


    // EFFECTS: runs the Pokedex application
    public Pokedex() {
        input = new Scanner(System.in);
        jsonWriterDatabase = new JsonWriter(JSON_STORE_DATABASE);
        jsonWriterRoster = new JsonWriter(JSON_STORE_ROSTER);
        jsonReaderDatabase = new JsonReader(JSON_STORE_DATABASE);
        jsonReaderRoster = new JsonReader(JSON_STORE_ROSTER);
        runPokedex();
    }

    // Attribution: Drew inspiration from the runTeller() method from the TellerApp
    //              class in the Teller Project.
    // MODIFIES: this
    // EFFECTS: processes user input
    public void runPokedex() {
        boolean keepGoing = true;
        int command;
        database = new Database();
        roster = new Roster();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        init();

        while (keepGoing) {
            displayMenu();
            command = input.nextInt();

            if (command == 12) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

    }

    // Attribution: Drew inspiration from the processCommand() method from the TellerApp
    //              class in the Teller Project.
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(int command) {
        if (command == 1) {
            catchPokemon();
        } else if (command == 2) {
            giveInfo();
        } else if (command == 3) {
            giveInfoRoster();
        } else if (command == 4) {
            pokemonToDatabase();
        } else if (command == 5) {
            addToRoster();
        } else if (command == 6) {
            removeFromRoster();
        } else if (command == 7) {
            changeMove();
        } else if (command == 8) {
            saveDatabase();
        } else if (command == 9) {
            saveRoster();
        } else if (command == 10) {
            loadRoster();
        } else {
            loadDatabase();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes some pokemon
    private void init() {
        bulbasaur = new Pokemon("bulbasaur",1,1,45, new String[]{"growth","leech seed",
                "vine whip","tackle"}, new String[]{"grass","poison"},49,49,45,
                "Overgrow","ivySaur",false);

        ivySaur = new Pokemon("ivysaur",2,142,60,new String[]{"tackle","growth","poison powder",
                "seed bomb"}, new String[]{"grass","poison"},62,63,60,"Overgrow",
                "venusaur", false);

        charmander = new Pokemon("charmander",3,62,42,new String[]{"growl","scratch","ember",
                "smokescreen"}, new String[]{"fire"},52,43,65,"Blaze",
                "charmeleon", false);

        charmeleon = new Pokemon("charmeleon",4,142,58,new String[]{"ember","growl","fire fang",
                "slash"},new String[]{"Fire"},64,58,80,"Blaze","charizard",
                false);

        squirtle = new Pokemon("squirtle",7,63,44,new String[]{"tackle","tail whip","water gun",
                "rapid spin"}, new String[]{"water"},48,65,43,"Torrent",
                "wartortle",false);

        wartortle = new Pokemon("wartortle",8,142,59,new String[]{"tackle","water pulse","rain dance",
                "rapid spin"},new String[]{"water"},63,80,58,"Torrent",
                "blastoise",false);

        database.addPokemon(bulbasaur);
        database.addPokemon(ivySaur);
        database.addPokemon(charmander);
        database.addPokemon(charmeleon);
        database.addPokemon(squirtle);





    }

    // Attribution: Drew inspiration from the displayMenu() method from the TellerApp
    //              class in the Teller Project.
    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1. Catch a Pokemon");
        System.out.println("\t2. Retrieve information of Pokemon");
        System.out.println("\t3. Retrieve information of Roster");
        System.out.println("\t4. Add a Pokemon to Database");
        System.out.println("\t5. Add a Pokemon to Roster");
        System.out.println("\t6. Remove a Pokemon from the Roster");
        System.out.println("\t7. Change the moves of a Pokemon from the Roster");
        System.out.println("\t8. Save Pokedex Database to file");
        System.out.println("\t9. Save user Pokemon Roster to file");
        System.out.println("\t10. Load saved Roster data");
        System.out.println("\t11. Load saved Database data");
        System.out.println("\t12. Quit");

    }

    // REQUIRES: non-empty database
    // MODIFIES: database
    // EFFECTS: changes a pokemon's catch status to true
    private void catchPokemon() {
        try {
            String name = selectPokemon();
            Pokemon p1 = database.getPokemon(name);
            p1.catchPokemon();
            System.out.println("Caught " + name + " successfully!!");
        } catch (InvalidPokemon e) {
            System.out.println("Invalid Pokemon name");
        }

    }

    private void pokemonToDatabase() {
        System.out.println("Enter the name of the Pokemon:");
        String name = input.next();
        System.out.println("Enter the ID number of the pokemon:");
        int id = input.nextInt();
        System.out.println("Enter the base expereince level of the Pokemon:");
        int exp = input.nextInt();
        System.out.println("Enter the base heath level of the Pokemon:");
        int hp = input.nextInt();
        System.out.println("Enter the moves of the Pokemon");
        String[] moves = new String[]{input.next(),input.next(), input.next(),input.next()};
        System.out.println("Enter the types of the Pokemon (max 2)");
        String[] types = new String[]{input.next(),input.next()};
        System.out.println("Enter the attack level of the Pokemon");
        int attkLvl = input.nextInt();
        System.out.println("Enter the defense level of the Pokemon");
        int defLvl = input.nextInt();
        System.out.println("Enter the speed level of the Pokemon");
        int speed = input.nextInt();
        System.out.println("Enter the name of the special ability of the Pokemon");
        String abilities = input.next();
        System.out.println("Enter the evolution of the Pokemon");
        String evolution = input.next();
        database.addPokemon(new Pokemon(name,id,exp,hp,moves,types,attkLvl,defLvl,speed,abilities, evolution, false));
    }

    // REQUIRES: non-empty database
    // EFFECTS: prints the information of a pokemon
    private void giveInfo() {
        try {
            String name = selectPokemon();
            Pokemon p1 = database.getPokemon(name);
            System.out.println("The information of " + name + " is :");
            System.out.println("Name: " + p1.getName());
            System.out.println("ID: " + p1.getId());
            System.out.println("Exp: " + p1.getExp());
            System.out.println("HP: " + p1.getHp());
            System.out.println("Moves: " + p1.printMoves());
            System.out.println("Types: " + p1.printTypes());
            System.out.println("Attack Level: " + p1.getAttackLevel());
            System.out.println("Defense Level: " + p1.getDefenseLevel());
            System.out.println("Speed: " + p1.getSpeed());
            System.out.println("Abilities: " + p1.getAbilities());
            System.out.println("Evolution: " + p1.getEvolution());
        } catch (InvalidPokemon e) {
            System.out.println("Invalid Pokemon name");
        }

    }

    // Effects: prints the names of pokemon in the roster
    private void giveInfoRoster() {
        ArrayList<String> info;
        info = roster.info();
        String s = "[";
        for (String i : info) {
            s = s + i + ",";
        }
        s = s + "]";
        System.out.println("The Pokemon in the roster are: " + s);

    }


    // MODIFIES: roster
    // EFFECTS: adds a pokemon to roster
    private void addToRoster() {
        try {
            String name = selectPokemon();
            Pokemon p1 = null;
            p1 = database.getPokemon(name);
            roster.addPokemonToRoster(p1);
            System.out.println("Added pokemon successfully!!");

        } catch (InvalidPokemon e) {
            System.out.println("Invalid Pokemon name");
        } catch (NotCaught e) {
            System.out.println("You have not caught this Pokemon !!");
        }

    }


    // MODIFIES: roster
    // EFFECTS: removes a given pokemon from the roster
    private void removeFromRoster() {
        try {
            String name = selectPokemon();
            Pokemon p1 = null;
            p1 = roster.getPokemon(name);
            roster.removePokemonFromRoster(p1);
            System.out.println("Removed Pokemon succesfully");


        } catch (InvalidPokemon e) {
            System.out.println("This Pokemon is not in the Roster !!");
        } catch (NotCaught e) {
            System.out.println("This Pokemon is not in the Roster !!");
        }


    }


    // MODIFIES: roster
    // EFFECTS: changes the move of a pokemon with a given move
    private void changeMove() {
        try {
            String name = selectPokemon();
            Pokemon p1 = null;
            p1 = roster.getPokemon(name);

            String moveOld = "";
            String moveNew = "";

            System.out.println("Enter the move you want to change:");
            moveOld = input.next();
            moveOld = moveOld.toLowerCase();

            System.out.println("Enter the new move");
            moveNew = input.next();
            moveNew = moveNew.toLowerCase();

            roster.changeMove(p1,moveOld,moveNew);;
            System.out.println("Changed the move of the Pokemon");


        } catch (InvalidPokemon e) {
            System.out.println("This Pokemon is not in the Roster !!");
        } catch (NotCaught e) {
            System.out.println("This Pokemon is not in the Roster !!");
        } catch (MoveNotExist e) {
            System.out.println("The Pokemon dosen't have the given move!!");;
        }


    }


    // Attribution: Drew inspiration from the selectAccount() method from the TellerApp
    //              class in the Teller Project.
    // EFFECTS: asks the user for a Pokemon name
    private String selectPokemon() throws InvalidPokemon {
        String selection = "";
        System.out.println("Enter the name of the Pokemon:");
        selection = input.next();
        selection = selection.toLowerCase();
        return selection;


    }

    // EFFECTS: saves the database to file
    private void saveDatabase() {
        try {
            jsonWriterDatabase.open();
            jsonWriterDatabase.write(database);
            jsonWriterDatabase.close();
            System.out.println("Saved the updated Pokemon Database to " + JSON_STORE_DATABASE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_DATABASE);
        }
    }

    // EFFECTS: saves the user Pokemon roster to file
    private void saveRoster() {
        try {
            jsonWriterRoster.open();
            jsonWriterRoster.write(roster);
            jsonWriterRoster.close();
            System.out.println("Saved the user Pokemon Roster to " + JSON_STORE_ROSTER);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_ROSTER);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads roster data from file
    private void loadRoster() {
        try {
            roster = (Roster) jsonReaderRoster.read();
            System.out.println("Loaded user roster data from " + JSON_STORE_ROSTER);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_ROSTER);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads database data from file
    private void loadDatabase() {
        try {
            database = (Database) jsonReaderDatabase.read();
            System.out.println("Loaded Pokedex database data from " + JSON_STORE_DATABASE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_DATABASE);
        }
    }


}

