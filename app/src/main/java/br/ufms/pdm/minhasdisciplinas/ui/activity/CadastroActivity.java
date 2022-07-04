package br.ufms.pdm.minhasdisciplinas.ui.activity;

import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.COLECAO_FIRESTORE_ALUNO;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_CURSO;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_EMAIL;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_FOTO;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_NOME;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.ufms.pdm.minhasdisciplinas.databinding.ActivityCadastroBinding;

public class CadastroActivity extends AppCompatActivity {
    private ActivityCadastroBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private final Context context = getBaseContext();
    private EditText campoNome, campoCurso, campoEmail, campoSenha, campoConfirmaSenha;
    private String nome, curso, email, senha, confirmaSenha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        binding.activityCadastroBtnCadastrar.setOnClickListener(view -> {
            inicializacaoDosCampos();
            preencheVariaveis();
            verificaCampos();
        });

        binding.activityCadastroBtnLogin.setOnClickListener(view -> {
            startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
            finish();
        });

    }

    private void verificaCampos() {
        if (nome.isEmpty()) {
            campoNome.setError("Digite seu nome");
        } else if (curso.isEmpty()) {
            campoCurso.setError("Digite um curso");
        } else if (email.isEmpty()) {
            campoEmail.setError("Digite um e-mail");
        } else if (senha.isEmpty()) {
            campoSenha.setError("Digite uma senha");
        } else if (confirmaSenha.isEmpty()) {
            campoConfirmaSenha.setError("Digite novamente sua senha");
        } else {
            verificaSenha();
        }
    }

    private void verificaSenha() {
        if(senha.length() >= 6){
            if(senha.contentEquals(confirmaSenha)){
                cadastraAluno();
            }
            else {
                campoSenha.setError("Digite senhas iguais");
                campoConfirmaSenha.setError("Digite senhas iguais");
            }
        } else {
            campoSenha.setError("Digite uma senha com 6 caracteres, no mínimo.");
        }
    }

    private void cadastraAluno() {
        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()) {
                adicionaAlunoBD();
                startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(context, "Erro ao realizar o cadastro!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void adicionaAlunoBD() {
        Map<String, Object> aluno = new HashMap<>();
        aluno.put(KEY_NOME, nome);
        aluno.put(KEY_CURSO, curso);
        aluno.put(KEY_EMAIL, email);
        aluno.put(KEY_FOTO, null);

        db.collection(COLECAO_FIRESTORE_ALUNO).add(aluno);
    }

    private void preencheVariaveis() {
        nome = campoNome.getText().toString();
        curso = campoCurso.getText().toString();
        email = campoEmail.getText().toString();
        senha = campoSenha.getText().toString();
        confirmaSenha = campoConfirmaSenha.getText().toString();
    }

    private void inicializacaoDosCampos() {
        campoNome = binding.activityCadastroEdtxtNome;
        campoCurso = binding.activityCadastroEdtxtCurso;
        campoEmail = binding.activityCadastroEdtxtEmail;
        campoSenha = binding.activityCadastroEdtxtSenha;
        campoConfirmaSenha = binding.activityCadastroEdtxtConfirmaSenha;
    }
}