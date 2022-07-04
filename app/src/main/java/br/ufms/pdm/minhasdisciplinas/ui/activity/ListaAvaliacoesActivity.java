package br.ufms.pdm.minhasdisciplinas.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import br.ufms.pdm.minhasdisciplinas.R;
import br.ufms.pdm.minhasdisciplinas.databinding.ActivityListaAvaliacoesBinding;

public class ListaAvaliacoesActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Detalhes da disciplina";
    private ActivityListaAvaliacoesBinding binding;
    private TextView nomeDisciplina, professor, turma, anoSemestre, cargaHoraria, periodo, situacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListaAvaliacoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(TITULO_APPBAR);
        habilitaBotaoVoltar();
        inicializacaoDosCampos();
    }

    private void habilitaBotaoVoltar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void inicializacaoDosCampos() {
        nomeDisciplina = binding.activityListaAvaliacoesTxtDisciplina;
        professor = binding.activityListaAvaliacoesTxtProfessor;
        turma = binding.activityListaAvaliacoesTxtTurma;
        anoSemestre = binding.activityListaAvaliacoesTxtAnoSemestre;
        cargaHoraria = binding.activityListaAvaliacoesTxtCargaHoraria;
        periodo = binding.activityListaAvaliacoesTxtPeriodo;
        situacao = binding.activityListaAvaliacoesTxtSituacao;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lista_avaliacoes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.menu_editar){
            // TODO: Entrar no formulário de disciplina no modo edita
        } else if(itemId == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}