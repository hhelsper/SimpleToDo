package com.example.simpletodo

import android.content.Intent
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{

            override fun onItemLongClicked(position: Int) {
                //1. Remove the item from the list
                listOfTasks.removeAt(position)
                //2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }


        }

        val onShortClickListener = object : TaskItemAdapter.OnShortClickListener{

            override fun onItemShortClicked(position: Int) {

                    val REQUEST_CODE = 20
                    // first parameter is the context, second is the class of the activity to launch
                    val i = Intent(this@MainActivity, editTask::class.java)
                    i.putExtra("task", listOfTasks[position])
                    i.putExtra("position", position)
                    startActivityForResult(i, REQUEST_CODE ) // brings up the second activity

            }

        }

        loadItems()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = TaskItemAdapter(listOfTasks, onLongClickListener, onShortClickListener)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //Get a reference to the button
        // and then set an onClickListener
        findViewById<Button>(R.id.button).setOnClickListener{

            //1. Grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()
            //2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            //Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)
            //3. Reset the text field
            inputTextField.setText("")

            saveItems()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // REQUEST_CODE is defined above
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            //1. update the item from the list
            val text = data?.extras?.getString("task")
            val position = data?.extras?.getInt("position", 0)
            if (text != null) {
                if (position != null) {
                    listOfTasks[position] = text
                }
            }

            //2. Notify the adapter that our data set has changed
            adapter.notifyDataSetChanged()

            saveItems()
        }
    }

    //Save the data that the user has inputted
    //Save data by writing and reading from a file

    //get the file we need
    fun getDataFile() : File {

        //every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    //Load the items by reading every line in the data file
    fun loadItems(){
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }

    }
    //save items by writing them into our data file
    fun saveItems(){

        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }
}