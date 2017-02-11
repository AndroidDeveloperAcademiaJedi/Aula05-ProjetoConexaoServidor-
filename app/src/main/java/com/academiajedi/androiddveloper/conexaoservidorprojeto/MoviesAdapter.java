package com.academiajedi.androiddveloper.conexaoservidorprojeto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alexsoaresdesiqueira on 11/02/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.PersonViewHolder> {

    private List<MoviesNow> moviesNowList;
    private RecycleViewClickHack recycleViewClickHack;

    public MoviesAdapter(List<MoviesNow> pessoaList){
        this.moviesNowList = pessoaList;
    }


    public class PersonViewHolder extends RecyclerView.ViewHolder {

        ImageView imvMovie;
        TextView tvTitulo;
        TextView tvTituloOriginal;
        TextView tvdatalancamento;

        PersonViewHolder(View itemView) {
            super(itemView);

            imvMovie = (ImageView) itemView.findViewById(R.id.imvMovie);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
            tvTituloOriginal = (TextView) itemView.findViewById(R.id.tvTituloOriginal);
            tvdatalancamento = (TextView) itemView.findViewById(R.id.tvdatalancamento);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recycleViewClickHack != null){
                        recycleViewClickHack.onClickListener(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setRecycleViewClick(RecycleViewClickHack recycleViewClickHack){
        this.recycleViewClickHack = recycleViewClickHack;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filme, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.tvTitulo.setText(moviesNowList.get(position).getTitle());
        holder.tvTituloOriginal.setText(moviesNowList.get(position).getOriginal_title());
        holder.tvdatalancamento.setText(moviesNowList.get(position).getRelease_date());
        holder.imvMovie.setImageBitmap(moviesNowList.get(position).getImageMovie());
    }

    @Override
    public int getItemCount() {
        return moviesNowList.size();
    }

}