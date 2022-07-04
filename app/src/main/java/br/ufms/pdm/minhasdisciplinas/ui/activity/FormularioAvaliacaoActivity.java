package br.ufms.pdm.minhasdisciplinas.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.ufms.pdm.minhasdisciplinas.R;

public class FormularioAvaliacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_avaliacao);
        habilitaBotaoVoltar();
    }

    private void habilitaBotaoVoltar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.menu_salvar){
            // TODO: Salvar avaliacao (alterações/nova avaliacao)
        } else if(itemId == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}