/*
 *@author:<Matheus Augusto Marti>
 */

package br.edu.fateczl.gestordeprovasetrabalhos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.gestordeprovasetrabalhos.controller.DisciplinaController;
import br.edu.fateczl.gestordeprovasetrabalhos.controller.ProvaController;
import br.edu.fateczl.gestordeprovasetrabalhos.model.Disciplina;
import br.edu.fateczl.gestordeprovasetrabalhos.model.Prova;
import br.edu.fateczl.gestordeprovasetrabalhos.persistence.DisciplinaDao;
import br.edu.fateczl.gestordeprovasetrabalhos.persistence.ProvaDao;

public class ProvaFragment extends Fragment {
    private View view;
    private EditText etIdProva, etDescricaoProva, etDataProva, etPesoProva, etNotaProva;
    private CheckBox cbRecuperacaoProva;
    private Spinner spDisciplinaProva;
    private TextView tvListaProva;
    private ProvaController pCont;
    private DisciplinaController dCont;
    private List<Disciplina> disciplinas;

    public ProvaFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_prova, container, false);
        etIdProva = view.findViewById(R.id.etIdProva);
        etDescricaoProva = view.findViewById(R.id.etDescricaoProva);
        etDataProva = view.findViewById(R.id.etDataProva);
        etPesoProva = view.findViewById(R.id.etPesoProva);
        etNotaProva = view.findViewById(R.id.etNotaProva);
        cbRecuperacaoProva = view.findViewById(R.id.cbRecuperacaoProva);
        spDisciplinaProva = view.findViewById(R.id.spDisciplinaProva);
        Button btnBuscarProva = view.findViewById(R.id.btnBuscarProva);
        Button btnInserirProva = view.findViewById(R.id.btnInserirProva);
        Button btnModificarProva = view.findViewById(R.id.btnModificarProva);
        Button btnExcluirProva = view.findViewById(R.id.btnExcluirProva);
        Button btnListarProva = view.findViewById(R.id.btnListarProva);
        tvListaProva = view.findViewById(R.id.tvListaProva);
        tvListaProva.setMovementMethod(new ScrollingMovementMethod());

        pCont = new ProvaController(new ProvaDao(view.getContext()));
        dCont = new DisciplinaController(new DisciplinaDao(view.getContext()));
        preencheSpinner();

        btnInserirProva.setOnClickListener(op -> acaoInserir());
        btnModificarProva.setOnClickListener(op -> acaoModificar());
        btnExcluirProva.setOnClickListener(op -> acaoExcluir());
        btnBuscarProva.setOnClickListener(op -> acaoBuscar());
        btnListarProva.setOnClickListener(op -> acaoListar());

        return view;
    }

    private void acaoInserir() {
        if (!validaCampos()) {
            return;
        }

        Prova prova = montaProva();

        if (prova == null) {
            Toast.makeText(view.getContext(), "Erro ao montar a prova. Verifique os dados.", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            pCont.inserir(prova);
            Toast.makeText(view.getContext(), "Prova inserida com sucesso!", Toast.LENGTH_LONG).show();
            limpaCampos();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), "Erro ao inserir a prova: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoModificar() {
        if (!validaCampos()) {
            return;
        }
        Prova prova = montaProva();
        if (prova == null) {
            Toast.makeText(view.getContext(), "Erro ao montar a prova. Verifique os dados.", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            pCont.modificar(prova);
            Toast.makeText(view.getContext(), "Prova atualizada com sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), "Erro ao atualizar a prova: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoExcluir() {
        if (etIdProva.getText().toString().trim().isEmpty()) {
            Toast.makeText(view.getContext(), "Id é obrigatório!", Toast.LENGTH_LONG).show();
        } else {
            Prova prova = new Prova(Integer.parseInt(etIdProva.getText().toString()),
                    null, null,
                    false, null);
            try {
                pCont.deletar(prova);
                Toast.makeText(view.getContext(), "Prova excluida com sucesso", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), "Erro ao excluir a prova: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        if (etIdProva.getText().toString().trim().isEmpty()) {
            Toast.makeText(view.getContext(), "Id é obrigatório!", Toast.LENGTH_LONG).show();
        } else {
            Prova prova = new Prova(Integer.parseInt(etIdProva.getText().toString()),
                    null, null,
                    false, null);
            try {
                disciplinas = dCont.listar();
                prova = pCont.buscar(prova);
                if (prova.getDescricao() != null) {
                    preencheCampos(prova);
                } else {
                    Toast.makeText(view.getContext(), "Prova não encontrada", Toast.LENGTH_LONG).show();
                    limpaCampos();
                }
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), "Erro ao buscar a prova: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void acaoListar() {
        try {
            List<Prova> provas = pCont.listar();
            StringBuilder buffer = new StringBuilder();
            for (Prova p : provas) {
                buffer.append(p.toString()).append("\n");
            }
            tvListaProva.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), "Erro ao listar as provas: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void preencheCampos(Prova p) {
        etIdProva.setText(String.valueOf(p.getId()));
        etDescricaoProva.setText(p.getDescricao());
        etDataProva.setText(p.getData());
        etPesoProva.setText(String.valueOf(p.getPeso()));
        etNotaProva.setText(String.valueOf(p.getNota()));
        cbRecuperacaoProva.setChecked(p.isRecuperacao());

        int cont = 1;
        for (Disciplina d : disciplinas) {
            if (d.getId() == p.getDisciplina().getId()) {
                spDisciplinaProva.setSelection(cont);
            } else {
                cont++;
            }
        }
        if (cont > disciplinas.size()) {
            spDisciplinaProva.setSelection(0);
        }
    }

    private void limpaCampos() {
        etIdProva.setText("");
        etDescricaoProva.setText("");
        etDataProva.setText("");
        etPesoProva.setText("");
        etNotaProva.setText("");
        cbRecuperacaoProva.setChecked(false);
        spDisciplinaProva.setSelection(0);
    }

    private Prova montaProva() {
        Prova p = null;
        try {
            int id = etIdProva.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(etIdProva.getText().toString());
            String descricao = etDescricaoProva.getText().toString().trim();
            String data = etDataProva.getText().toString().trim();
            boolean recuperacao = cbRecuperacaoProva.isChecked();
            Disciplina disciplina = (Disciplina) spDisciplinaProva.getSelectedItem();

            p = new Prova(id, descricao, data, recuperacao, disciplina);

            if (!etPesoProva.getText().toString().trim().isEmpty()) {
                p.setPeso(Double.parseDouble(etPesoProva.getText().toString()));
            }
            if (!etNotaProva.getText().toString().trim().isEmpty()) {
                p.setNota(Double.parseDouble(etNotaProva.getText().toString()));
            }
        } catch (NumberFormatException e) {
            Toast.makeText(view.getContext(), "Formato inválido em ID, peso ou nota!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), "Erro ao montar a prova: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return p;
    }

    private void preencheSpinner() {
        Disciplina d0 = new Disciplina(0, "Selecione uma disciplina", "");
        try {
            disciplinas = dCont.listar();
            disciplinas.add(0, d0);
            ArrayAdapter<Disciplina> ad = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, disciplinas);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDisciplinaProva.setAdapter(ad);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validaCampos() {
        if (etIdProva.getText().toString().trim().isEmpty()) {
            Toast.makeText(view.getContext(), "Id é obrigatório!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (etDescricaoProva.getText().toString().trim().isEmpty()) {
            Toast.makeText(view.getContext(), "Descrição é obrigatória!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (etDataProva.getText().toString().trim().isEmpty()) {
            Toast.makeText(view.getContext(), "Data é obrigatória!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (spDisciplinaProva.getSelectedItemPosition() == 0) {
            Toast.makeText(view.getContext(), "Selecione uma disciplina!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}