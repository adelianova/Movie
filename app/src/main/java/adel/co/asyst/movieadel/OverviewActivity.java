package adel.co.asyst.movieadel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import adel.co.asyst.movieadel.model.MovieModel;
import adel.co.asyst.movieadel.utility.Constant;
import adel.co.asyst.movieadel.utility.DateUtils;

public class OverviewActivity extends AppCompatActivity {

    TextView titletv, datetv, desctv;
    ImageView desciv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        desciv = findViewById(R.id.image_view_backdrop);
        titletv = findViewById(R.id.text_view_movie_title);
        datetv = findViewById(R.id.text_view_movie_release_date);
        desctv = findViewById(R.id.text_view_movie_overview);

        if (getIntent().getExtras() != null) {
            MovieModel movieModel = getIntent().getExtras().getParcelable("movie");
            Glide.with(this).load(Constant.BACKDROP_PATH + movieModel.getBackdrop_path()).into(desciv);
            titletv.setText(movieModel.getTitle());
            datetv.setText(DateUtils.formatDate("yyyy-MM-dd", "dd MMMM yyyy", movieModel.getRelease_date()));
            desctv.setText(movieModel.getOverview());

        }
    }
}
