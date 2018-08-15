package adel.co.asyst.movieadel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import adel.co.asyst.movieadel.R;
import adel.co.asyst.movieadel.model.MovieModel;
import adel.co.asyst.movieadel.utility.Constant;
import adel.co.asyst.movieadel.utility.DateUtils;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<MovieModel> mListMovie;
    onItemClickListener mListener;

    public MovieAdapter(Context context, ArrayList<MovieModel> listMovie) {
        this.mContext = context;
        this.mListMovie = listMovie;
    }


    public MovieAdapter(Context context, ArrayList<MovieModel> listMovie, onItemClickListener onItemClickListener) {
        this.mContext = context;
        this.mListMovie = listMovie;
        this.mListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        return new MovieAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MovieModel movieModel = mListMovie.get(position);

        holder.titletv.setText(movieModel.getTitle());

        holder.overviewtv.setText(movieModel.getOverview());
        if (!movieModel.getRelease_date().isEmpty()) {
            holder.releasedatetv.setText(DateUtils.formatDate("yyyy-MM-dd", "dd MMMM yyyy", movieModel.getRelease_date()));
        } else {
            holder.releasedatetv.setText("AAAA");
        }

        holder.readtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickListener(movieModel);
            }
        });
        RequestOptions requestOptions = new RequestOptions().placeholder(mContext.getResources().getDrawable(R.drawable.ic_cloud)).error(R.drawable.ic_cloud);
        Glide.with(mContext).load(Constant.BASE_IMAGE + movieModel.getPoster_path()).apply(requestOptions).into(holder.movieiv);


    }

    @Override
    public int getItemCount() {
        return mListMovie.size();
    }

    public interface onItemClickListener {
        void onItemClickListener(MovieModel movieModel);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView movieiv, desciv;
        TextView titletv, overviewtv, releasedatetv, readtv;
        CardView movieCardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            movieiv = itemView.findViewById(R.id.image_view_movie);
            desciv = itemView.findViewById(R.id.image_view_backdrop);
            titletv = itemView.findViewById(R.id.text_view_movie);
            overviewtv = itemView.findViewById(R.id.text_view_desc);
            releasedatetv = itemView.findViewById(R.id.text_view_movie_date);
            readtv = itemView.findViewById(R.id.text_view_read_more);
            movieCardView = itemView.findViewById(R.id.cardView);

        }
    }


}
