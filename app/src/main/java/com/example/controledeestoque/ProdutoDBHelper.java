package com.example.controledeestoque;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "estoque.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PRODUTOS = "produtos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUANTIDADE = "quantidade";
    public static final String COLUMN_DESCRICAO = "descricao";
    public static final String COLUMN_VALOR = "valor";

    public ProdutoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PRODUTOS_TABLE = "CREATE TABLE " + TABLE_PRODUTOS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_QUANTIDADE + " INTEGER NOT NULL, "
                + COLUMN_DESCRICAO + " TEXT, "
                + COLUMN_VALOR + " REAL NOT NULL);";

        db.execSQL(SQL_CREATE_PRODUTOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se precisar atualizar o banco no futuro
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTOS);
        onCreate(db);
    }

    public void inserirProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_QUANTIDADE, produto.getQuantidade());
        values.put(COLUMN_DESCRICAO, produto.getDescricao());
        values.put(COLUMN_VALOR, produto.getValorUnitario());

        db.insert(TABLE_PRODUTOS, null, values);
        db.close();
    }

    public List<Produto> listarProdutos() {
        List<Produto> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUTOS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                int qtd = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTIDADE));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRICAO));
                double valor = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VALOR));

                lista.add(new Produto(id, qtd, desc != null ? desc : "", valor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

    public void excluirProduto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUTOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public double calcularTotalEstoque() {
        double total = 0.0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT quantidade, valor FROM " + TABLE_PRODUTOS, null);

        if (cursor.moveToFirst()) {
            do {
                int qtd = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTIDADE));
                double valor = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VALOR));
                total += qtd * valor;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return total;
    }


}
