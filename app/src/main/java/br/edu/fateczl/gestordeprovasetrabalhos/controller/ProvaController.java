package br.edu.fateczl.gestordeprovasetrabalhos.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.edu.fateczl.gestordeprovasetrabalhos.model.Prova;
import br.edu.fateczl.gestordeprovasetrabalhos.persistence.ProvaDao;

public class ProvaController implements IController<Prova> {
    private final ProvaDao pDao;

    public ProvaController(ProvaDao pDao) {
        this.pDao = pDao;
    }

    @Override
    public void inserir(Prova prova) throws SQLException {
        if (pDao.open() == null) {
            pDao.open();
        }
        pDao.insert(prova);
        pDao.close();
    }

    @Override
    public void modificar(Prova prova) throws SQLException {
        if (pDao.open() == null) {
            pDao.open();
        }
        pDao.update(prova);
        pDao.close();
    }

    @Override
    public void deletar(Prova prova) throws SQLException {
        if (pDao.open() == null) {
            pDao.open();
        }
        pDao.delete(prova);
        pDao.close();
    }

    @Override
    public Prova buscar(Prova prova) throws SQLException {
        if (pDao.open() == null) {
            pDao.open();
        }
        return pDao.findOne(prova);
    }

    @Override
    public List<Prova> listar() throws SQLException {
        if (pDao.open() == null) {
            pDao.open();
        }
        return pDao.findAll();
    }
}
