package br.edu.fateczl.gestordeprovasetrabalhos.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.gestordeprovasetrabalhos.model.Trabalho;
import br.edu.fateczl.gestordeprovasetrabalhos.persistence.TrabalhoDao;

public class TrabalhoController implements IController<Trabalho> {
    private final TrabalhoDao tDao;

    public TrabalhoController(TrabalhoDao tDao) {
        this.tDao = tDao;
    }

    @Override
    public void inserir(Trabalho trabalho) throws SQLException {
        if (tDao.open() == null) {
            tDao.open();
        }
        tDao.insert(trabalho);
        tDao.close();
    }

    @Override
    public void modificar(Trabalho trabalho) throws SQLException {
        if (tDao.open() == null) {
            tDao.open();
        }
        tDao.update(trabalho);
        tDao.close();
    }

    @Override
    public void deletar(Trabalho trabalho) throws SQLException {
        if (tDao.open() == null) {
            tDao.open();
        }
        tDao.delete(trabalho);
        tDao.close();
    }

    @Override
    public Trabalho buscar(Trabalho trabalho) throws SQLException {
        if (tDao.open() == null) {
            tDao.open();
        }
        return tDao.findOne(trabalho);
    }

    @Override
    public List<Trabalho> listar() throws SQLException {
        if (tDao.open() == null) {
            tDao.open();
        }
        return tDao.findAll();
    }

}
