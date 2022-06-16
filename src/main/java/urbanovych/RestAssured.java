package urbanovych;

import java.util.ArrayList;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class RestAssured {

    private static String key = "1d2ab2ddc3eb8d104f351c62589ed47b";
    private static String token = "46ffe16ee4e96f24a1ee49fd525413256bc10915038b322297e2dc524e701260";
    private static String name = "AssuredAllSteps";

    public static ArrayList<String> jsonParser(ValidatableResponse response) {
        ArrayList<String> jsonData = new ArrayList<>();
        String id = response.extract().path("id");
        String name = response.extract().path("name");
        jsonData.add(id);
        jsonData.add(name);
        return jsonData;
    }

    public static ValidatableResponse createBoard(String key, String token, String boardName) {
        io.restassured.RestAssured.baseURI = "https://api.trello.com";
        ValidatableResponse response = given().contentType(ContentType.JSON).log().all()
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("name", boardName)
                .when().post("/1/boards/")
                .then().log().all().assertThat().statusCode(200);
        return response;
    }

    public static ValidatableResponse getBoard(String key, String token, String boardName) {
        io.restassured.RestAssured.baseURI = "https://api.trello.com";
        ValidatableResponse response = given().contentType(ContentType.JSON).log().all()
                .queryParam("key", key)
                .queryParam("token", token)
                .when().get("/1/boards/" + boardName +"/memberships")
                .then().log().all().assertThat().statusCode(200);
        return response;
    }

    public static ValidatableResponse createList(String key, String token, String idBoard, String listName) {
        io.restassured.RestAssured.baseURI = "https://api.trello.com";
        ValidatableResponse response = given().contentType(ContentType.JSON).log().all()
                .queryParam("name", listName)
                .queryParam("idBoard", idBoard)
                .queryParam("key", key)
                .queryParam("token", token)
                .when().post("/1/lists")
                .then().log().all().assertThat().statusCode(200);
        return response;
    }

    public static ValidatableResponse createTrelloCard(String key, String token, String cardName, String idList) {
        io.restassured.RestAssured.baseURI = "https://api.trello.com";
        ValidatableResponse response = given().contentType(ContentType.JSON).log().all()
                .queryParam("name", cardName)
                .queryParam("idList", idList)
                .queryParam("key", key)
                .queryParam("token", token)
                .when().post("/1/cards")
                .then().log().all().assertThat().statusCode(200);
        return response;
    }

    public static void moveCard(String key, String token, String idCard, String idList) {
        io.restassured.RestAssured.baseURI = "https://api.trello.com";
        ValidatableResponse response = given().contentType(ContentType.JSON).log().all()
                .queryParam("idList", idList)
                .queryParam("key", key)
                .queryParam("token", token)
                .when().put("/1/cards/" + idCard)
                .then().log().all().assertThat().statusCode(200);
    }

    public static ValidatableResponse deleteBoard(String key, String token, String boardId) {
        io.restassured.RestAssured.baseURI = "https://api.trello.com";
        ValidatableResponse response = given().contentType(ContentType.JSON).log().all()
                .queryParam("key", key)
                .queryParam("token", token)
                .when().delete("/1/boards/" + boardId)
                .then().log().all().assertThat().statusCode(200);
        return response;
    }

    public static void main(String[] args) {
        ValidatableResponse createBoardResponse = createBoard(key, token, name);
        ArrayList<String> createBoardList = jsonParser(createBoardResponse);

        getBoard(key, token, createBoardList.get(0));

        ValidatableResponse createDONE = createList(key, token, createBoardList.get(0), "DONE");
        ArrayList<String> createDONEList = jsonParser(createDONE);
        ValidatableResponse createTODO = createList(key, token, createBoardList.get(0), "TODO");
        ArrayList<String> createTODOList = jsonParser(createTODO);
        ValidatableResponse createCard = createTrelloCard(key, token, "Automation", createTODOList.get(0));
        ArrayList<String> createCardList = jsonParser(createCard);

        moveCard(key, token, createCardList.get(0), createDONEList.get(0));

        deleteBoard(key, token, createBoardList.get(0));
    }
}
