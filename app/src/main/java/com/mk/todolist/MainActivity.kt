package com.mk.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mk.todolist.ui.theme.ToDoListTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.material3.TextField


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

data class Job(
    val id: Int,
    val title: String,
)



@Composable
fun MyApp(modifier: Modifier = Modifier) {
val jobs = remember { mutableStateListOf<Job>() }
var currentId: Int = remember{0}
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)) {
            JobInputBar(
                onAddJob = {
                    title ->
                jobs.add(Job(id = currentId++, title = title))
            })
        JobsList(jobs = jobs,
            modifier = Modifier.weight(1f),
            onJobDelete = {
                    jobToDelete ->
                jobs.remove(jobToDelete)

            }

            )
        }}
    }


@Composable
fun JobInputBar(onAddJob: (String) -> Unit ) {
    var text by remember{mutableStateOf("")}
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
    TextField (value = text, onValueChange = {text = it}, modifier = Modifier.weight(1f), placeholder = {"Insert text..."})
    Button(
        onClick = {
            if(text.isNotBlank())
                onAddJob(text)
            text = ""},
                shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(56.dp))

    {
        Text("Add")
    }
}



}
@Composable
fun JobItem(job : Job, onJobDelete : (Job) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {

        Text(
            text = "${job.title}",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Button(
        onClick = { onJobDelete(job)
            },
                shape = RoundedCornerShape(8.dp)
        ) {
            Text("Remove")
        }

    }
}



@Composable
 fun JobsList(modifier: Modifier = Modifier, jobs: List<Job>, onJobDelete : (Job) -> Unit) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(jobs) { job ->
            JobItem(
                job,
                onJobDelete = onJobDelete

            )
        }

    }
}


@Preview
@Composable
fun MyAppPreview()  {
    ToDoListTheme  {
        MyApp(Modifier.fillMaxSize())
    }
}