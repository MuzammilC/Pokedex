package persistence;

import exceptions.NotCaught;
import model.Database;
import model.Pokemon;
import model.Roster;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Roster roster = new Roster();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRoster() {
        try {
            Roster roster = new Roster();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRoster.json");
            writer.open();
            writer.write(roster);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRoster.json");
            roster = (Roster) reader.read();
            assertEquals("roster", roster.getName());
            assertEquals(0, roster.numPokemon());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyDatabase() {
        try {
            Database database = new Database();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDatabase.json");
            writer.open();
            writer.write(database);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDatabase.json");
            database = (Database) reader.read();
            assertEquals("database", database.getName());
            assertEquals(0, database.numPokemon());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRoster() {
        try {
            Roster roster = new Roster();
            roster.addPokemonToRoster(new Pokemon("charmander", 3, 62, 42, new String[]{"growl", "scratch", "ember",
                            "smokescreen"}, new String[]{"fire"}, 52, 43, 65, "blaze",
                            "charmeleon", true));
            roster.addPokemonToRoster(new Pokemon("bulbasaur", 1, 1, 45, new String[]{"growth", "leech seed",
                            "vine whip", "tackle"}, new String[]{"grass", "poison"}, 49, 49, 45,
                            "overgrow", "ivysaur", true));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRoster.json");
            writer.open();
            writer.write(roster);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRoster.json");
            roster = (Roster) reader.read();
            assertEquals("roster", roster.getName());
            List<Pokemon> rosterPokemon = roster.getPokemonInCollection();
            assertEquals(2, rosterPokemon.size());
            checkPokemon("charmander", 3, rosterPokemon.get(0));
            checkPokemon("bulbasaur", 1, rosterPokemon.get(1));

        } catch (IOException | NotCaught e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDatabase() {
        try {
            Database database = new Database();
            database.addPokemon(new Pokemon("charmander", 3, 62, 42, new String[]{"growl", "scratch", "ember",
                    "smokescreen"}, new String[]{"fire"}, 52, 43, 65, "blaze",
                    "charmeleon", false));
            database.addPokemon(new Pokemon("bulbasaur", 1, 1, 45, new String[]{"growth", "leech seed",
                    "vine whip", "tackle"}, new String[]{"grass", "poison"}, 49, 49, 45,
                    "overgrow", "ivysaur", false));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDatabase.json");
            writer.open();
            writer.write(database);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDatabase.json");
            database = (Database) reader.read();
            assertEquals("database", database.getName());
            List<Pokemon> databasePokemon = database.getPokemonInCollection();
            assertEquals(2, databasePokemon.size());
            checkPokemon("charmander", 3, databasePokemon.get(0));
            checkPokemon("bulbasaur", 1, databasePokemon.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

