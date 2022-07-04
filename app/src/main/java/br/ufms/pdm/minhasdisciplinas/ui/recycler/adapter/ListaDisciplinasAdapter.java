package br.ufms.pdm.minhasdisciplinas.ui.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.ufms.pdm.minhasdisciplinas.R;
import br.ufms.pdm.minhasdisciplinas.model.Disciplina;
import br.ufms.pdm.minhasdisciplinas.ui.recycler.adapter.listener.OnItemClickLister;

public class ListaDisciplinasAdapter extends RecyclerView.Adapter<ListaDisciplinasAdapter.DisciplinaViewHolder> {

    private final ArrayList<Disciplina> disciplinas;
    private final Context context;
    private OnItemClickLister onItemClickLister;

    public ListaDisciplinasAdapter(Context context, ArrayList<Disciplina> disciplinas) {
        this.context = context;
        this.disciplinas = disciplinas;
    }

    public void setOnItemClickLister(OnItemClickLister onItemClickLister) {
        this.onItemClickLister = onItemClickLister;
    }

    @NonNull
    @Override
    public ListaDisciplinasAdapter.DisciplinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_disciplina, parent, false);
        return new DisciplinaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaDisciplinasAdapter.DisciplinaViewHolder holder, int position) {
        Disciplina disciplina = disciplinas.get(position);
        holder.vincula(disciplina);
    }

    @Override
    public int getItemCount() {
        return disciplinas.size();
    }

    public class DisciplinaViewHolder extends RecyclerView.ViewHolder {

        private final TextView nome;
        private final TextView professor;
        private final TextView situacao;
        private Disciplina disciplina;

        public DisciplinaViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.item_disciplina_disciplina);
            professor = itemView.findViewById(R.id.item_disciplina_professor);
            situacao = itemView.findViewById(R.id.item_disciplina_situacao);
            itemView.setOnClickListener(view -> onItemClickLister.onItemClick(disciplina));
        }

        public void vincula(Disciplina disciplina) {
            this.disciplina = disciplina;
            preencheCampo(disciplina);
        }

        private void preencheCampo(Disciplina disciplina) {
            nome.setText(disciplina.getNome());
            professor.setText(disciplina.getProfessor());
            situacao.setText(disciplina.getSituacao());
        }
    }
}
