package com.meurepertorio.meurepertorio.adapter;

/**
 * Created by matheus_soares on 22/06/18.
 */
        import android.content.Context;
        import android.net.Uri;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.List;

        import com.meurepertorio.meurepertorio.R;
        import com.meurepertorio.meurepertorio.model.Musica;


public class MusicasAdapter extends RecyclerView.Adapter<MusicasAdapter.MusicasViewHolder> {
    protected static final String TAG = "MusicasAdapter";
    private final List<Musica> musicas;
    private final Context context;

    private MusicaOnClickListener musicaOnClickListener;

    public MusicasAdapter(Context context, List<Musica> musicas, MusicaOnClickListener musicaOnClickListener) {
        this.context = context;
        this.musicas = musicas;
        this.musicaOnClickListener = musicaOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.musicas != null ? this.musicas.size() : 0;
    }

    @Override
    public MusicasViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_musicas, viewGroup, false);


        MusicasViewHolder holder = new MusicasViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MusicasViewHolder holder, final int position) {

        Musica c = musicas.get(position);
        Log.d(TAG, "Musica no Adapter da RecyclerView: " + c.toString());

        Log.d(TAG, c.toString());

        holder.tNome.setText(c.nome);
        //holder.progress.setVisibility(View.VISIBLE);

        if (musicaOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    musicaOnClickListener.onClickMusica(holder.itemView, position);
                }
            });
        }

       // holder.progress.setVisibility(View.INVISIBLE);
    }

    public interface MusicaOnClickListener {
        public void onClickMusica(View view, int idx);
    }

    // ViewHolder com as views
    public static class MusicasViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        //public ProgressBar progress;

        public MusicasViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.tvNome_card_view_adaptermusica);
        }
    }
}