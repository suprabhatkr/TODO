package com.example.todoplanner.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todoplanner.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String ID = "id";
    private static final String NAME = "todoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String STATUS = "status";
    private static final String TASK = "task";
    private static final String CREATE_TABLE = "Create table "+TODO_TABLE+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+STATUS+" INTEGER, "+TASK+" TEXT)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TODO_TABLE);
        onCreate(db);
    }

    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK,task.getText());
        cv.put(STATUS,task.getStatus());
        db.insert(TODO_TABLE,null,cv);
    }

    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList= new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setText(cur.getString(cur.getColumnIndex(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id,int Status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS,Status);
        db.update(TODO_TABLE,cv,ID+"=?",new String[] {String.valueOf(id)});
    }

    public void updateTask(int id,String Task){
        ContentValues cv = new ContentValues();
        cv.put(TASK,Task);
        db.update(TODO_TABLE,cv,ID+"=?",new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE,ID+"=?",new String[] {String.valueOf(id)});
    }
}
