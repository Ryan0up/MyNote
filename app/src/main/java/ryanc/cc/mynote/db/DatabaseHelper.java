package ryanc.cc.mynote.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ryanc.cc.mynote.bean.NoteBean;

/**
 * Created by ryan0up on 2017/7/3.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String NOTE_TITLE = "note_title";
    public static final String NOTE_DATE = "note_date";
    public static final String NOTE_CONTENT = "note_content";
    public static final String RYAN_NOTE = "ryan_note";

    //创建数据库ryanDB
    public DatabaseHelper(Context context) {
        super(context, "ryanDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建数据表ryan_note
        db.execSQL("create table if not exists ryan_note(" +
                "id integer primary key," +
                "note_title varchar," +
                "note_date varchar," +
                "note_content varchar)");
    }

    /**
     * 添加数据的方法
     * @param noteBean
     */
    public void insertNote(NoteBean noteBean){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE_TITLE,noteBean.getNoteTitle());
        cv.put(NOTE_DATE,noteBean.getNoteDate());
        cv.put(NOTE_CONTENT,noteBean.getNoteContent());
        database.insert(RYAN_NOTE,null,cv);
    }

    public Cursor getAllNoteData(){
        SQLiteDatabase database = getWritableDatabase();
        return database.query(RYAN_NOTE,null,null,null,null,null,NOTE_DATE+" desc");
    }

    public void deleteNoteData(int id){
        SQLiteDatabase database = getWritableDatabase();
        database.delete("ryan_note", "id="+id,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
