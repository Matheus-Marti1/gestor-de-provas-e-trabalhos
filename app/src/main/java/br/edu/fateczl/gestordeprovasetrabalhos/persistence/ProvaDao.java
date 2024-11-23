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
import br.edu.fateczl.gestordeprovasetrabalhos.model.Prova;

public class ProvaDao implements IProvaDao, ICRUDDao<Prova> {
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public ProvaDao(Context context) {
        this.context = context;
    }


    @Override
    public ProvaDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }


    @Override
    public void insert(Prova prova) throws SQLException {
        ContentValues contentValues = getContentValues(prova);
        database.insert("atividade", null, contentValues);
    }


    @Override
    public int update(Prova prova) throws SQLException {
        ContentValues contentValues = getContentValues(prova);
        return database.update("atividade", contentValues, "id_atv = " + prova.getId(), null);
    }

    @Override
    public void delete(Prova prova) throws SQLException {
        database.delete("atividade", "id_atv = " + prova.getId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Prova findOne(Prova prova) throws SQLException {
        String sql = "SELECT d.id AS disciplina_id, d.nome, d.professor, " +
                "p.id_atv, p.descricao, p.data, p.peso, p.nota, p.is_recuperacao " +
                "FROM disciplina d, atividade p " +
                "WHERE d.id = p.disciplina_id " +
                "AND p.id_atv = " + prova.getId();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        assert cursor != null;
        if (!cursor.isAfterLast()) {
            Disciplina disciplina = new Disciplina(cursor.getInt(cursor.getColumnIndex("disciplina_id")),
                    cursor.getString(cursor.getColumnIndex("nome")),
                    cursor.getString(cursor.getColumnIndex("professor")));

            prova.setId(cursor.getInt(cursor.getColumnIndex("id_atv")));
            prova.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            prova.setData(cursor.getString(cursor.getColumnIndex("data")));
            if (!cursor.isNull(cursor.getColumnIndex("peso"))) {
                prova.setPeso(cursor.getDouble(cursor.getColumnIndex("peso")));
            }
            if (!cursor.isNull(cursor.getColumnIndex("nota"))) {
                prova.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            }
            prova.setRecuperacao(cursor.getInt(cursor.getColumnIndex("is_recuperacao")) == 1);
            prova.setDisciplina(disciplina);
        }
        cursor.close();
        return prova;
    }

    @SuppressLint("Range")
    @Override
    public List<Prova> findAll() throws SQLException {
        List<Prova> provas = new ArrayList<>();
        String sql = "SELECT d.id AS disciplina_id, d.nome, d.professor, " +
                "p.id_atv, p.descricao, p.data, p.peso, p.nota, p.is_recuperacao " +
                "FROM disciplina d, atividade p " +
                "WHERE d.id = p.disciplina_id AND p.tipo = 'prova';";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (true) {
            assert cursor != null;
            if (cursor.isAfterLast()) break;
            boolean isRecuperacao = cursor.getInt(cursor.getColumnIndex("is_recuperacao")) == 1;
            Disciplina disciplina = new Disciplina(cursor.getInt(cursor.getColumnIndex("disciplina_id")),
                    cursor.getString(cursor.getColumnIndex("nome")),
                    cursor.getString(cursor.getColumnIndex("professor")));
            Prova prova = new Prova(cursor.getInt(cursor.getColumnIndex("id_atv")),
                    cursor.getString(cursor.getColumnIndex("descricao")),
                    cursor.getString(cursor.getColumnIndex("data")), isRecuperacao, disciplina);

            if (!cursor.isNull(cursor.getColumnIndex("peso"))) {
                prova.setPeso(cursor.getDouble(cursor.getColumnIndex("peso")));
            }
            if (!cursor.isNull(cursor.getColumnIndex("nota"))) {
                prova.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            }

            provas.add(prova);
            cursor.moveToNext();
        }
        cursor.close();
        return provas;
    }

    private ContentValues getContentValues(Prova prova) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("id_atv", prova.getId());
        contentValue.put("descricao", prova.getDescricao());
        contentValue.put("data", prova.getData());
        contentValue.put("peso", prova.getPeso());
        contentValue.put("nota", prova.getNota());
        contentValue.put("tipo", "prova");
        contentValue.put("is_recuperacao", prova.isRecuperacao() ? 1 : 0);
        contentValue.put("disciplina_id", prova.getDisciplina().getId());
        return contentValue;
    }
}
