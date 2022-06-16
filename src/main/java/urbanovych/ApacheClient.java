package urbanovych;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;

public class ApacheClient {

    public static HttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

    public static ArrayList<String> getStringJsonVariable(HttpResponse response, String variableId, String variableName) throws Exception {

        String id;
        String name;
        ArrayList<String> myArrayList = new ArrayList<>();

        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(responseString);
        ObjectNode node = new ObjectMapper().readValue(responseString, ObjectNode.class);

        String idField = String.valueOf(node.get(variableId));
        String nameField = String.valueOf(node.get(variableName));
        id = idField.replaceAll("^\"|\"$", "");
        name = nameField.replaceAll("^\"|\"$", "");;

        myArrayList.add(id);
        myArrayList.add(name);

        return myArrayList;
    }

    public static HttpResponse createBoard(String key, String token, String boardName) throws Exception {
        HttpPost createBoard = new HttpPost("https://api.trello.com/1/boards/?key=" + key + "&token=" + token + "&name=" + boardName);
        HttpResponse response = httpClient.execute(createBoard);
        return response;
    }

    public static void getBoard(String key, String token, String idBoard) throws Exception {
        HttpGet getBoard = new HttpGet("https://api.trello.com/1/boards/" + idBoard + "/memberships?key=" + key + "&token=" + token + "&id=VIwv0v0O");
        HttpResponse response = httpClient.execute(getBoard);
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(responseString);
    }

    public static HttpResponse createList(String key, String token, String idBoard, String listName) throws Exception {
        HttpPost createList = new HttpPost("https://api.trello.com/1/lists?name=" + listName +"&idBoard=" + idBoard + "&key=" + key +"&token=" + token);
        HttpResponse response = httpClient.execute(createList);
        return response;
    }

    public static HttpResponse createTrelloCard(String key, String token, String cardName, String idList) throws Exception {
        HttpPost createCard = new HttpPost("https://api.trello.com/1/cards?name=" + cardName +"&idList=" + idList + "&key=" + key + "&token=" + token);
        HttpResponse response = httpClient.execute(createCard);
        return response;
    }

    public static void moveCard(String key, String token, String idCard, String idList) throws Exception {
        HttpPut moveCard = new HttpPut("https://api.trello.com/1/cards/" + idCard + "?idList=" + idList + "&key=" + key + "&token=" + token);
        HttpResponse response = httpClient.execute(moveCard);
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(responseString);
    }

    public static HttpResponse deleteBoard(String key, String token, String idBoard) throws Exception { // work
        HttpDelete deleteBoard = new HttpDelete("https://api.trello.com/1/boards/" + idBoard +"?key=" + key +"&token=" + token);
        HttpResponse response = httpClient.execute(deleteBoard);
        return response;
    }
}