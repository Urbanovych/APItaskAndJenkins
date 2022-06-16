package Retrofit;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import java.io.IOException;

public class RetrofitOKhttp {

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.trello.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    static RetrofitTrelloSteps service = retrofit.create(RetrofitTrelloSteps.class);
    private static String key = "1d2ab2ddc3eb8d104f351c62589ed47b";
    private static String token = "46ffe16ee4e96f24a1ee49fd525413256bc10915038b322297e2dc524e701260";

    public static void main(String[] args) throws IOException {

        Call<Board> create = service.createBoard(key, token, "Retrofit15");
        Response<Board> createBoardResponse = create.execute();
        System.out.println(createBoardResponse.raw());

        String idBoard = createBoardResponse.body().getId();

//        Call<Board> getBoard = service.getBoard(idBoard, key, token);
//        Response<Board> getBoardResponse = getBoard.execute();
//        System.out.println(getBoardResponse.raw());

        Call<TrelloList> createDONElist = service.createList("DONE", idBoard, key, token);
        Response<TrelloList> createDONElistResponse = createDONElist.execute();
        System.out.println(createDONElistResponse.raw());

        Call<TrelloList> createTODOlist = service.createList("TODO", idBoard, key, token);
        Response<TrelloList> createTODOlistResponse = createTODOlist.execute();
        System.out.println(createTODOlistResponse.raw());

        String TODOid = createTODOlistResponse.body().getId();
        String DONEid = createDONElistResponse.body().getId();

        Call<Card> createCard = service.createCard("Mycard", TODOid, key, token);
        Response<Card> createCardResponse = createCard.execute();
        System.out.println(createCardResponse.raw());

        String idCard = createCardResponse.body().getId();

        Call<Card> moveCard = service.moveCard(idCard, DONEid, key, token);
        Response<Card> moveCardResponse = moveCard.execute();
        System.out.println(moveCardResponse.raw());

        Call<Board> deleteBoard = service.deleteBoard(idBoard, key, token);
        Response<Board> deleteBoardResponse = deleteBoard.execute();
        System.out.println(deleteBoardResponse.raw());
    }
}
