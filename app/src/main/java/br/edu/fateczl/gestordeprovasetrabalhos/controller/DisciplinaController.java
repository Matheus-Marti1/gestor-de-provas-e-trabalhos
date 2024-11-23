package br.edu.fateczl.gestordeprovasetrabalhos.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.gestordeprovasetrabalhos.model.Disciplina;
import br.edu.fateczl.gestordeprovasetrabalhos.persistence.DisciplinaDao;

public class DisciplinaController implements IController<Disciplina> {
    private final DisciplinaDao dDao;

    public DisciplinaController(DisciplinaDao dDao) {
        this.dDao = dDao;
    }

    @Override
    public void inserir(Disciplina disciplina) throws SQLException {
        if (dDao.open() == null) {
            dDao.open();
        }
        dDao.insert(disciplina);
        dDao.close();
    }

    @Override
    public void modificar(Disciplina disciplina) throws SQLException {
        if (dDao.open() == null) {
            dDao.open();
        }
        dDao.update(disciplina);
        dDao.close();
    }

    @Override
    public void deletar(Disciplina disciplina) throws SQLException {
        if (dDao.open() == null) {
            dDao.open();
        }
        dDao.delete(disciplina);
        dDao.close();
    }

    @Override
    public Disciplina buscar(Disciplina disciplina) throws SQLException {
        if (dDao.open() == null) {
            dDao.open();
        }
        return dDao.findOne(disciplina);
    }

    @Override
    public List<Disciplina> listar() throws SQLException {
        if (dDao.open() == null) {
            dDao.open();
        }
        return dDao.findAll();
    }
}
