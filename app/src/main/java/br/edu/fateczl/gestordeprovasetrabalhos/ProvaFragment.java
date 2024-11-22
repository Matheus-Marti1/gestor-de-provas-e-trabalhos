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

public class ProvaFragment extends Fragment {
    private View view;
    private EditText etIdProva, etDescricaoProva, etDataProva, etPesoProva, etNotaProva;
    private CheckBox cbRecuperacaoProva;
    private Spinner spDisciplinaProva;
    private Button btnBuscarProva, btnInserirProva, btnModificarProva, btnExcluirProva, btnListarProva;
    private TextView tvListaProva;

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
        btnBuscarProva = view.findViewById(R.id.btnBuscarProva);
        btnInserirProva = view.findViewById(R.id.btnInserirProva);
        btnModificarProva = view.findViewById(R.id.btnModificarProva);
        btnExcluirProva = view.findViewById(R.id.btnExcluirProva);
        btnListarProva = view.findViewById(R.id.btnListarProva);
        tvListaProva = view.findViewById(R.id.tvListaProva);
        return view;
    }
}