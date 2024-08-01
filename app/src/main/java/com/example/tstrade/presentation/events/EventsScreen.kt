package com.example.tstrade.presentation.events

import android.widget.Toolbar
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.overscroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tstrade.domain.entities.EventItem
import com.example.tstrade.domain.entities.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


@Composable
fun EventsScreen(
    viewModel : EventsViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState()
    Column {
//
//        // add toolbar
//        Toolbar() // TODO: Implement
        when {
            state.value.isLoading -> PageLoading()
            state.value.data.isNotEmpty() -> EventsScreenContent(
                state.value.data,
                state.value.isShowAddDialog,
                onAddEventButtonClick = { viewModel.changeAddDialogState(true)},
                onLeaveEventButtonClick ={eventItem, user -> viewModel.leaveEvent(eventItem, user)},
                onJoinEventButtonClick = { eventItem, user -> viewModel.joinEvent(eventItem, user) },
                onDismissDialogClick = { viewModel.changeAddDialogState(false)}
            )
        }
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
    Scaffold (
//        topBar = Toolbar(),
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