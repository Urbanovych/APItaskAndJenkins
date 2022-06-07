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

public class Main {

    public static HttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

    private static String getID(HttpResponse response) throws Exception {
        String id;
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(responseString);
        ObjectNode node = new ObjectMapper().readValue(responseString, ObjectNode.class);
        String idField = String.valueOf(node.get("id"));
        id = idField.replaceAll("^\"|\"$", "");
        return id;
    }

    public static String createBoard(String key, String token, String boardName) throws Exception { // work
        String id;
        HttpPost createBoard = new HttpPost("https://api.trello.com/1/boards/?key=" + key + "&token=" + token + "&name=" + boardName);
        HttpResponse response = httpClient.execute(createBoard);
        return id = getID(response);
    }

    public static void getBoard(String key, String token, String idBoard) throws Exception {
        HttpGet getBoard = new HttpGet("https://api.trello.com/1/boards/" + idBoard + "/memberships?key=" + key + "&token=" + token + "&id=VIwv0v0O");
        HttpResponse response = httpClient.execute(getBoard);
        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(responseString);
    }

    public static String createTODO(String key, String token, String idBoard, String listName) throws Exception {
        String listId;
        HttpPost createTODO = new HttpPost("https://api.trello.com/1/lists?name=" + listName +"&idBoard=" + idBoard + "&key=" + key +"&token=" + token);
        HttpResponse response = httpClient.execute(createTODO);
        return listId = getID(response);
    }

    public static String createDONE(String key, String token, String idBoard, String listName) throws Exception {
        String listId;
        HttpPost createDONE = new HttpPost("https://api.trello.com/1/lists?name=" + listName +"&idBoard=" + idBoard + "&key=" + key +"&token=" + token);
        HttpResponse response = httpClient.execute(createDONE);
        return listId = getID(response);
    }

    public static String createTrelloCard(String key, String token, String cardName, String idList) throws Exception {
        String idCard;
        HttpPost createCard = new HttpPost("https://api.trello.com/1/cards?name=" + cardName +"&idList=" + idList + "&key=" + key + "&token=" + token);
        HttpResponse response = httpClient.execute(createCard);
        return idCard = getID(response);
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

    public static void main(String[] args) {
        String key = "1d2ab2ddc3eb8d104f351c62589ed47b";
        String token = "46ffe16ee4e96f24a1ee49fd525413256bc10915038b322297e2dc524e701260";

        try {
            String idBoard = createBoard(key, token, "APItest5");
            getBoard(key, token, idBoard);
            String doneId = createTODO(key, token, idBoard, "Done");
            String todoId = createTODO(key, token, idBoard, "Todo");
            String cardId = createTrelloCard(key, token, "NewCard", todoId);
            moveCard(key, token, cardId, doneId);
            deleteBoard(key, token, idBoard);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}