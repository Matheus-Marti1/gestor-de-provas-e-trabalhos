package br.edu.fateczl.gestordeprovasetrabalhos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DisciplinaFragment extends Fragment {
    private View view;
    private EditText etNomeDisciplina, etProfessorDisciplina;
    private Button btnBuscarDisciplina, btnInserirDisciplina, btnModificarDisciplina, btnExcluirDisciplina, btnListarDisciplina;
    private TextView tvListaDisciplina;

    public DisciplinaFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disciplina, container, false);
        etNomeDisciplina = view.findViewById(R.id.etNomeDisciplina);
        etProfessorDisciplina = view.findViewById(R.id.etProfessorDisciplina);
        btnBuscarDisciplina = view.findViewById(R.id.btnBuscarDisciplina);
        btnInserirDisciplina = view.findViewById(R.id.btnInserirDisciplina);
        btnModificarDisciplina = view.findViewById(R.id.btnModificarDisciplina);
        btnExcluirDisciplina = view.findViewById(R.id.btnExcluirDisciplina);
        btnListarDisciplina = view.findViewById(R.id.btnListarDisciplina);
        tvListaDisciplina = view.findViewById(R.id.tvListaDisciplinas);
        return view;
    }
}