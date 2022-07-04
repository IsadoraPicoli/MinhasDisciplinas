package br.ufms.pdm.minhasdisciplinas.ui.tab;

import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.COLECAO_FIRESTORE_ALUNO;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_CURSO;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_EMAIL;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_NOME;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

import br.ufms.pdm.minhasdisciplinas.R;
import br.ufms.pdm.minhasdisciplinas.databinding.FragmentPerfilTabBinding;
import br.ufms.pdm.minhasdisciplinas.ui.activity.LoginActivity;

public class PerfilTabFragment extends Fragment {

    private FragmentPerfilTabBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView campoNome, campoCurso, campoEmail;

    public PerfilTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPerfilTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializacaoDosCampos();
        buscaInformacoesAluno();
    }

    private void buscaInformacoesAluno() {
        String email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        db.collection(COLECAO_FIRESTORE_ALUNO)
                .whereEqualTo(KEY_EMAIL, email)
                .get()
                .addOnCompleteListener(task -> {
                    if(!task.getResult().isEmpty()){
                        for (QueryDocumentSnapshot document : task.getResult()){
                            campoNome.setText(Objects.requireNonNull(document.get(KEY_NOME)).toString());
                            campoCurso.setText(Objects.requireNonNull(document.get(KEY_CURSO)).toString());
                            campoEmail.setText(Objects.requireNonNull(document.get(KEY_EMAIL)).toString());
                        }
                    }
                });
    }

    private void inicializacaoDosCampos() {
        campoNome = binding.fragmentPerfilTabNome;
        campoCurso = binding.fragmentPerfilTabCurso;
        campoEmail = binding.fragmentPerfilTabEmail;
    }

    public static PerfilTabFragment newInstance() {
        return new PerfilTabFragment();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_perfil_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.menu_sair){
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            onDestroyView();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}