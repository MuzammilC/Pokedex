package model;

import exceptions.MoveNotExist;
import exceptions.NotCaught;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RosterTest {

    Pokemon p1;
    Pokemon p2;
    Roster roster;

    @BeforeEach
    public void setUp() {
        roster = new Roster();
        p1 = new Pokemon("charmander", 3, 62, 42, new String[]{"growl", "scratch", "ember",
                "smokescreen"}, new String[]{"fire"}, 52, 43, 65, "blaze",
                "charmeleon", false);
        p2 = new Pokemon("bulbasaur", 1, 1, 45, new String[]{"growth", "leech seed",
                "vine whip", "tackle"}, new String[]{"grass", "poison"}, 49, 49, 45,
                "overgrow", "ivysaur", false);
    }

    @Test
    public void testGetName() {
        assertEquals("roster",roster.getName());
    }
    @Test
    public void testAddOnePokemonCaught() {
        try {
            p1.catchPokemon();
            assertTrue(p1.isCaught());
            roster.addPokemonToRoster(p1);

        } catch (NotCaught e) {
            fail("Not expecting an exception here");
        }
    }

    @Test
    public void testAddOnePokemonNotCaught() {
        try {
            assertFalse(p1.isCaught());
            roster.addPokemonToRoster(p1);
            fail();
        } catch (NotCaught e) {

        }
    }

    @Test
    public void testRemovePokemon() {
        try {
            p1.catchPokemon();
            p2.catchPokemon();
            roster.addPokemonToRoster(p1);
            roster.addPokemonToRoster(p2);
            assertTrue(roster.contains(p1));
            roster.removePokemonFromRoster(p1);

        } catch (NotCaught e) {
            fail("Not expecting an exception here");
        }
    }

    @Test
    public void testRemovePokemonNoPokemon() {
        try {
            assertFalse(p1.isCaught());
            roster.removePokemonFromRoster(p1);
            fail();
        } catch (NotCaught e) {

        }
    }

    @Test
    public void testRemovePokemonCaughtPokemon() {
        try {
            p1.catchPokemon();
            assertTrue(p1.isCaught());
            roster.removePokemonFromRoster(p1);
            fail();
        } catch (NotCaught e) {

        }
    }

    @Test
    public void testChangeMoveCaughtPokemonMoveExist() {
        try {
            p1.catchPokemon();
            roster.addPokemonToRoster(p1);
            roster.changeMove(p1, "growl", "flame thrower");
            String[] m1 = p1.getMoves();
            assertTrue(m1[0].equals("flame thrower"));
        } catch (NotCaught e) {
            fail("Not expecting an exception here!!");
        } catch (MoveNotExist e) {
            fail("Not expecting an exception here!!");
        }

    }

    @Test
    public void testChangeMoveCaughtPokemonMoveExistNotInRoster() {
        try {
            p1.catchPokemon();
            roster.changeMove(p1, "growl", "flame thrower");
            fail();
        } catch (NotCaught e) {

        } catch (MoveNotExist e) {

        }
    }

    @Test
    public void testChangeMoveCaughtPokemonMoveNotExist() {
        try {
            p1.catchPokemon();
            roster.addPokemonToRoster(p1);
            roster.changeMove(p1, "poison powder", "flame thrower");
            fail();
        } catch (NotCaught e) {

        } catch (MoveNotExist e) {

        }
    }

    @Test
    public void testChangeMoveNotCaughtMoveExist() {
        try {
            roster.addPokemonToRoster(p1);
            roster.changeMove(p1, "growl", "flame thrower");
            fail();
        } catch (NotCaught e) {

        } catch (MoveNotExist e) {

        }
    }

    @Test
    public void testChangeMoveNotCaughtMoveNotExist() {
        try {
            roster.addPokemonToRoster(p1);
            roster.changeMove(p1,"poison powder","flame thrower");
            fail();
        } catch (NotCaught e) {

        }
        catch (MoveNotExist e){

        }
    }
}

