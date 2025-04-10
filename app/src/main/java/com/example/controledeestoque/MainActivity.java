package com.example.controledeestoque;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private EditText editQuantidade, editDescricao, editValor;
    private ProdutoDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editQuantidade = findViewById(R.id.editQuantidade);
        editDescricao = findViewById(R.id.editDescricao);
        editValor = findViewById(R.id.editValor);
        Button btnSalvar = findViewById(R.id.btnSalvar);

        dbHelper = new ProdutoDBHelper(this);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarProduto();
            }
        });

        Button btnVerLista = findViewById(R.id.btnVerLista);
        btnVerLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListaProdutosActivity.class);
                startActivity(intent);
            }
        });

    }

    private void salvarProduto() {
        String quantidadeStr = editQuantidade.getText().toString().trim();
        String descricaoStr = editDescricao.getText().toString().trim();
        String valorStr = editValor.getText().toString().trim();

        if (quantidadeStr.isEmpty() || valorStr.isEmpty()) {
            Toast.makeText(this, "Preencha quantidade e valor!", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidade = Integer.parseInt(quantidadeStr);
        double valor = Double.parseDouble(valorStr);

        Produto produto = new Produto(quantidade, descricaoStr, valor);
        dbHelper.inserirProduto(produto);

        Toast.makeText(this, "Produto salvo com sucesso!", Toast.LENGTH_SHORT).show();

        editQuantidade.setText("");
        editDescricao.setText("");
        editValor.setText("");
    }
}
