package com.example.notas

import com.example.notas.TodoItems.TodoDb
import com.example.notas.TodoItems.TodoItemData
import com.example.notas.TodoItems.TodoListAdapter
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notas.helpers.ActivitiesHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var todoItems:MutableList<TodoItemData> = ArrayList()
    var adapter:TodoListAdapter? = null
    var todoDbController:TodoDb.Controller? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoDbController = TodoDb.Controller(this)
        this.setTitle("Lista de tareas")
        vAdd.setOnClickListener {
            startActivityForResult(ActivitiesHelper().openAddTodo(this), ActivitiesHelper().OPEN_ADD_TODO_RID)
        }
        /* Obtener información*/
        getTodos()
        initTodoRecycler()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) { // Si no falla
            getTodos()
            initTodoRecycler()
            adapter?.notifyDataSetChanged()
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            // Si algo falla
        }
    }

    fun clickDelete(item:TodoItemData) {
        todoDbController!!.delete(item)
        getTodos()
        initTodoRecycler()

    }

    fun clickEdit(item:TodoItemData) {
        startActivityForResult(ActivitiesHelper().openEditTodo(this, item), ActivitiesHelper().OPEN_EDIT_TODO_RID)
    }

    /* SetUp fuctions */
    fun getTodos(){
        todoItems = todoDbController!!.getTodos()
    }

    fun initTodoRecycler() {
        adapter = TodoListAdapter(todoItems, this, ::clickDelete,::clickEdit)
        vRecyclerTodos.layoutManager = LinearLayoutManager(this)
        vRecyclerTodos.adapter = adapter
    }
}