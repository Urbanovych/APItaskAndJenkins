import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.ArrayList;
import static urbanovych.RestAssured.*;

public class RestAssuredTrelloTest {

    String key = "1d2ab2ddc3eb8d104f351c62589ed47b";
    String token = "46ffe16ee4e96f24a1ee49fd525413256bc10915038b322297e2dc524e701260";
    String name = "Assured1";

    private static void assertNameEquals(ArrayList<String> listWithName, String expected) {
        Assert.assertEquals(listWithName.get(1), expected, "Name is not " + expected);
    }

    @Test
    public void restAssuredAllStepsTest() {
        ValidatableResponse createBoardResponse = createBoard(key, token, name);
        ArrayList<String> createBoardList = jsonParser(createBoardResponse);

        getBoard(key, token, createBoardList.get(0));

        ValidatableResponse createDONE = createList(key, token, createBoardList.get(0), "DONE");
        ArrayList<String> createDONEList = jsonParser(createDONE);
        ValidatableResponse createTODO = createList(key, token, createBoardList.get(0), "TODO");
        ArrayList<String> createTODOList = jsonParser(createTODO);
        ValidatableResponse createCard = createTrelloCard(key, token, "Automation", createTODOList.get(0));
        ArrayList<String> createCardList = jsonParser(createCard);

        assertNameEquals(createDONEList, "DONE");
        assertNameEquals(createTODOList, "TODO");
        assertNameEquals(createCardList, "Automation");

        moveCard(key, token, createCardList.get(0), createDONEList.get(0));

        deleteBoard(key, token, createBoardList.get(0));
    }
}
