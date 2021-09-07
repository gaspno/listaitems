package com.shambles.ntworkenterprice.listatarefas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;



public class TarefaDAO implements iTarefaDAO{

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    public TarefaDAO(Context context) {
        DbHelper dbHelper=new DbHelper(context);
        escrever=dbHelper.getWritableDatabase();
        ler=dbHelper.getReadableDatabase();



    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv=new ContentValues();
        cv.put("nome",tarefa.getNomeTarefa());

        try {
            escrever.insert(DbHelper.TABELA_TAREFAS,null,cv);
            Log.e("Info","Tarefa salva com sucesso");

        }catch (Exception e){

            Log.e("Info","Erro ao salvar a Tarefa"+e.getMessage());
            return false;

        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {


        ContentValues cv=new ContentValues();
        cv.put("nome",tarefa.getNomeTarefa());

        try {

            String condition[]={tarefa.getId().toString()};
            escrever.update(DbHelper.TABELA_TAREFAS,cv,"ID=?",condition);
            Log.e("Info","Tarefa atualizada com sucesso");

        }catch (Exception e){

            Log.e("Info","Erro ao atualizar a Tarefa"+e.getMessage());
            return false;

        }




        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {



        try {

            String condition[]={tarefa.getId().toString()};
            escrever.delete(DbHelper.TABELA_TAREFAS,"ID=?",condition);
            Log.e("Info","Tarefa deletada com sucesso");

        }catch (Exception e){

            Log.e("Info","Erro ao deletar a Tarefa"+e.getMessage());
            return false;

        }




        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa>tarefas=new ArrayList<>();

        String sql="SELECT * FROM "+DbHelper.TABELA_TAREFAS+" ;";
        Cursor cursor=ler.rawQuery(sql,null);

        while(cursor.moveToNext()){

            Long id=cursor.getLong(cursor.getColumnIndex("ID"));
            String nome=cursor.getString(cursor.getColumnIndex("NOME"));


            Tarefa tarefa=new Tarefa();
            tarefa.setId(id);
            tarefa.setNomeTarefa(nome);

            tarefas.add(tarefa);

        }

        return  tarefas;
    }
}
