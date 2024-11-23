/*
 *@author:<Matheus Augusto Marti>
 */

package br.edu.fateczl.gestordeprovasetrabalhos.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.gestordeprovasetrabalhos.model.Disciplina;
import br.edu.fateczl.gestordeprovasetrabalhos.model.Trabalho;

public class TrabalhoDao implements ITrabalhoDao, ICRUDDao<Trabalho> {
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public TrabalhoDao(Context context) {
        this.context = context;
    }

    @Override
    public TrabalhoDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Trabalho trabalho) throws SQLException {
        ContentValues contentValues = getContentValues(trabalho);
        database.insert("atividade", null, contentValues);
    }

    @Override
    public int update(Trabalho trabalho) throws SQLException {
        ContentValues contentValues = getContentValues(trabalho);
        return database.update("atividade", contentValues, "id_atv = " + trabalho.getId(), null);
    }

    @Override
    public void delete(Trabalho trabalho) throws SQLException {
        database.delete("atividade", "id_atv = " + trabalho.getId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Trabalho findOne(Trabalho trabalho) throws SQLException {
        String sql = "SELECT d.id AS disciplina_id, d.nome, d.professor, " +
                "t.id_atv, t.descricao, t.data, t.peso, t.nota, t.num_integrantes, t.requer_apresentacao " +
                "FROM disciplina d, atividade t " +
                "WHERE d.id = t.disciplina_id " +
                "AND t.id_atv = " + trabalho.getId();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        assert cursor != null;
        if (!cursor.isAfterLast()) {
            Disciplina disciplina = new Disciplina(cursor.getInt(cursor.getColumnIndex("disciplina_id")),
                    cursor.getString(cursor.getColumnIndex("nome")),
                    cursor.getString(cursor.getColumnIndex("professor")));

            trabalho.setId(cursor.getInt(cursor.getColumnIndex("id_atv")));
            trabalho.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            trabalho.setData(cursor.getString(cursor.getColumnIndex("data")));
            if (!cursor.isNull(cursor.getColumnIndex("peso"))) {
                trabalho.setPeso(cursor.getDouble(cursor.getColumnIndex("peso")));
            }
            if (!cursor.isNull(cursor.getColumnIndex("nota"))) {
                trabalho.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            }
            trabalho.setNumIntegrantes(cursor.getInt(cursor.getColumnIndex("num_integrantes")));
            trabalho.setRequerApresentacao(cursor.getInt(cursor.getColumnIndex("requer_apresentacao")) == 1);
            trabalho.setDisciplina(disciplina);
        }
        cursor.close();
        return trabalho;
    }

    @SuppressLint("Range")
    @Override
    public List<Trabalho> findAll() throws SQLException {
        List<Trabalho> trabalhos = new ArrayList<>();
        String sql = "SELECT d.id AS disciplina_id, d.nome, d.professor, " +
                "t.id_atv, t.descricao, t.data, t.peso, t.nota, t.num_integrantes, t.requer_apresentacao " +
                "FROM disciplina d, atividade t " +
                "WHERE d.id = t.disciplina_id AND t.tipo = 'trabalho'";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (true) {
            assert cursor != null;
            if (cursor.isAfterLast()) break;
            boolean requerApresentacao = cursor.getInt(cursor.getColumnIndex("requer_apresentacao")) == 1;
            Disciplina disciplina = new Disciplina(cursor.getInt(cursor.getColumnIndex("disciplina_id")),
                    cursor.getString(cursor.getColumnIndex("nome")),
                    cursor.getString(cursor.getColumnIndex("professor")));
            Trabalho trabalho = new Trabalho(cursor.getInt(cursor.getColumnIndex("id_atv")),
                    cursor.getString(cursor.getColumnIndex("descricao")),
                    cursor.getString(cursor.getColumnIndex("data")), requerApresentacao,
                    cursor.getInt(cursor.getColumnIndex("num_integrantes")),
                    disciplina);

            if (!cursor.isNull(cursor.getColumnIndex("peso"))) {
                trabalho.setPeso(cursor.getDouble(cursor.getColumnIndex("peso")));
            }
            if (!cursor.isNull(cursor.getColumnIndex("nota"))) {
                trabalho.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            }


            trabalhos.add(trabalho);
            cursor.moveToNext();
        }
        cursor.close();
        return trabalhos;
    }

    private ContentValues getContentValues(Trabalho trabalho) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("id_atv", trabalho.getId());
        contentValue.put("descricao", trabalho.getDescricao());
        contentValue.put("data", trabalho.getData());
        contentValue.put("peso", trabalho.getPeso());
        contentValue.put("nota", trabalho.getNota());
        contentValue.put("tipo", "trabalho");
        contentValue.put("num_integrantes", trabalho.getNumIntegrantes());
        contentValue.put("requer_apresentacao", trabalho.isRequerApresentacao() ? 1 : 0);
        contentValue.put("disciplina_id", trabalho.getDisciplina().getId());
        return contentValue;
    }
}
