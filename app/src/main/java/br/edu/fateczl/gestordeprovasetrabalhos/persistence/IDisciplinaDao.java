package br.edu.fateczl.gestordeprovasetrabalhos.persistence;

import java.sql.SQLException;

public interface IDisciplinaDao {
    public DisciplinaDao open() throws SQLException;
    public void close();
}
