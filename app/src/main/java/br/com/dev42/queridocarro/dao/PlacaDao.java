package br.com.dev42.queridocarro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.dev42.queridocarro.model.Placa;

/**
 * Created by sossai on 27/04/17.
 */

public class PlacaDao extends SQLiteOpenHelper {
    private static final int VERSAO = 1;
    private static final String TABELA = "Placas";
    private static final String DATABASE = "QueridoCarro";

    public PlacaDao(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "placa STRING,"
                + "codigoacesso STRING,"
                + "quantidadeos INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void inserir(Placa placa){
        ContentValues values = new ContentValues();

        values.put("placa", placa.getPlaca());
        values.put("codigoacesso", placa.getCodigoAcesso());
        values.put("quantidadeos", placa.getQuantidadeOs());
        getWritableDatabase().insert(TABELA,null,values);
    }

    public void apagar(Placa placa){
        String args[] = {placa.getId().toString()};
        getWritableDatabase().delete(TABELA,"id=?",args);
    }

    public void alterar(Placa placa){
        String args[] = {placa.getId().toString()};
        ContentValues values = new ContentValues();

        values.put("id", placa.getId());
        values.put("placa", placa.getPlaca());
        values.put("codigoacesso", placa.getCodigoAcesso());
        values.put("quantidadeos", placa.getQuantidadeOs());

        getWritableDatabase().update(TABELA,values,"id=?",args);
    }

    public List<Placa> listar(){
        List<Placa> placas = new ArrayList<>();

        String sql = "SELECT * FROM " + TABELA + " ORDER BY ID DESC;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);

        while (c.moveToNext()){
            Placa placa = new Placa();
            placa.setId(c.getInt(c.getColumnIndex("id")));
            placa.setPlaca(c.getString(c.getColumnIndex("placa")));
            placa.setCodigoAcesso(c.getString(c.getColumnIndex("codigoacesso")));
            placa.setQuantidadeOs(c.getInt(c.getColumnIndex("quantidadeos")));

            placas.add(placa);
        }
        c.close();

        return placas;
    }

    public Integer quantidadePlacasCadastradas(){
        String sql = "SELECT COUNT(*) FROM " + TABELA + ";";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cr = db.rawQuery(sql, null);

        cr.moveToFirst();
        Integer quantidade = cr.getInt(0);
        cr.close();

        return quantidade;
    }

    public Integer jaCadastrada(String placa){
        String sql = "SELECT id FROM " + TABELA + " WHERE placa = " + "\"" + placa +  "\"" + ";";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cr = db.rawQuery(sql,null);
        Integer id = 0;
        while (cr.moveToNext()){
            id = cr.getInt(cr.getColumnIndex("id"));
        }
        cr.close();
        return id;
    }
}
