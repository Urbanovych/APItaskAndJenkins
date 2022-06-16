package Retrofit;

import com.google.gson.annotations.SerializedName;

public class Board extends AccessAPI{

    private String name;

    private String id;

    public Board(String key, String token, String name, String id) {
        super(key, token);
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
