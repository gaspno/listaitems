package com.shambles.ntworkenterprice.listatarefas;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView listaView;
    private AdapterLista adapterLista;
    private List<Tarefa> tarefaList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaView=findViewById(R.id.listaView);




        listaView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), listaView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Tarefa selection=tarefaList.get(position);

                Intent intent=new Intent(MainActivity.this,AdicionarTarefaActivity.class);
                intent.putExtra("TarefaSelecionada",selection);

                startActivity(intent);


            }

            @Override
            public void onLongItemClick(View view, int position) {

                final Tarefa selection=tarefaList.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirmar exclusão?");
                builder.setMessage("Deseja deletar a tarefa :"+selection.getNomeTarefa());
                builder.setIcon(R.drawable.ic_delete_forever_black_24dp);
                builder.setPositiveButtonIcon(getDrawable(R.drawable.ic_check_black_24dp));
                builder.setNegativeButtonIcon(getDrawable(R.drawable.ic_cancel_black_24dp));
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        TarefaDAO tarefaDAO=new TarefaDAO(getApplicationContext());
                        if( tarefaDAO.deletar(selection)){

                            Toast.makeText(getApplicationContext(), "Tarefa deletada com sucesso", Toast.LENGTH_SHORT).show();
                            configListaView();


                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Erro ao deletar tarefa", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create();
                builder.show();



            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        configListaView();

    }

    public void configListaView(){

        TarefaDAO tarefaDAO=new TarefaDAO(getApplicationContext()) ;
        tarefaList=tarefaDAO.listar();


        adapterLista=new AdapterLista(tarefaList);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        listaView.setLayoutManager(layoutManager);
        listaView.setHasFixedSize(true);
        listaView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayout.VERTICAL));
        listaView.setAdapter(adapterLista);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
