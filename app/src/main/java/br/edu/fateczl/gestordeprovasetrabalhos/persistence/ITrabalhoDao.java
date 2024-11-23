/*
 *@author:<Matheus Augusto Marti>
 */

package br.edu.fateczl.gestordeprovasetrabalhos.persistence;

import java.sql.SQLException;

public interface ITrabalhoDao {
    public TrabalhoDao open() throws SQLException;
    public void close();
}
