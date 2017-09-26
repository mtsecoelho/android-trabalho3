package com.example.matheus.trabalho3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ProdutoDAO produtoDAO;
    private ListView listview;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItems;
    private List<Produto> produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        produtoDAO = new ProdutoDAO(this);
        listItems = new ArrayList<>();
        produtos = new ArrayList<>();

        produtos = produtoDAO.list();
        for (Produto p : produtos) {
            listItems.add(p.getId() + " " + p.getNome() + p.getCategoria() + " " + p.getCreationTime());
        }

        listview = (ListView) findViewById(R.id.listView1);
        listview.setOnItemClickListener(this);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);

        listview.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCadastro(new Produto());
            }
        });
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Produto p = produtos.get(position);
        startCadastro(p);
    }

    private void startCadastro(Produto p) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), CadastroActivity.class);
        intent.putExtra("id",p.getId());
        intent.putExtra("nome",p.getNome());
        intent.putExtra("categoria",p.getCategoria());
        intent.putExtra("creationTime",p.getCreationTime());
        startActivity(intent);
    }
}
