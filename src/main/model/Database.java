package model;

import org.json.JSONObject;

// Represents the inherent database of the Pokedex
public class Database extends CollectionOfPokemon {

    String name;

    // Effects: creates a database within the Pokedex
    public Database() {
        super();
        name = "database";
    }

    // Effects: returns the name "database"
    public String getName() {
        return this.name;
    }


    // Converts a database into a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("TypeOfCollection",name);
        json.put("Pokemon", pokemonToJson());
        return json;
    }

    public void addPokemonToDatabase(Pokemon p) {
        addPokemon(p);
        EventLog.getInstance().logEvent(new Event("Added " + p.getName() + " to the Database"));
    }

    public void removePokemonFromDatabase(String n) {
        removePokemon(n);
        EventLog.getInstance().logEvent(new Event("Removed " + n + " from the Database"));

    }


}
