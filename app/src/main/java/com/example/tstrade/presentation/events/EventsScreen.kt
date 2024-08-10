package com.example.tstrade.presentation.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.example.tstrade.domain.entities.EventItem
import com.example.tstrade.domain.entities.User
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun EventsScreen(
    viewModel : EventsViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState()
    Column {
        when {
            state.value.isLoading -> PageLoading()
        }

        EventsScreenContent(
            state.value.data,
            state.value.isShowAddDialog,
            onAddEventButtonClick = { viewModel.changeAddDialogState(true)},
            onLeaveEventButtonClick ={eventItem, user -> viewModel.leaveEvent(eventItem, user)},
            onJoinEventButtonClick = { eventItem, user -> viewModel.joinEvent(eventItem, user) },
            onDismissDialogClick = { viewModel.changeAddDialogState(false)}
        )
    }
}

@Composable
private fun EventsScreenContent(
    events: List<EventItem>,
    isShowDialog:Boolean,
    onAddEventButtonClick: (EventItem) -> Unit,
    onLeaveEventButtonClick: (EventItem, User) -> Unit,
    onJoinEventButtonClick: (EventItem, User) -> Unit,
    onDismissDialogClick: () -> Unit
) {

    var showEventCreateDialog by remember { mutableStateOf(isShowDialog) }

    Scaffold (
//        topBar = Toolbar(),
        floatingActionButton = {
            FloatingButton(onClick = { showEventCreateDialog = true })
        },
        content = { paddingValues ->  
            LazyColumn (
                contentPadding = paddingValues,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.95f)
            ) {
                itemsIndexed(events) { _, item ->
                    EventCard(
                        item = item,
                    )
                }
            }
        }
    )

    if(showEventCreateDialog) {
        CreateEventDialog(dismissDialog = { showEventCreateDialog = false})
    }


}

@Composable
private fun PageLoading() {
    Surface(color = Color.LightGray) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun Toolbar() {

}

@Composable
private fun EventCard(item: EventItem) {
    BoxWithConstraints(modifier = Modifier
        .padding(8.dp)
    ) {

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = item.name,
                modifier = Modifier
                    .padding(4.dp)
            )

            Row {

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(4.dp)

                ) {

                    Text(
                        text = item.description,
                        modifier = Modifier
                            .weight(0.7f)
//                            .align(Alignment.Start)
                    )

                    Text(
                        text = item.author,
                        modifier = Modifier
                            .weight(0.2f)
                    )

                    Text(
                        text = item.date.toString(),
                        modifier = Modifier
                            .weight(0.1f)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(4.dp)
                ) {
                    
                    Text(
                        text = "Participants:",
                        )

                    LazyColumn (
                        modifier = Modifier
                            .height(this@BoxWithConstraints.maxHeight * 0.6f)
                    ) {
                        item.attendants?.let {
                            items(it) { user ->
                                Text(text = user)
                            }
                        }
                    }

//                    if(item.attendants.contains(Firebase.auth.currentUser)) {
//
//                    }
                }

            }
        }
    }
}

@Composable
fun FloatingButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Filled.Create, "Create")
    }
}

@Composable
fun CreateEventDialog(dismissDialog: () -> Unit) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = dismissDialog
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create Event",
                    modifier = Modifier
                        .padding(8.dp),
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )

                OutlinedTextField(
                    value = "Title",
                    modifier = Modifier
                        .padding(8.dp),
                    onValueChange = {},
                    label = { Text("Title") }
                )

                OutlinedTextField(
                    value = "Description",
                    modifier = Modifier
                        .padding(8.dp),
                    onValueChange = {},
                    label = { Text("Description") }
                )

                DatePickerDocked()

                DialWithDialog()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked() {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Date") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialWithDialog() {
    var showTimePicker by remember { mutableStateOf(false) }

    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    var selectedTime = ""

    OutlinedTextField(
        value = selectedTime,
        onValueChange = { },
        label = { Text("Time") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showTimePicker = !showTimePicker }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date"
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    )

    if(showTimePicker) {
        TimePickerDialog(
            onDismiss = { showTimePicker = false },
            onConfirm = { selectedTime = getTime(timePickerState) }
        ) {
            TimePicker(
                state = timePickerState,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun getTime(timePickerState: TimePickerState): String {
    val cal = Calendar.getInstance()

    cal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
    cal.set(Calendar.MINUTE, timePickerState.minute)

    return SimpleDateFormat("HH:mm", Locale.US).format(cal.time)
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}
