package Retrofit;

public class TrelloList extends AccessAPI {

    private String name;

    private String id;

    public TrelloList(String name, String id, String key, String token) {
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
