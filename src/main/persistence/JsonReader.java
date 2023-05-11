package persistence;

import model.CollectionOfPokemon;
import model.Database;
import model.Pokemon;
import model.Roster;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads collection of pokemon from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads collection of pokemon from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CollectionOfPokemon read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCollectionOfPokemon(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses collection of pokemon from JSON object and returns it
    private CollectionOfPokemon parseCollectionOfPokemon(JSONObject jsonObject) {
        String name = jsonObject.getString("TypeOfCollection");
        if (name.equals("roster")) {
            Roster roster = new Roster();
            addPokemons(roster, jsonObject);
            return roster;
        } else {
            Database database = new Database();
            addPokemons(database, jsonObject);
            return database;
        }
    }


    // MODIFIES: CollectionofPokemon
    // EFFECTS: parses pokemon from JSON object and adds them to collection of pokemon
    private void addPokemons(CollectionOfPokemon cp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Pokemon");
        for (Object json : jsonArray) {
            JSONObject nextPokemon = (JSONObject) json;
            addPokemon(cp, nextPokemon);
        }
    }


    // MODIFIES: CollectionofPokemon
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addPokemon(CollectionOfPokemon cp, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int id = (int) jsonObject.get("id");
        int exp = (int) jsonObject.get("exp");
        int hp = (int) jsonObject.get("hp");
        String[] moves =  jsonToStringArray((JSONArray) jsonObject.get("moves"));
        String[] types =  jsonToStringArray((JSONArray) jsonObject.get("types"));
        int attacklevel = (int) jsonObject.get("attacklevel");
        int defenselevel = (int) jsonObject.get("defenselevel");
        int speed = (int) jsonObject.get("speed");
        String abilities = jsonObject.getString("abilities");
        String evolution = jsonObject.getString("evolution");
        Boolean caught = (Boolean) jsonObject.get("caught");
        Pokemon p1 = new Pokemon(name, id, exp, hp, moves, types, attacklevel, defenselevel, speed, abilities,
                evolution, caught);
        cp.addPokemon(p1);
    }


    // EFFECTS: returns a JSON Array as a String[]
    private String[] jsonToStringArray(JSONArray j) {
        List<String> newList = new ArrayList<String>();
        for (int i = 0; i < j.length(); i++) {
            newList.add(j.getString(i));

        }
        int size = newList.size();
        String[] stringArray = newList.toArray(new String[size]);
        return stringArray;

    }
}

