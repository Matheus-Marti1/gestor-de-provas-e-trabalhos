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

public class DisciplinaDao implements IDisciplinaDao, ICRUDDao<Disciplina> {

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public DisciplinaDao(Context context) {
        this.context = context;
    }

    @Override
    public DisciplinaDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Disciplina disciplina) throws SQLException {
        ContentValues contentValues = getContentValues(disciplina);
        database.insert("disciplina", null, contentValues);
    }


    @Override
    public int update(Disciplina disciplina) throws SQLException {
        ContentValues contentValues = getContentValues(disciplina);
        return database.update("disciplina", contentValues, "id = " + disciplina.getId(), null);
    }

    @Override
    public void delete(Disciplina disciplina) throws SQLException {
        database.delete("disciplina", "id = " + disciplina.getId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Disciplina findOne(Disciplina disciplina) throws SQLException {
        String sql = "SELECT id, nome, professor FROM disciplina WHERE id = " + disciplina.getId();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            disciplina.setId(cursor.getInt(cursor.getColumnIndex("id")));
            disciplina.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            disciplina.setProfessor(cursor.getString(cursor.getColumnIndex("professor")));
        }
        cursor.close();
        return disciplina;
    }

    @SuppressLint("Range")
    @Override
    public List<Disciplina> findAll() throws SQLException {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT id, nome, professor FROM disciplina";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
             Disciplina disciplina = new Disciplina(cursor.getInt(cursor.getColumnIndex("id")),
                     cursor.getString(cursor.getColumnIndex("nome")),
                     cursor.getString(cursor.getColumnIndex("professor")));
             disciplinas.add(disciplina);
             cursor.moveToNext();
        }
        cursor.close();
        return disciplinas;
    }

    private ContentValues getContentValues(Disciplina disciplina) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("id", disciplina.getId());
        contentValue.put("nome", disciplina.getNome());
        contentValue.put("professor", disciplina.getProfessor());
        return contentValue;
    }
}
