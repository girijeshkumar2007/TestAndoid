package Model;


import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by girijeshkumar on 30/06/16.
 */
public interface StackOverflowAPI {

    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<StackOverflowQuestions> loadQuestions(@Query("tagged") String tags);
}
