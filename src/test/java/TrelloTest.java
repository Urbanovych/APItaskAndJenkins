import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import urbanovych.Main;


public class TrelloTest {

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

    @Test
    public void mainTest() throws Exception {
        String idBoard = Main.createBoard(key, token, "APItestJunit");
        Main.getBoard(key, token, idBoard);
        String doneId = Main.createDONE(key, token, idBoard, "Done");
        String todoId = Main.createTODO(key, token, idBoard, "Todo");
        String cardId = Main.createTrelloCard(key, token, "NewCard", todoId);
        Main.moveCard(key, token, cardId, doneId);
        HttpResponse response = Main.deleteBoard(key, token, idBoard);
        statusCode200Validation(response);
    }
}
