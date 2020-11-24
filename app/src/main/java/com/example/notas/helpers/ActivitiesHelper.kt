package com.example.notas.helpers

import com.example.notas.TodoItems.TodoItemData
import android.content.Context
import android.content.Intent
import com.example.notas.TodoAddEdit

class ActivitiesHelper {
    val OPEN_ADD_TODO_RID = 0
    val OPEN_EDIT_TODO_RID = 1
    fun openAddTodo(ctx:Context):Intent{
        val intent = Intent(ctx, TodoAddEdit::class.java).apply {
            putExtra("TYPE", "ADD")
        }
        return intent
    }
    fun openEditTodo(ctx: Context, itemData: TodoItemData):Intent{
        val intent = Intent(ctx, TodoAddEdit::class.java).apply {
            putExtra("TYPE", "EDIT")
            putExtra("ID", itemData.id)
            putExtra("TITLE", itemData.title)
            putExtra("MESSAGE", itemData.message)
            putExtra("DATE", itemData.date)
            putExtra("IMAGE_URI", itemData.imageUri)
        }
        return intent
    }
}