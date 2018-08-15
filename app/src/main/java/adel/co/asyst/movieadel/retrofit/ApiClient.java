package adel.co.asyst.movieadel.retrofit;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit = null;
    public static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    public static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit newInstance(Context context) {
        if (retrofit == null) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);

            retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        }

        return retrofit;
    }
}
