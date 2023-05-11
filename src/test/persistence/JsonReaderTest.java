package persistence;

import model.CollectionOfPokemon;
import model.Database;
import model.Pokemon;
import model.Roster;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CollectionOfPokemon cp = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRoster() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRoster.json");
        try {
            Roster roster = (Roster) reader.read();
            assertEquals("roster", roster.getName());
            assertEquals(0, roster.numPokemon());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDatabase.json");
        try {
            Database database = (Database) reader.read();
            assertEquals("database", database.getName());
            assertEquals(0, database.numPokemon());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralRoster() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRoster.json");
        try {
            Roster roster = (Roster) reader.read();
            assertEquals("roster", roster.getName());
            List<Pokemon> pokemonInRoster = roster.getPokemonInCollection();
            assertEquals(2, pokemonInRoster.size());
            checkPokemon("charmander", 3, pokemonInRoster.get(0));
            checkPokemon("bulbasaur", 1, pokemonInRoster.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDatabase() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDatabase.json");
        try {
            Database database = (Database) reader.read();
            assertEquals("database", database.getName());
            List<Pokemon> pokemonInRoster = database.getPokemonInCollection();
            assertEquals(5, pokemonInRoster.size());
            checkPokemon("bulbasaur", 1, pokemonInRoster.get(0));
            checkPokemon("ivysaur", 2, pokemonInRoster.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
