package persistence;

import model.Pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPokemon(String name, int id, Pokemon p1) {
        assertEquals(name, p1.getName());
        assertEquals(id, p1.getId());

    }
}
