package com.example.matheus.trabalho3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CadastroActivity extends AppCompatActivity {
    private Produto p;
    private String categoria;
    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        produtoDAO = new ProdutoDAO(this);
        p = new Produto();

        final TextView tvNome = (TextView) findViewById(R.id.editText);
        final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);

        Intent it = getIntent();
        Bundle bundle = it.getExtras();

        try {

            p.setId(Integer.parseInt(bundle.get("id").toString()));
            p.setNome(bundle.get("nome").toString());
            p.setCategoria(bundle.get("categoria").toString());
            p.setCreationTime(bundle.get("creationTime").toString());

            categoria = p.getCategoria();

            switch (categoria) {
                case "Teste 0":
                    rg.check(R.id.radioButton);
                    break;
                case "Teste 1":
                    rg.check(R.id.radioButton2);
                    break;
                case "Teste 2":
                    rg.check(R.id.radioButton3);
                    break;
                case "Teste 3":
                    rg.check(R.id.radioButton4);
                    break;
            }

        } catch (Exception e) {

            Log.i("Nada", "Novo Produto");

        }

        tvNome.setText(p.getNome());

        Button bt = (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.setNome(tvNome.getText().toString());

                if(rg.getCheckedRadioButtonId()!=-1){
                    int id= rg.getCheckedRadioButtonId();
                    View radioButton = rg.findViewById(id);
                    int radioId = rg.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) rg.getChildAt(radioId);
                    p.setCategoria((String) btn.getText());
                }
                if (p.getId() > 0) {
                    produtoDAO.update(p);
                } else {
                    produtoDAO.create(p);
                }
            }
        });

        Button bt1 = (Button) findViewById(R.id.button3);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produtoDAO.delete(p.getId());
            }
        });

    }
}
