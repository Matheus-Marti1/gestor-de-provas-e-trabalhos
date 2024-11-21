package br.edu.fateczl.gestordeprovasetrabalhos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TrabalhoFragment extends Fragment {
    private View view;
    private EditText etIdTrabalho, etDescricaoTrabalho, etDataTrabalho, etPesoTrabalho, etNotaTrabalho, etNumIntegrantesTrabalho;
    private CheckBox cbApresentacao;
    private Spinner spDisciplinaTrabalho;
    private Button btnBuscarTrabalho, btnInserirTrabalho, btnModificarTrabalho, btnExcluirTrabalho, btnListarTrabalho;
    private TextView tvListaTrabalho;

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
        btnBuscarTrabalho = view.findViewById(R.id.btnBuscarTrabalho);
        btnInserirTrabalho = view.findViewById(R.id.btnInserirTrabalho);
        btnModificarTrabalho = view.findViewById(R.id.btnModificarTrabalho);
        btnExcluirTrabalho = view.findViewById(R.id.btnExcluirTrabalho);
        btnListarTrabalho = view.findViewById(R.id.btnListarTrabalho);
        tvListaTrabalho = view.findViewById(R.id.tvListaTrabalho);
        return view;
    }
}