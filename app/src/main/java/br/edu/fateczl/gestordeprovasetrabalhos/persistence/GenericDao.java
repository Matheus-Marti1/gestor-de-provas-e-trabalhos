package br.edu.fateczl.gestordeprovasetrabalhos.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {

    private static final String DATABASE = "GESTOR.DB";
    private static final int DATABASE_VER = 1;
    private static final String CREATE_TABLE_DISCIPLINA =
            "CREATE TABLE disciplina ( " +
                    "id INTEGER NOT NULL PRIMARY KEY, " +
                    "nome TEXT NOT NULL, " +
                    "professor TEXT NOT NULL);";
    private static final String CREATE_TABLE_ATIVIDADE =
            "CREATE TABLE atividade ( " +
                    "id INTEGER NOT NULL PRIMARY KEY, " +
                    "descricao TEXT NOT NULL, " +
                    "data TEXT NOT NULL, " +
                    "nota REAL, " +
                    "peso REAL NOT NULL, " +
                    "tipo TEXT NOT NULL, " +
                    "requer_apresentacao INTEGER, " +
                    "is_recuperacao INTEGER, " +
                    "disciplina_id INTEGER NOT NULL, " +
                    "FOREIGN KEY (disciplina_id) REFERENCES disciplina(id) ON DELETE CASCADE);";

    public GenericDao(Context context) {
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DISCIPLINA);
        db.execSQL(CREATE_TABLE_ATIVIDADE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS disciplina");
            db.execSQL("DROP TABLE IF EXISTS atividade");
            onCreate(db);
        }
    }
}
