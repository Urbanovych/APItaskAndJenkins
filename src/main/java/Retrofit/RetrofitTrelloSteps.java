package Retrofit;

import retrofit.Call;
import retrofit.http.*;

import java.util.List;

public interface RetrofitTrelloSteps {

    @POST("/1/boards/")
    Call<Board> createBoard(@Query("key") String key, @Query("token") String token, @Query("name") String name);

//    @GET("/1/boards/{id}/memberships")
//    Call<Board> getBoard(@Path("id") String id, @Query("key") String key, @Query("token") String token);

    @POST("/1/lists")
    Call<TrelloList> createList(@Query("name") String name, @Query("idBoard") String idBoard, @Query("key") String key, @Query("token") String token);

    @POST("/1/cards")
    Call<Card> createCard(@Query("name") String name, @Query("idList") String idList, @Query("key") String key, @Query("token") String token);

    @PUT("/1/cards/{idCard}")
    Call<Card> moveCard(@Path("idCard") String idCard, @Query("idList") String idList, @Query("key") String key, @Query("token") String token);

    @DELETE("/1/boards/{id}/")
    Call<Board> deleteBoard(@Path("id") String id, @Query("key") String key, @Query("token") String token);
}
