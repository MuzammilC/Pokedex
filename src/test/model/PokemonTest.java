package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    Pokemon p1;
    Pokemon p2;

    @BeforeEach
    public void setUp() {
        p1 = new Pokemon("charmander",3,62,42,new String[]{"growl","scratch","ember",
                "smokescreen"}, new String[]{"fire"},52,43,65,"blaze",
                "charmeleon", false);
        p2 = new Pokemon("bulbasaur",1,1,45, new String[]{"growth","leech seed",
                "vine whip","tackle"}, new String[]{"grass","poison"},49,49,45,
                "overgrow","ivysaur",false);
    }

    @Test
    public void testName() {
       String a = p1.getName();
       String b = p2.getName();
       assertTrue(a.equals("charmander"));
       assertTrue(b.equals("bulbasaur"));

    }

    @Test
    public void testId() {
        int a = p1.getId();
        int b = p2.getId();
        assertEquals(3,a);
        assertEquals(1,b);
    }

    @Test
    public void testExp() {
        int a = p1.getExp();
        int b = p2.getExp();
        assertEquals(62,a);
        assertEquals(1,b);
    }

    @Test
    public void testHp() {
        int a = p1.getHp();
        int b = p2.getHp();
        assertEquals(42,a);
        assertEquals(45,b);
    }

    @Test
    public void testAttackLevel() {
        int a = p1.getAttackLevel();
        int b = p2.getAttackLevel();
        assertEquals(52,a);
        assertEquals(49,b);
    }

    @Test
    public void testDefenseLevel() {
        int a = p1.getDefenseLevel();
        int b = p2.getDefenseLevel();
        assertEquals(43,a);
        assertEquals(49,b);
    }

    @Test
    public void testSpeed() {
        int a = p1.getSpeed();
        int b = p2.getSpeed();
        assertEquals(65,a);
        assertEquals(45,b);
    }

    @Test
    public void testAbilities() {
        String a = p1.getAbilities();
        String b = p2.getAbilities();
        assertTrue(a.equals("blaze"));
        assertTrue(b.equals("overgrow"));

    }

    @Test
    public void testPrintTypes() {
        String[] a = p1.getTypes();
        String[] b = p2.getTypes();
        assertEquals("[fire,]",p1.printTypes());
        assertEquals("[grass,poison,]",p2.printTypes());
    }

    @Test
    public void testPrintMoves() {
        String[] a = p1.getMoves();
        String[] b = p2.getTypes();
        assertEquals("[growl,scratch,ember,smokescreen,]",p1.printMoves());
        assertEquals("[growth,leech seed,vine whip,tackle,]",p2.printMoves());
    }

    @Test
    public void testEvolution() {
        String a = p1.getEvolution();
        String b = p2.getEvolution();
        assertEquals("charmeleon",a);
        assertEquals("ivysaur",b);

    }

    @Test
    public void testCaught() {
        assertFalse(p1.isCaught());
        p1.catchPokemon();
        assertTrue(p1.isCaught());
    }

    @Test
    public void testDamage() {
        int a = p1.getHp();
        assertEquals(42,a);
        p1.doDamage(10);
        int b = p1.getHp();
        assertEquals(32,b);
    }

    @Test
    public void testAttackPower() {
        int a = p1.attackPower();
        assertEquals(310.0,a);
    }
}