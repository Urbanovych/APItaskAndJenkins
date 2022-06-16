import Retrofit.Board;
import Retrofit.Card;
import Retrofit.TrelloList;
import org.junit.jupiter.api.Test;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import Retrofit.RetrofitTrelloSteps;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RetrofitOKhttpTest {

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.trello.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    static RetrofitTrelloSteps service = retrofit.create(RetrofitTrelloSteps.class);
    private static String key = "1d2ab2ddc3eb8d104f351c62589ed47b";
    private static String token = "46ffe16ee4e96f24a1ee49fd525413256bc10915038b322297e2dc524e701260";

    @Test
    public void retrofitAllStepsTest() throws IOException {
        Call<Board> create = service.createBoard(key, token, "Retrofit");
        Response<Board> createBoardResponse = create.execute();
        System.out.println(createBoardResponse.raw());

        assertTrue(createBoardResponse.isSuccess());
        assertEquals("Retrofit", createBoardResponse.body().getName(), "Name are not same");
        String idBoard = createBoardResponse.body().getId();

        Call<TrelloList> createDONElist = service.createList("DONE", idBoard, key, token);
        Response<TrelloList> createDONElistResponse = createDONElist.execute();
        System.out.println(createDONElistResponse.raw());

        assertTrue(createDONElistResponse.isSuccess());
        assertEquals("DONE", createDONElistResponse.body().getName(), "Name are not same");
        String DONEid = createDONElistResponse.body().getId();

        Call<TrelloList> createTODOlist = service.createList("TODO", idBoard, key, token);
        Response<TrelloList> createTODOlistResponse = createTODOlist.execute();
        System.out.println(createTODOlistResponse.raw());

        assertTrue(createTODOlistResponse.isSuccess());
        assertEquals("TODO", createTODOlistResponse.body().getName(), "Name are not same");
        String TODOid = createTODOlistResponse.body().getId();

        Call<Card> createCard = service.createCard("MyCard", TODOid, key, token);
        Response<Card> createCardResponse = createCard.execute();
        System.out.println(createCardResponse.raw());

        assertTrue(createCardResponse.isSuccess());
        assertEquals("MyCard", createCardResponse.body().getName(), "Name are not same");
        String idCard = createCardResponse.body().getId();

        Call<Card> moveCard = service.moveCard(idCard, DONEid, key, token);
        Response<Card> moveCardResponse = moveCard.execute();
        System.out.println(moveCardResponse.raw());

        assertTrue(moveCardResponse.isSuccess());

        Call<Board> deleteBoard = service.deleteBoard(idBoard, key, token);
        Response<Board> deleteBoardResponse = deleteBoard.execute();
        System.out.println(deleteBoardResponse.raw());

        assertTrue(deleteBoardResponse.isSuccess());
    }

}
