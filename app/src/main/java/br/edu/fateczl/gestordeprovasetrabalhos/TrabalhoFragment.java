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
import br.edu.fateczl.gestordeprovasetrabalhos.controller.TrabalhoController;
import br.edu.fateczl.gestordeprovasetrabalhos.model.Disciplina;
import br.edu.fateczl.gestordeprovasetrabalhos.model.Trabalho;
import br.edu.fateczl.gestordeprovasetrabalhos.persistence.DisciplinaDao;
import br.edu.fateczl.gestordeprovasetrabalhos.persistence.TrabalhoDao;

public class TrabalhoFragment extends Fragment {
    private View view;
    private EditText etIdTrabalho, etDescricaoTrabalho, etDataTrabalho, etPesoTrabalho, etNotaTrabalho, etNumIntegrantesTrabalho;
    private CheckBox cbApresentacao;
    private Spinner spDisciplinaTrabalho;
    private TextView tvListaTrabalho;
    private TrabalhoController tCont;
    private DisciplinaController dCont;
    private List<Disciplina> disciplinas;

    public TrabalhoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trabalho, container, false);
        etIdTrabalho = view.findViewById(R.id.etIdTrabalho);
        etDescricaoTrabalho = view.findViewById(R.id.etDescricaoTrabalho);
        etDataTrabalho = view.findViewById(R.id.etDataTrabalho);
        etPesoTrabalho = view.findViewById(R.id.etPesoTrabalho);
        etNotaTrabalho = view.findViewById(R.id.etNotaTrabalho);
        etNumIntegrantesTrabalho = view.findViewById(R.id.etNumIntegrantesTrabalho);
        cbApresentacao = view.findViewById(R.id.cbApresentacao);
        spDisciplinaTrabalho = view.findViewById(R.id.spDisciplinaTrabalho);
        Button btnBuscarTrabalho = view.findViewById(R.id.btnBuscarTrabalho);
        Button btnInserirTrabalho = view.findViewById(R.id.btnInserirTrabalho);
        Button btnModificarTrabalho = view.findViewById(R.id.btnModificarTrabalho);
        Button btnExcluirTrabalho = view.findViewById(R.id.btnExcluirTrabalho);
        Button btnListarTrabalho = view.findViewById(R.id.btnListarTrabalho);
        tvListaTrabalho = view.findViewById(R.id.tvListaTrabalho);
        tvListaTrabalho.setMovementMethod(new ScrollingMovementMethod());

        tCont = new TrabalhoController(new TrabalhoDao(view.getContext()));
        dCont = new DisciplinaController(new DisciplinaDao(view.getContext()));
        preencheSpinner();

        btnInserirTrabalho.setOnClickListener(op -> acaoInserir());
        btnModificarTrabalho.setOnClickListener(op -> acaoModificar());
        btnExcluirTrabalho.setOnClickListener(op -> acaoExcluir());
        btnBuscarTrabalho.setOnClickListener(op -> acaoBuscar());
        btnListarTrabalho.setOnClickListener(op -> acaoListar());
        return view;
    }

    private void acaoInserir() {
        if (!validaCampos()) {
            return;
        }
        Trabalho trabalho = montaTrabalho();
        if (trabalho == null) {
            Toast.makeText(view.getContext(), "Erro ao montar o trabalho. Verifique os dados.", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            tCont.inserir(trabalho);
            Toast.makeText(view.getContext(), "Trabalho inserido com sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoModificar() {
        if (!validaCampos()) {
            return;
        }
        Trabalho trabalho = montaTrabalho();
        if (trabalho == null) {
            Toast.makeText(view.getContext(), "Erro ao montar o trabalho. Verifique os dados.", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            tCont.modificar(trabalho);
            Toast.makeText(view.getContext(), "Trabalho atualizado com sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoExcluir() {
        if (etIdTrabalho.getText().toString().trim().isEmpty()) {
            Toast.makeText(view.getContext(), "Id é obrigatório!", Toast.LENGTH_LONG).show();
        } else {
            Trabalho trabalho = new Trabalho(Integer.parseInt(etIdTrabalho.getText().toString()), null,
                    null, false, 0, null);
            try {
                tCont.deletar(trabalho);
                Toast.makeText(view.getContext(), "Trabalho excluido com sucesso", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        if (etIdTrabalho.getText().toString().trim().isEmpty()) {
            Toast.makeText(view.getContext(), "Id é obrigatório!", Toast.LENGTH_LONG).show();
        } else {
            Trabalho trabalho = new Trabalho(Integer.parseInt(etIdTrabalho.getText().toString()), null,
                    null, false, 0, null);
            try {
                disciplinas = dCont.listar();
                trabalho = tCont.buscar(trabalho);
                if (trabalho.getDescricao() != null) {
                    preencheCampos(trabalho);
                } else {
                    Toast.makeText(view.getContext(), "Trabalho não encontrado", Toast.LENGTH_LONG).show();
                    limpaCampos();
                }
            } catch (SQLException e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void acaoListar() {
        try {
            List<Trabalho> trabalhos = tCont.listar();
            StringBuilder buffer = new StringBuilder();
            for (Trabalho t : trabalhos) {
                buffer.append(t.toString()).append("\n");
            }
            tvListaTrabalho.setText(buffer.toString());
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Trabalho montaTrabalho() {
        Trabalho t = null;
        try {
            t = new Trabalho(Integer.parseInt(etIdTrabalho.getText().toString()),
                    etDescricaoTrabalho.getText().toString(),
                    etDataTrabalho.getText().toString(),
                    cbApresentacao.isChecked(),
                    Integer.parseInt(etNumIntegrantesTrabalho.getText().toString()), (Disciplina) spDisciplinaTrabalho.getSelectedItem());
            if (!etPesoTrabalho.getText().toString().trim().isEmpty()) {
                t.setPeso(Double.parseDouble(etPesoTrabalho.getText().toString()));
            }
            if (!etNotaTrabalho.getText().toString().trim().isEmpty()) {
                t.setNota(Double.parseDouble(etNotaTrabalho.getText().toString()));
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return t;
    }

    private void limpaCampos() {
        etIdTrabalho.setText("");
        etDescricaoTrabalho.setText("");
        etDataTrabalho.setText("");
        etPesoTrabalho.setText("");
        etNotaTrabalho.setText("");
        etNumIntegrantesTrabalho.setText("");
        cbApresentacao.setChecked(false);
        spDisciplinaTrabalho.setSelection(0);
    }

    private void preencheCampos(Trabalho t) {
        etIdTrabalho.setText(String.valueOf(t.getId()));
        etDescricaoTrabalho.setText(t.getDescricao());
        etDataTrabalho.setText(t.getData());
        etPesoTrabalho.setText(String.valueOf(t.getPeso()));
        etNotaTrabalho.setText(String.valueOf(t.getNota()));
        etNumIntegrantesTrabalho.setText(String.valueOf(t.getNumIntegrantes()));
        cbApresentacao.setChecked(t.isRequerApresentacao());

        int cont = 1;
        for (Disciplina d : disciplinas) {
            if (d.getId() == t.getDisciplina().getId()) {
                spDisciplinaTrabalho.setSelection(cont);
            } else {
                cont++;
            }
        }
        if (cont > disciplinas.size()) {
            spDisciplinaTrabalho.setSelection(0);
        }
    }

    private void preencheSpinner() {
        Disciplina d0 = new Disciplina(0, "Selecione uma disciplina", "");
        try {
            disciplinas = dCont.listar();
            disciplinas.add(0, d0);
            ArrayAdapter<Disciplina> ad = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, disciplinas);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDisciplinaTrabalho.setAdapter(ad);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validaCampos() {
        if (etIdTrabalho.getText().toString().trim().isEmpty()) {
            Toast.makeText(view.getContext(), "Id é obrigatório!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (etDescricaoTrabalho.getText().toString().trim().isEmpty()) {
            Toast.makeText(view.getContext(), "Descrição é obrigatória!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (etDataTrabalho.getText().toString().trim().isEmpty()) {
            Toast.makeText(view.getContext(), "Data é obrigatória!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (spDisciplinaTrabalho.getSelectedItemPosition() == 0) {
            Toast.makeText(view.getContext(), "Selecione uma disciplina!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}