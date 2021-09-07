package com.shambles.ntworkenterprice.listatarefas;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION=1;
    public static String NOME_DB="DB_TAREFAS";
    public static String TABELA_TAREFAS="tarefas";


    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " +
                TABELA_TAREFAS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NOME VARCHAR(50) NOT NULL);";
        try {
            db.execSQL(sql);
            Log.i("info db","Sucesso ao criar a tabela") ;
        } catch (Exception e) {
            Log.i("info db","Erro ao criar tabela "+e.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+TABELA_TAREFAS+" ;";
        try {
            db.execSQL(sql);
            onCreate(db);
            Log.i("info db","Sucesso ao atualizar o app") ;
        } catch (Exception e) {
            Log.i("info db","Erro ao atualizar o app "+e.getMessage());
        }
    }
    }

