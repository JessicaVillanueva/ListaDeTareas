package com.example.notas.helpers

import com.example.notas.TodoItems.TodoDb
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataDbHelper (context:Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val db:SQLiteDatabase
    val values: ContentValues

    companion object {
        private val DATABASE_NAME = "todo_app"
        private val  DATABASE_VERSION = 1
    }

    init {
        db = writableDatabase
        values = ContentValues()
    }

    override fun onCreate(database: SQLiteDatabase?) {
        database!!.execSQL("CREATE TABLE ${TodoDb.TodoTable.TABLE_NAME} " +
                "(" +
                "${TodoDb.TodoTable.ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${TodoDb.TodoTable.TITLE} TEXT NOT NULL, " +
                "${TodoDb.TodoTable.MESSAGE} TEXT NOT NULL, " +
                "${TodoDb.TodoTable.DATE} TEXT NOT NULL, " +
                "${TodoDb.TodoTable.IMAGE_LINK} TEXT NOT NULL" +
                ")")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}