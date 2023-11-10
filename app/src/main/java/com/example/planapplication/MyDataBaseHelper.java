package com.example.planapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_COMPLETE = "complete";

    private static final String TABLE_NAME2 = "Accommodation";
    private static final String COLUMN_ID_ACCOMMODATION = "id_accommodation";
    private static final String COLUMN_ID_BASIC = "id_basic";
    private static final String COLUMN_ID_CHILD = "id_child";
    MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_COMPLETE + " INTEGER DEFAULT 0);";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_NAME2 +
                " (" + COLUMN_ID_ACCOMMODATION + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ID_BASIC + " INTEGER, "
                + COLUMN_ID_CHILD + " INTEGER);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }
    Cursor readHomeData() {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " NOT IN (SELECT "+ COLUMN_ID_BASIC +" FROM "+TABLE_NAME2+")" +
                " AND " + COLUMN_COMPLETE+ " = 0"+
                " ORDER BY "+COLUMN_DATE+";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor readCompleteHomeData() {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " NOT IN (SELECT "+ COLUMN_ID_BASIC +" FROM "+TABLE_NAME2+")" +
                " AND " + COLUMN_COMPLETE+ " = 1"+
                " ORDER BY "+COLUMN_DATE+";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor readDataInDay(String day) {
        Log.d("readDataInDay",day+"");
        String query = "SELECT * FROM " + TABLE_NAME+ " WHERE "+ COLUMN_ID + " NOT IN (SELECT "+ COLUMN_ID_BASIC +" FROM "+TABLE_NAME2+")" +
                " AND " + COLUMN_DATE+" = '"+day+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        Log.d("readDataInDay","создали курсор и придали null ");
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        Log.d("readDataInDay",cursor.getCount()+"");
        return cursor;
    }
    Cursor readProgressDataBasic() {
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE " + COLUMN_ID + " NOT IN (SELECT "+ COLUMN_ID_CHILD +" FROM "+TABLE_NAME2+");";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    Cursor readProgressDataChild(String id) {
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE " + COLUMN_ID +
                " IN (SELECT "+ COLUMN_ID_CHILD +" FROM "+TABLE_NAME2+" WHERE "+COLUMN_ID_BASIC+" = "+ id +");";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    void addTask(String taskName, String taskDescription, String taskDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, taskName);
        cv.put(COLUMN_DESCRIPTION, taskDescription);
        cv.put(COLUMN_DATE, taskDate);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Добавленно успешно", Toast.LENGTH_SHORT).show();
        }
    }
    void addChildTask(String taskName, String taskDescription, String taskDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, taskName);
        cv.put(COLUMN_DESCRIPTION, taskDescription);
        cv.put(COLUMN_DATE, taskDate);
        Log.d("Add task", "Записал");
        long result = db.insert(TABLE_NAME, null, cv);
        Log.d("Add task", "id added = " +result);
        if (result == -1) {
            Toast.makeText(context, "Ошибка добавления в таблицу задач", Toast.LENGTH_SHORT).show();
        } else {
            cv.clear();
            cv.put(COLUMN_ID_CHILD, (int) result);
            cv.put(COLUMN_ID_BASIC, Integer.parseInt(ProgressFragment.idBasic));
            result = db.insert(TABLE_NAME2, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Ошибка добавления в таблицу распредления", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Добавленно успешно", Toast.LENGTH_SHORT).show();
            }
        }
    }
    void deleteTask(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[] {row_id});
        if (result == -1) {
            Toast.makeText(context, "Ошибка удаления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Успешное удаление", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteBranchTask(int row_id){
        SQLiteDatabase db = getWritableDatabase();
        deleteTaskBranchRecursively(db, row_id);
    }
    private void deleteTaskBranchRecursively(SQLiteDatabase db, int taskId) {
        Log.d("Check id", "id = " +taskId);
        // Удалите задачу по ее id_task
        db.delete("Task", "_id = ?", new String[]{String.valueOf(taskId)});

        // Получите все дочерние id_task из таблицы Accommodation для данного taskId
        Cursor cursor = db.query("Accommodation", new String[]{"id_child"},"id_basic = ?", new String[]{String.valueOf(taskId)}, null, null, null);
        Log.d("Check count", "count = " +cursor.getCount());
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int childTaskId = cursor.getInt(cursor.getColumnIndex("id_child"));
            Log.d("Check count", "count = " +cursor.getCount());
            // Рекурсивный вызов удаления ветви задач для каждой дочерней задачи
            deleteTaskBranchRecursively(db, childTaskId);
        }
        cursor.close();
    }
    void updateTask(String row_id, String taskName, String taskDescription, String taskDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Log.d("Save", row_id + " "+taskName+" "+taskDescription);
        cv.put(COLUMN_NAME, taskName);
        cv.put(COLUMN_DESCRIPTION, taskDescription);
        cv.put(COLUMN_DATE, taskDate);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Ошибка обновления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Обновленно успешно", Toast.LENGTH_SHORT).show();
        }
    }
    void updateTask(String row_id, int complete){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Log.d("Save", row_id + "");
        cv.put(COLUMN_COMPLETE, complete);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Ошибка обновления", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Обновленно успешно", Toast.LENGTH_SHORT).show();
        }
    }
}

