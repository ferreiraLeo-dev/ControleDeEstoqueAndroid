package com.example.controledeestoque;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ListaProdutosActivity extends AppCompatActivity {

    private TextView textTotal;
    private ListView listView;
    private ProdutoDBHelper dbHelper;
    private List<Produto> listaProdutos;
    private ArrayAdapter<String> adapter;
    private String[] produtosFormatados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        listView = findViewById(R.id.listViewProdutos);
        textTotal = findViewById(R.id.textTotal);
        dbHelper = new ProdutoDBHelper(this);

        atualizarLista();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoSelecionado = listaProdutos.get(position);

                new AlertDialog.Builder(ListaProdutosActivity.this)
                        .setTitle("Excluir")
                        .setMessage("Deseja excluir este produto?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            dbHelper.excluirProduto(produtoSelecionado.getId());
                            atualizarLista();
                            Toast.makeText(ListaProdutosActivity.this, "Produto excluído", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();

                return true;
            }
        });

    }

    private void atualizarLista() {
        listaProdutos = dbHelper.listarProdutos();
        produtosFormatados = new String[listaProdutos.size()];

        for (int i = 0; i < listaProdutos.size(); i++) {
            Produto p = listaProdutos.get(i);
            produtosFormatados[i] = "Qtd: " + p.getQuantidade()
                    + " | Valor: R$ " + p.getValorUnitario()
                    + (p.getDescricao().isEmpty() ? "" : " | " + p.getDescricao());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produtosFormatados);
        listView.setAdapter
                (adapter);
        double total = dbHelper.calcularTotalEstoque();
        textTotal.setText(String.format("Total em estoque: R$ %.2f", total));

    }
}