package persistence;

import org.json.JSONObject;

public interface Writable {

    // Effects: returns this as JSON object
    JSONObject toJson();
}
