package br.ufms.pdm.minhasdisciplinas.ui.tab;

import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.COLECAO_FIRESTORE_ALUNO;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.COLECAO_FIRESTORE_DISCIPLINAS;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_CURSO;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_EMAIL;
import static br.ufms.pdm.minhasdisciplinas.ui.ConstantesActivities.KEY_NOME;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.ufms.pdm.minhasdisciplinas.R;
import br.ufms.pdm.minhasdisciplinas.databinding.FragmentDisciplinasTabBinding;
import br.ufms.pdm.minhasdisciplinas.model.Disciplina;
import br.ufms.pdm.minhasdisciplinas.ui.activity.FormularioDisciplinaActivity;
import br.ufms.pdm.minhasdisciplinas.ui.activity.ListaAvaliacoesActivity;
import br.ufms.pdm.minhasdisciplinas.ui.recycler.adapter.ListaDisciplinasAdapter;

public class DisciplinasTabFragment extends Fragment {

    private Context context;
    private FragmentDisciplinasTabBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String docRefAluno;
    private Spinner spinner;
    private TextView txt_curso;
    private RecyclerView recycler;
    private ArrayList<Disciplina> todasDisciplinas;
    private ListaDisciplinasAdapter adapter;

    public DisciplinasTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDisciplinasTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializacaoDosCampos();
        buscaAluno();
        listarDisciplinas();

        configuraSpinnerAdapter(context);

        binding.fragmentDisciplinasTabFab.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), FormularioDisciplinaActivity.class);
            startActivity(intent);
        });
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
                            txt_curso.setText(Objects.requireNonNull(document.get(KEY_CURSO)).toString());
                            buscaDisciplinas();
                        }
                    }
                });
    }

    private void buscaDisciplinas() {
        db
                .collection(COLECAO_FIRESTORE_ALUNO).document(docRefAluno)
                .collection(COLECAO_FIRESTORE_DISCIPLINAS)
                .orderBy(KEY_NOME)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> lista = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot documentSnapshot : lista){
                        Disciplina disciplina = documentSnapshot.toObject(Disciplina.class);
                        todasDisciplinas.add(disciplina);
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    private void inicializacaoDosCampos() {
        todasDisciplinas = new ArrayList<>();
        txt_curso = binding.fragmentDisciplinasTabTxtCurso;
        spinner = binding.fragmentDisciplinasTabSpinner;
        recycler = binding.fragmentDisciplinasTabRecyclerView;
    }

    private void configuraSpinnerAdapter(Context context) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(
                        context,
                        R.array.ano_semestre_array,
                        android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    private void listarDisciplinas() {
        configuraRecyclerView(todasDisciplinas);
    }

    private void configuraRecyclerView(ArrayList<Disciplina> todasDisciplinas) {
        configuraAdapter(todasDisciplinas);
    }

    private void configuraAdapter(ArrayList<Disciplina> todasDisciplinas) {
        adapter = new ListaDisciplinasAdapter(context, todasDisciplinas);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickLister(disciplina -> {
            Intent intent = new Intent(getActivity(), ListaAvaliacoesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        listarDisciplinas();
    }

    public static Fragment newInstance() {
        return new DisciplinasTabFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}