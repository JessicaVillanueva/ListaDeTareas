package com.example.notas

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notas.TodoItems.TodoDb
import com.example.notas.TodoItems.TodoItemData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_todo_add_edit.*
import java.text.SimpleDateFormat
import java.util.*


class TodoAddEdit : AppCompatActivity() {

    var todoDbController: TodoDb.Controller? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_add_edit)
        todoDbController = TodoDb.Controller(this)
        /* Back button support */
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        /* formulario */
        formSetUp()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    fun formSetUp() {
        when(intent.getStringExtra("TYPE")) {
            "ADD" -> formSetUpAdd()
            "EDIT" -> formSetUpEdit()
        }
        vTodoInputDate.setOnClickListener {
            showDatePicker()
        }
        vTodoInputImage.setOnFocusChangeListener { view, isFocus ->
            val uri = vTodoInputImage.text.toString().trim()
            if(!isFocus && uri.isNotEmpty()) {
                updateImage(uri)
            }
        }
        vTodoBtnCancel.setOnClickListener { onBackPressed() }
    }
    fun formSetUpAdd(){
        setTitle("Agregar tarea")
        vTodoBtnSave.setOnClickListener {
            // Validaciones
            todoDbController!!.insert(
                    TodoItemData(
                            0,
                            vTodoInputTitle.text.toString(),
                            vTodoInputMessage.text.toString(),
                            vTodoInputDate.text.toString(),
                            vTodoInputImage.text.toString()
                    )
            )
            setResult(Activity.RESULT_OK)
            onBackPressed()
        }
    }
    fun formSetUpEdit(){
        setTitle("Editar tarea")
        val id = intent?.getIntExtra("ID", -1)!!
        vTodoInputTitle.setText(intent.getStringExtra("TITLE"))
        vTodoInputMessage.setText(intent.getStringExtra("MESSAGE"))
        vTodoInputDate.setText(intent.getStringExtra("DATE"))
        intent.getStringExtra("IMAGE_URI")?.let {
            vTodoInputImage.setText(it)
            updateImage(it)
        }
        vTodoBtnSave.setOnClickListener {
            // Validaciones
            todoDbController!!.update(
                    TodoItemData(
                            id,
                            vTodoInputTitle.text.toString(),
                            vTodoInputMessage.text.toString(),
                            vTodoInputDate.text.toString(),
                            vTodoInputImage.text.toString()
                    )
            )
            setResult(Activity.RESULT_OK, intent)
            onBackPressed()
        }

    }
    fun showDatePicker() {
        var formatDate = SimpleDateFormat("dd/MM/YYYY", Locale.US)
        val getDate = Calendar.getInstance()
        val datePicker = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            val selectDate = Calendar.getInstance()
            selectDate.set(Calendar.YEAR, year)
            selectDate.set(Calendar.MONTH, month)
            selectDate.set(Calendar.DAY_OF_MONTH, day)
            val date = formatDate.format(selectDate.time)
            vTodoInputDate.setText(date)
        }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }
    fun updateImage(uri:String) {
        Picasso.get().load(uri).into(vImagePreview)
    }
}