package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class editTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_task)

        val taskToEdit = intent.getStringExtra("task")
        val position = intent.getIntExtra("position",0)
        val inputTask = findViewById<EditText>(R.id.editTask)

        inputTask.setText(taskToEdit)

        findViewById<Button>(R.id.button2).setOnClickListener{

            val inputTextField = findViewById<EditText>(R.id.editTask)
            //1. Grab the text the user has inputted from @id/editTask
            val userInputtedTask = inputTextField.text.toString()
            //2. Add the string to our list of tasks: listOfTasks
            val data = Intent()
            // Pass relevant data back as a result
            data.putExtra("task", userInputtedTask)
            data.putExtra("position", position)
            // Activity finished ok, return the data
            setResult(RESULT_OK, data) // set result code and bundle data for response
            finish() // closes the activity, pass data to parent
        }

    }

}