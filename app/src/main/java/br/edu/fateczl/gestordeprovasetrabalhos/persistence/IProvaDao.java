package br.edu.fateczl.gestordeprovasetrabalhos.persistence;

import java.sql.SQLException;

public interface IProvaDao {
    public ProvaDao open() throws SQLException;
    public void close();
}
