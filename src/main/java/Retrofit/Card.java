package Retrofit;

public class Card extends AccessAPI {

    private String id;
    private String name;

    public Card(String key, String token, String name,
                String id) {
        super(key, token);
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
