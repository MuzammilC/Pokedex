package model;

import exceptions.InvalidPokemon;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a collection of Pokemon
public abstract class CollectionOfPokemon implements Writable {

    private ArrayList<Pokemon> collection;


    // Effects: creates a list of Pokemon
    public CollectionOfPokemon() {
        this.collection = new ArrayList<>();

    }

    // Effects: returns the number of pokemon in the collection
    public int numPokemon() {
        return collection.size();
    }

    // Effects: returns the collection
    public ArrayList<Pokemon> getPokemonInCollection() {
        return collection;
    }

    // Requires: non-empty list of Pokemon
    // Effects: returns the pokemon with the given name
    public Pokemon getPokemon(String n) throws InvalidPokemon {
        Pokemon req = null;
        for (Pokemon p : collection) {
            if (p.getName().equals(n)) {
                req = p;
            }
        }
        if (req != null) {
            return req;
        } else {
            throw new InvalidPokemon();
        }
    }

    // Requires: non-empty list of Pokemon
    // Modifies: this
    // Effects: removes a pokemon from the collection
    public void removePokemon(String n) {
        for (Pokemon p : collection) {
            if (p.getName().equals(n)) {
                collection.remove(p);

            }
        }
    }

    // Modifies: this
    // Effects: adds a Pokemon to the collection
    public void addPokemon(Pokemon p) {
        this.collection.add(p);

    }

    // Effects: returns true if a pokemon is in the collection
    public boolean contains(Pokemon p) {
        return collection.contains(p);
    }

    // Modifies: this
    // Effects: returns the names of the pokemon in the collection
    public ArrayList<String> info() {
        ArrayList<String> l = new ArrayList<>();
        for (Pokemon i : collection) {
            l.add(i.getName());
        }

        return l;
    }

    // Effects: returns the size of the collection
    public int size() {
        return collection.size();
    }

    // Effects: removes the pokemon from the given index
    public void remove(int index) {
        collection.remove(index);

    }

    //
    // Effects: returns the pokemon from the given index
    public Pokemon get(int i) {
        return collection.get(i);
    }

    // EFFECTS: returns things in this collection of pokemon as a JSON array
    protected JSONArray pokemonToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Pokemon p : collection) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }

    public abstract JSONObject toJson();


}
