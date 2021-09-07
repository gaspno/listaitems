package com.shambles.ntworkenterprice.listatarefas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private EditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        editTarefa=findViewById(R.id.TextTarefa);

        tarefaAtual= (Tarefa) getIntent().getSerializableExtra("TarefaSelecionada");

        if(tarefaAtual!=null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutarefas,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.itemSalvar:
                TarefaDAO tarefaDAO=new TarefaDAO(getApplicationContext());
                Tarefa tarefa=new Tarefa();
                if(tarefaAtual!=null){

                    if(!editTarefa.getText().toString().equals("")) {

                        tarefa.setNomeTarefa(editTarefa.getText().toString());
                        tarefa.setId(tarefaAtual.getId());

                        if(tarefaDAO.atualizar(tarefa)){

                            Toast.makeText(this, "Tarefa atualizada com sucesso", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                        else{
                            Toast.makeText(this, "Erro ao atualizar tarefa", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                else if(!editTarefa.getText().toString().equals(""))
                {

                    tarefa.setNomeTarefa(editTarefa.getText().toString());

                    if(tarefaDAO.salvar(tarefa)) {
                        Toast.makeText(this, "Tarefa salva com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(this, "Erro ao salvar tarefa", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                else{
                    Toast.makeText(this, "Digite uma Tarefa", Toast.LENGTH_SHORT).show();
                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
