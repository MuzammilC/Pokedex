package model;

import exceptions.MoveNotExist;
import exceptions.NotCaught;
import org.json.JSONObject;


// Represents a roster of Pokemon that is personalized by the user.
public class Roster extends CollectionOfPokemon  {

    private String name;

    // Effects: creates a roster
    public Roster() {
        super();
        name = "roster";
    }

    // Effects: returns the name "roster"
    public String getName() {
        return this.name;
    }

    // Modifies: this
    // Effects: changes a move of the pokemon
    public void changeMove(Pokemon p,String moveOld, String moveNew) throws NotCaught, MoveNotExist {
        if (this.contains(p)) {
            boolean b = false;
            String[] m = p.getMoves();
            for (int i = 0; i < m.length; i += 1) {
                if (m[i].equals(moveOld)) {
                    m[i] = moveNew;
                    b = true;
                    EventLog.getInstance().logEvent(new Event("Changed the move of Pokemon " + p.getName()
                            + " from the roster!!"));
                }
            }
            if (!b) {
                throw new MoveNotExist();
            }
        } else {
            throw new NotCaught();
        }
    }




    // Requires: given pokemon should be caught
    // Modifies: this
    // Effects: adds a pokemon which is caught to this
    public void addPokemonToRoster(Pokemon p) throws NotCaught {

        if (p.isCaught()) {
            this.addPokemon(p);
            EventLog.getInstance().logEvent(new Event("Added  " + p.getName() + " to the roster!!"));
        } else {
            throw new NotCaught();
        }

    }

    // Requires: contains the given pokemon in this
    // Modifies: this
    // Effects: removes a pokemon from this
    public void removePokemonFromRoster(Pokemon p) throws NotCaught {
        if (this.contains(p)) {
            this.removePokemon(p.getName());
            EventLog.getInstance().logEvent(new Event("Removed " + p.getName() + " from the roster!!"));
        } else {
            throw new NotCaught();
        }
    }



    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("TypeOfCollection",name);
        json.put("Pokemon", pokemonToJson());
        return json;
    }


}




