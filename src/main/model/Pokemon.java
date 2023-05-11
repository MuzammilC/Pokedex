package model;

import org.json.JSONObject;
import persistence.Writable;


// Represents a Pokemon having a name, id, experience level, health level, moves, types, attack level, defense level,
// speed, abilities, evolution and whether it has been caught or naught.
public class Pokemon implements Writable {

    private String name;
    private int id;
    private int exp;
    private int hp;
    private String[] moves;
    private String[] types;
    private int attackLevel;
    private int defenseLevel;
    private int speed;
    private String abilities;
    private String evolution;
    private boolean caught;

    // Requires:
    // Modifies: this
    // Effects: creates a pokemon with the given attributes
    public Pokemon(String name, int id, int exp, int hp, String[] moves, String[] types,
                   int attackLevel, int defenseLevel, int speed, String abilities, String evolution,
                   boolean caught) {
        this.name = name;
        this.id = id;
        this.exp = exp;
        this.hp = hp;
        this.moves = moves;
        this.types = types;
        this.attackLevel = attackLevel;
        this.defenseLevel = defenseLevel;
        this.speed = speed;
        this.abilities = abilities;
        this.evolution = evolution;
        this.caught = caught;


    }

    // Effects: returns the name of the pokemon
    public String getName() {
        return name;
    }

    // Effects: returns the pokemon's ID
    public int getId() {
        return id;

    }

    // Effects: returns the pokemon's experience level
    public int getExp() {
        return exp;
    }

    // Effects: returns the pokemon's health level
    public int getHp() {
        return hp;
    }

    // Effects: returns the pokemon's Attack level
    public int getAttackLevel() {
        return attackLevel;
    }

    // Effects: returns the pokemon's defense level
    public int getDefenseLevel() {
        return defenseLevel;
    }

    // Effects: returns the pokemon's speed level
    public int getSpeed() {
        return speed;
    }

    // Effects: returns the pokemon's special abilities
    public String getAbilities() {
        return abilities;
    }

    // Effects: returns the pokemon's moves
    public String[] getMoves() {
        return moves;
    }

    // Effects: returns the moves in the printable form
    public String printMoves() {
        String[] m = this.getMoves();
        String s = "[";
        for (String i : m) {
            s = s + i + ",";
        }
        return s + "]";
    }

    // Effects: returns the pokemon's type
    public String[] getTypes() {
        return types;
    }

    // Effects: returns the types in the printable form
    public String printTypes() {
        String[] t = this.getTypes();
        String s = "[";
        for (String i : t) {
            s = s + i + ",";
        }
        return s + "]";
    }

    // Effects: returns the pokemon's next evolution
    public String getEvolution() {
        return evolution;
    }

    // Effects: returns true if pokemon is caught
    public boolean isCaught() {
        return caught;
    }

    // Effects: catches a pokemon
    public void catchPokemon() {
        this.caught = true;
        EventLog.getInstance().logEvent(new Event("Caught Pokemon" + this.getName()));
    }

    // Requires: damage > 0
    // Modifies: this
    // Effects: decreases the health level by damage
    public void doDamage(int damage) {
        this.hp -= damage;

    }

    // Effects: Returns the attack power of a pokemon
    public int attackPower() {

        return (this.attackLevel / 10) * this.exp;
    }

    // returns this as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("id",id);
        json.put("exp",exp);
        json.put("hp",hp);
        json.put("moves",moves);
        json.put("types",types);
        json.put("attacklevel",attackLevel);
        json.put("defenselevel",defenseLevel);
        json.put("speed",speed);
        json.put("abilities",abilities);
        json.put("evolution",evolution);
        json.put("caught",caught);
        return json;

    }

}








