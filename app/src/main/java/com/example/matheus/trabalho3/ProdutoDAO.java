package com.example.matheus.trabalho3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matheus on 25/09/17.
 */

public class ProdutoDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Produtos.db";
    public static final int DATABASE_VERSION = 1;

    public ProdutoDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer();
        sql.append("create table produtos (");
        sql.append("id integer primary key autoincrement,");
        sql.append("nome text,");
        sql.append("categoria text,");
        sql.append("creation_time integer default current_timestamp )");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists produtos");
        onCreate(db);
    }

    public void create(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("categoria", produto.getCategoria());
        contentValues.put("nome", produto.getNome());
        contentValues.put("creation_time", produto.getCreationTime());
        long id = db.insert("produtos", null, contentValues);
        Log.i("SQLite", "create id = " + id);
    }


    public void update(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("categoria", produto.getCategoria());
        contentValues.put("nome", produto.getNome());
        String[] fieldValues = new String[1];
        fieldValues[0] = Integer.toString(produto.getId());
        db.update("produtos", contentValues, " id = ? ", fieldValues);
    }

    public void delete(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("produtos", " id = ? ", new String[] { Integer.toString(id) });
    }

    public void deleteall() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("produtos", " id > ? ", new String[] { Integer.toString(0) });
    }

    public Produto findById(Integer id) {
        Produto produto = new Produto();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select id, nome, categoria, creation_time from produtos where id='"+id+"'", null);
        if (result != null && result.getCount() > 0) {
            produto.setId(result.getInt(0));
            produto.setNome(result.getString(1));
            produto.setCategoria(result.getString(2));
            produto.setCreationTime(result.getString(3));
        }

        return produto;
    }

    public List<Produto> list() {
        List<Produto> produtos = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("select id, nome, categoria, creation_time from produtos order by id", null);
        if (result != null && result.getCount() > 0) {
            produtos = new ArrayList<>();
            result.moveToFirst();
            while (result.isAfterLast() == false) {
                Produto produto = new Produto();
                produto.setId(result.getInt(0));
                produto.setNome(result.getString(1));
                produto.setCategoria(result.getString(2));
                produto.setCreationTime(result.getString(3));
                produtos.add(produto);
                result.moveToNext();
            }
        }
        return produtos;
    }
}
