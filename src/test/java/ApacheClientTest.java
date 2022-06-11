import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import urbanovych.ApacheClient;

import java.util.ArrayList;


public class ApacheClientTest {

    static CloseableHttpClient httpClient;
    String key = "1d2ab2ddc3eb8d104f351c62589ed47b";
    String token = "46ffe16ee4e96f24a1ee49fd525413256bc10915038b322297e2dc524e701260";

    @AfterAll
    public static void setup() {
        httpClient = HttpClients.createDefault();
    }

    private static void statusCode200Validation(HttpResponse response) {
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200, "Responce code is not 200");
    }

    private static void assertNameEquals(ArrayList<String> listWithName, String expected) {
        Assert.assertEquals(listWithName.get(1), expected, "Name is not " + expected);
    }

    @Test
    public void apacheClientAllStepsTest() throws Exception {
            HttpResponse createBoardResponse = ApacheClient.createBoard(key, token, "ApacheClient");
            ArrayList<String> board = ApacheClient.getStringJsonVariable(createBoardResponse, "id", "name");

            ApacheClient.getBoard(key, token, board.get(0));

            HttpResponse createDoneResponse = ApacheClient.createList(key, token, board.get(0), "Done");
            ArrayList<String> doneList = ApacheClient.getStringJsonVariable(createDoneResponse, "id", "name");

            HttpResponse createTodoResponse = ApacheClient.createList(key, token, board.get(0), "Todo");
            ArrayList<String> todoList = ApacheClient.getStringJsonVariable(createTodoResponse, "id", "name");

            HttpResponse createTrelloCardResponse = ApacheClient.createTrelloCard(key, token, "NewCard", (String) todoList.get(0));
            ArrayList<String> card = ApacheClient.getStringJsonVariable(createTrelloCardResponse, "id", "name");

            assertNameEquals(doneList, "Done");
            assertNameEquals(todoList, "Todo");
            assertNameEquals(card, "NewCard");

            ApacheClient.moveCard(key, token, card.get(0), doneList.get(0));
            HttpResponse deleteBoardResponse = ApacheClient.deleteBoard(key, token, board.get(0));
            statusCode200Validation(deleteBoardResponse);
    }
}
