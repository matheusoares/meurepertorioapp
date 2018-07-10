package com.meurepertorio.meurepertorio.service;

/**
 * Created by matheus_soares on 22/06/18.
 */
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.os.AsyncTask;
        import android.util.Log;

        import java.util.ArrayList;
        import java.util.List;

        import com.meurepertorio.meurepertorio.model.Musica;



public class MusicaServiceBD extends SQLiteOpenHelper {

    private static String TAG = "meuRepertorio";
    private static String NAME = "musica.sqlite";
    private static int VERSION = 1;
    private static MusicaServiceBD musicaServiceBD = null;


    /*
        Construtor
     */
    private MusicaServiceBD(Context context) {
        super(context, NAME, null, VERSION);
        getWritableDatabase();
    }

    //Singleton
    public static MusicaServiceBD getInstance(Context context){
        if(musicaServiceBD == null){
            return musicaServiceBD = new MusicaServiceBD(context);
        }

        return musicaServiceBD;
    }


    /*
        Métodos do ciclo de vida.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //cria a tabela
        String sql = "create table if not exists musica " +
                "(_id integer primary key autoincrement, " +
                "nome text, " +
                "banda text, " +
                "letra text)";
        Log.d(TAG, "Criando a tabela musica. Aguarde ...");
        sqLiteDatabase.execSQL(sql);
        Log.d(TAG, "Tabela musica criada com sucesso.");
        new Task().execute(); //popula o bd
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*
        CRUD
     */
    public List<Musica> getAll(){
        //abre a conexão com o bd
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try {
            return toList(sqLiteDatabase.rawQuery("select * from musica", null));
        }finally {
            sqLiteDatabase.close(); //libera o recurso
        }

    }

    public List<Musica> getByTipo (String tipo){
        //abre a conexão com o bd
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try{
            return toList(sqLiteDatabase.rawQuery("select * from musica order by " + tipo + " asc", null));
        }finally {
            sqLiteDatabase.close();
        }

    }

    public long save(Musica musica){

        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco

        try{
            //tupla com: chave, valor
            ContentValues values = new ContentValues();
            values.put("nome", musica.nome);
            values.put("banda", musica.banda);
            values.put("letra", musica.letra);

            //realiza a operação
            if(musica.id == null){
                //insere no banco de dados
                return db.insert("musica", null, values);
            }else{
                //altera no banco de dados
                values.put("_id", musica.id);
                return db.update("musica", values, "_id=" + musica.id, null);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close(); //libera o recurso
        }

        return 0L; //caso não realize a operação
    }

    public long delete(Musica musica){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
        try{
            return db.delete("musica", "_id=?", new String[]{String.valueOf(musica.id)});
        }
        finally {
            db.close(); //libera o recurso
        }
    }

    /*
        Utilitários
     */
    //converte de Cursor em uma List
    private List<Musica> toList(Cursor c) {
        List<Musica> musicas = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Musica musica = new Musica();

                // recupera os atributos do cursor para o musica
                musica.id = c.getLong(c.getColumnIndex("_id"));
                musica.nome = c.getString(c.getColumnIndex("nome"));
                musica.banda = c.getString(c.getColumnIndex("banda"));
                musica.letra = c.getString(c.getColumnIndex("letra"));

                musicas.add(musica);

            } while (c.moveToNext());
        }

        return musicas;
    }

    //Thread para executar a inserção de dados no bd.
    //Utilizada apenas na criação do bd
    private class Task extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            return popularTabelaMusica();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Log.d(TAG, "Tabela musica populada com sucesso.");
            }
        }

        //popula a tabela musica
        private boolean popularTabelaMusica() {
            //abre a conexão com o bd
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            try {
                //registro 1
                ContentValues values = new ContentValues();
                values.put("nome", "Parabéns a Você");
                values.put("banda", "Cantigas Populares");
                values.put("letra", "Parabéns pra você\n" +
                                    "Nesta data querida\n" +
                                    "Muitas felicidades\n" +
                                    "Muitos anos de vida\n" +
                                    "É pique, é pique\n" +
                                    "É pique, é pique, é pique, é pique\n" +
                                    "É hora, é hora\n" +
                                    "É hora, é hora, é hora\n" +
                                    "Ra-ti-bum\n" +
                                    "Parabéns!");
                sqLiteDatabase.insert("musica", null, values);

                //registro 2
                values = new ContentValues();
                values.put("nome", "Borboletinha");
                values.put("banda", "Cantigas Populares");
                values.put("letra", "Borboletinha tá na cozinha\n" +
                                    "fazendo chocolate\n" +
                                    "para a madrinha\n" +
                                    "\n" +
                                    "Poti, poti\n" +
                                    "perna de pau\n" +
                                    "olho de vidro\n" +
                                    "e nariz de pica-pau pau pau");
                sqLiteDatabase.insert("musica", null, values);


            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
            finally {
                sqLiteDatabase.close(); //libera o recurso
            }

            return true;
        }
    }//fim classe interna

}