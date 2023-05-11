package model;

import exceptions.InvalidPokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionOfPokemonTest {

    Pokemon p1;
    Pokemon p2;
    CollectionOfPokemon collection;

    @BeforeEach
    public void setUp() {
        collection = new Database();
        p1 = new Pokemon("charmander", 3, 62, 42, new String[]{"growl", "scratch", "ember",
                "smokescreen"}, new String[]{"fire"}, 52, 43, 65, "blaze",
                "charmeleon", false);
        p2 = new Pokemon("bulbasaur", 1, 1, 45, new String[]{"growth", "leech seed",
                "vine whip", "tackle"}, new String[]{"grass", "poison"}, 49, 49, 45,
                "overgrow", "ivysaur", false);

    }

    @Test
    public void testGetPokemonValidName() {
        try {
            collection.addPokemon(p1);
            collection.addPokemon(p2);
            collection.getPokemon(p1.getName());
        } catch (InvalidPokemon e) {
            fail("Did not expect an exception here!!");
        }

    }

    @Test
    public void testgetPokemonInvalidName() {
        try {
            collection.addPokemon(p1);
            collection.addPokemon(p2);
            collection.getPokemon("halibut");
            fail();
        } catch (InvalidPokemon e) {

        }

    }

    @Test
    public void testGetPokemonEmptyCollectionValidName() {
        try {
            collection.getPokemon(p1.getName());
            fail();
        } catch (InvalidPokemon e) {

        }
    }

    @Test
    public void testGetPokemonEmptyCollectionInvalidName() {
        try {
            collection.getPokemon("brownie");
            fail();
        } catch (InvalidPokemon e) {

        }
    }

    @Test
    public void testRemovePokemonValidName() {
        collection.addPokemon(p1);
        collection.addPokemon(p2);
        String a = "charmander";
        assertTrue(a.equals(p1.getName()));
        collection.removePokemon(a);
        assertFalse(collection.contains(p1));

    }

    @Test
    public void testRemovePokemonInvalidName() {
        collection.addPokemon(p1);
        collection.addPokemon(p2);
        String a = "lightning";
        assertFalse(a.equals(p1.getName()));
        collection.removePokemon(a);
        assertTrue(collection.contains(p1));
        assertTrue(collection.contains(p2));

    }

    @Test
    public void testInfoEmptyCollection() {
        ArrayList<String> testL = new ArrayList<>();
        assertFalse(collection.contains(p1));
        assertFalse(collection.contains(p2));
        assertEquals(testL,collection.info());
    }

    @Test
    public void testInfoNonEmptyCollection() {
        ArrayList<String> testL = new ArrayList<>();
        collection.addPokemon(p1);
        collection.addPokemon(p2);
        assertTrue(collection.contains(p1));
        assertTrue(collection.contains(p2));
        testL.add(p1.getName());
        testL.add(p2.getName());
        assertEquals(testL,collection.info());

    }

    @Test
    public void testGetFromIndex() {
        collection.addPokemon(p1);
        collection.addPokemon(p2);
        assertEquals(p1,collection.get(0));
        assertEquals(p2,collection.get(1));

    }

    @Test
    public void testGetSize() {
        assertEquals(0,collection.size());
        collection.addPokemon(p1);
        collection.addPokemon(p2);
        assertEquals(2,collection.size());
    }

    @Test
    public void testRemove() {
        assertEquals(0,collection.size());
        collection.addPokemon(p1);
        collection.addPokemon(p2);
        assertEquals(2,collection.size());
        collection.remove(0);
        assertEquals(1,collection.size());
    }

}



