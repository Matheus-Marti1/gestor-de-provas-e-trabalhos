package br.edu.fateczl.gestordeprovasetrabalhos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.gestordeprovasetrabalhos.controller.DisciplinaController;
import br.edu.fateczl.gestordeprovasetrabalhos.model.Disciplina;
import br.edu.fateczl.gestordeprovasetrabalhos.persistence.DisciplinaDao;

public class DisciplinaFragment extends Fragment {
    private View view;
    private EditText etIdDisciplina, etNomeDisciplina, etProfessorDisciplina;
    private Button btnBuscarDisciplina, btnInserirDisciplina, btnModificarDisciplina, btnExcluirDisciplina, btnListarDisciplina;
    private TextView tvListaDisciplina;
    private DisciplinaController dCont;

    public DisciplinaFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disciplina, container, false);
        etIdDisciplina = view.findViewById(R.id.etIdDisciplina);
        etNomeDisciplina = view.findViewById(R.id.etNomeDisciplina);
        etProfessorDisciplina = view.findViewById(R.id.etProfessorDisciplina);
        btnBuscarDisciplina = view.findViewById(R.id.btnBuscarDisciplina);
        btnInserirDisciplina = view.findViewById(R.id.btnInserirDisciplina);
        btnModificarDisciplina = view.findViewById(R.id.btnModificarDisciplina);
        btnExcluirDisciplina = view.findViewById(R.id.btnExcluirDisciplina);
        btnListarDisciplina = view.findViewById(R.id.btnListarDisciplina);
        tvListaDisciplina = view.findViewById(R.id.tvListaDisciplinas);
        tvListaDisciplina.setMovementMethod(new ScrollingMovementMethod());

        dCont = new DisciplinaController(new DisciplinaDao(view.getContext()));

        btnInserirDisciplina.setOnClickListener(op -> acaoInserir());
        btnModificarDisciplina.setOnClickListener(op -> acaoModificar());
        btnExcluirDisciplina.setOnClickListener(op -> acaoExcluir());
        btnBuscarDisciplina.setOnClickListener(op -> acaoBuscar());
        btnListarDisciplina.setOnClickListener(op -> acaoListar());
        return view;
    }

    private void acaoInserir() {
        Disciplina disciplina = montaDisciplina();
        try {
            dCont.inserir(disciplina);
            Toast.makeText(view.getContext(), "Disciplina inserida com sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoModificar() {
        Disciplina disciplina = montaDisciplina();
        try {
            dCont.modificar(disciplina);
            Toast.makeText(view.getContext(), "Disciplina atualizada com sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoExcluir() {
        Disciplina disciplina = montaDisciplina();
        try {
            dCont.deletar(disciplina);
            Toast.makeText(view.getContext(), "Disciplina excluida com sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        Disciplina disciplina = montaDisciplina();
        try {
            disciplina = dCont.buscar(disciplina);
            if (disciplina.getNome() != null) {
                preencheCampos(disciplina);
            } else {
                Toast.makeText(view.getContext(), "Disciplina não encontrada", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Disciplina> disciplinas = dCont.listar();
            StringBuilder buffer = new StringBuilder();
            for (Disciplina d : disciplinas) {
                buffer.append(d.toString()).append("\n");
            }
            tvListaDisciplina.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Disciplina montaDisciplina() {
        return new Disciplina(Integer.parseInt(etIdDisciplina.getText().toString()),
                etNomeDisciplina.getText().toString(),
                etProfessorDisciplina.getText().toString());
    }

    private void limpaCampos() {
        etIdDisciplina.setText("");
        etNomeDisciplina.setText("");
        etProfessorDisciplina.setText("");
        tvListaDisciplina.setText("");
    }

    private void preencheCampos(Disciplina d) {
        etIdDisciplina.setText(String.valueOf(d.getId()));
        etNomeDisciplina.setText(d.getNome());
        etProfessorDisciplina.setText(d.getProfessor());
        tvListaDisciplina.setText(d.toString());
    }
}