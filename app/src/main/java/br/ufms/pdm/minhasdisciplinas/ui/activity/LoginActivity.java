package br.ufms.pdm.minhasdisciplinas.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.ufms.pdm.minhasdisciplinas.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private EditText campoEmail, campoSenha;
    private String email, senha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        verificaUsuario();

        binding.activityLoginBtnLogin.setOnClickListener(view -> {
            inicializacaoDosCampos();
            preencheVariaveis();
            verificaCampos();
        });

        binding.activityLoginBtnCadastrar.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
            finish();
        });
    }

    private void verificaUsuario() {
        FirebaseUser usuario = mAuth.getCurrentUser();
        if (usuario != null)
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void verificaCampos() {
        if (email.isEmpty()) {
            campoEmail.setError("Digite um e-mail");
        } else if (senha.isEmpty()) {
            campoSenha.setError("Digite uma senha");
        } else {
            fazLogin();
        }
    }

    private void fazLogin() {
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Usuário e/ou senha incorretos!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void preencheVariaveis() {
        email = campoEmail.getText().toString();
        senha = campoSenha.getText().toString();
    }

    private void inicializacaoDosCampos() {
        campoEmail = binding.activityLoginEdtxtEmail;
        campoSenha = binding.activityLoginEdtxtSenha;
    }
}