package Retrofit;

public class AccessAPI {

    private String key;
    private String token;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccessAPI(String key, String token) {
        this.key = key;
        this.token = token;
    }
}
