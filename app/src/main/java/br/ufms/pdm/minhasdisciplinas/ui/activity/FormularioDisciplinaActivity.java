package br.ufms.pdm.minhasdisciplinas.ui.activity;

import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.COLECAO_FIRESTORE_ALUNO;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.COLECAO_FIRESTORE_DISCIPLINAS;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_ANO_SEMESTRE;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_CARGA_HORARIA;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_EMAIL;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_NOME;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_PERIODO_FIM;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_PERIODO_INICIO;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_PROFESSOR;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_SITUACAO;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_TURMA;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.ufms.pdm.minhasdisciplinas.R;
import br.ufms.pdm.minhasdisciplinas.databinding.ActivityFormularioDisciplinaBinding;
import br.ufms.pdm.minhasdisciplinas.model.Disciplina;

public class FormularioDisciplinaActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_EDITA_DISCIPLINA = "Editando disciplina";
    private static final String TITULO_APPBAR_NOVA_DISCIPLINA = "Criando disciplina";
    private ActivityFormularioDisciplinaBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText campoDisciplina, campoProfessor, campoTurma, campoAnoSemestre, campoCargaHoraria, campoPeriodoInicio, campoPeriodoFim;
    private String nomeDisciplina, professor, turma, strCargaHoraria, anoSemestre, periodoInicio, periodoFim, situacao, docRefAluno;
    private int cargaHoraria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_disciplina);
        setTitle(TITULO_APPBAR_NOVA_DISCIPLINA);
        binding = ActivityFormularioDisciplinaBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        habilitaBotaoVoltar();
        inicializacaoDosCampos();

        situacao = binding.activityFormularioDisciplinaRadioBtnMatriculado.getText().toString();

        binding.activityFormularioDisciplinaRadioBtnMatriculado.setOnClickListener(view ->
                situacao = binding.activityFormularioDisciplinaRadioBtnMatriculado.getText().toString());

        binding.activityFormularioDisciplinaRadioBtnAprovado.setOnClickListener(view ->
            situacao = binding.activityFormularioDisciplinaRadioBtnAprovado.getText().toString());

        binding.activityFormularioDisciplinaRadioBtnReprovado.setOnClickListener(view ->
            situacao = binding.activityFormularioDisciplinaRadioBtnReprovado.getText().toString());
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
            preencheVariaveis();
            verificaCampos();
        } else if(itemId == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void cadastraDisciplina() {
        Map<String, Object> disciplina = new HashMap<>();
        disciplina.put(KEY_NOME, nomeDisciplina);
        disciplina.put(KEY_PROFESSOR, professor);
        disciplina.put(KEY_TURMA, turma);
        disciplina.put(KEY_ANO_SEMESTRE, anoSemestre);
        disciplina.put(KEY_CARGA_HORARIA, cargaHoraria);
        disciplina.put(KEY_PERIODO_INICIO, periodoInicio);
        disciplina.put(KEY_PERIODO_FIM, periodoFim);
        disciplina.put(KEY_SITUACAO, situacao);

        buscaAluno();

        db
                .collection(COLECAO_FIRESTORE_ALUNO).document(docRefAluno)
                .collection(COLECAO_FIRESTORE_DISCIPLINAS)
                .add(disciplina)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getBaseContext(), "Disciplina cadastrada!", Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(getBaseContext(), "Não foi possível cadstrar a disciplina!", Toast.LENGTH_LONG).show());
    }

    private void buscaAluno() {
        String email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        db.collection(COLECAO_FIRESTORE_ALUNO)
                .whereEqualTo(KEY_EMAIL, email)
                .get()
                .addOnCompleteListener(task -> {
                    if(!task.getResult().isEmpty()){
                        for (QueryDocumentSnapshot document : task.getResult()){
                            docRefAluno = document.getId();
                        }
                    }
                });
    }

    private void verificaCampos() {
        if(nomeDisciplina.isEmpty()         ||
                professor.isEmpty()         ||
                turma.isEmpty()             ||
                anoSemestre.isEmpty()       ||
                strCargaHoraria.isEmpty()   ||
                periodoInicio.isEmpty()     ||
                periodoFim.isEmpty()
        ) {
            Toast.makeText(getBaseContext(), "Preeencha todos os campos!", Toast.LENGTH_LONG).show();
        } else
            cargaHoraria = Integer.parseInt(strCargaHoraria);
            cadastraDisciplina();
    }

    private void preencheVariaveis() {
        nomeDisciplina = campoDisciplina.getText().toString();
        professor = campoProfessor.getText().toString();
        turma = campoTurma.getText().toString();
        anoSemestre = campoAnoSemestre.getText().toString();
        strCargaHoraria = campoCargaHoraria.getText().toString();
        periodoInicio = campoPeriodoInicio.getText().toString();
        periodoFim = campoPeriodoFim.getText().toString();
    }

    private void inicializacaoDosCampos() {
        campoDisciplina = binding.activityFormularioDisciplinaEdtxtDisciplina;
        campoProfessor = binding.activityFormularioDisciplinaEdtxtProfessor;
        campoTurma = binding.activityFormularioDisciplinaEdtxtTurma;
        campoAnoSemestre = binding.activityFormularioDisciplinaEdtxtAnoSemestre;
        campoCargaHoraria = binding.activityFormularioDisciplinaEdtxtCargaHoraria;
        campoPeriodoInicio = binding.activityFormularioDisciplinaEdtxtPeriodoInicio;
        campoPeriodoFim = binding.activityFormularioDisciplinaEdtxtPeriodoFim;
    }
}