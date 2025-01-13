package info.bati11.whenit.ui.app.event_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import info.bati11.whenit.R
import info.bati11.whenit.domain.Event
import info.bati11.whenit.ui.theme.WhenitTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun EventListScreenRoute(
    onSettingsMenuClick: () -> Unit,
    onLicensesMenuClick: () -> Unit,
    navController: NavController,
    viewModel: EventListViewModel = viewModel(),
) {
    val events = viewModel.events.collectAsLazyPagingItems()
    EventListScreen(
        events = events,
        onSettingsMenuClick = onSettingsMenuClick,
        onLicensesMenuClick = onLicensesMenuClick,
        onEventCreateClick = {
            navController.navigate(
                EventListFragmentDirections.actionEventFragmentToEventCreateFragment()
            )
        },
        onEventRemoveClick = { event ->
            navController.navigate(
                EventListFragmentDirections.actionEventFragmentToEventMenuBottomSheetDialog(
                    event
                )
            )
        },
    )
}

@Composable
fun EventListScreen(
    events: LazyPagingItems<Event>,
    onSettingsMenuClick: () -> Unit,
    onLicensesMenuClick: () -> Unit,
    onEventCreateClick: () -> Unit,
    onEventRemoveClick: (Event) -> Unit,
    modifier: Modifier = Modifier,
    now: Long = System.currentTimeMillis(),
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Whenit", fontFamily = FontFamily(Font(R.font.corben_bold)))
                },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.primaryVariant,
                actions = {
                    ActionsMenu(onSettingsMenuClick, onLicensesMenuClick)
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = lazyListState.isScrollingUp(),
                enter = slideInVertically(initialOffsetY = { fullHeight -> 2 * fullHeight }),
                exit = slideOutVertically(targetOffsetY = { fullHeight -> 2 * fullHeight }),
            ) {
                FloatingActionButton(onClick = onEventCreateClick) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                }
            }
        },
    ) { innerPadding ->
        if (events.itemCount == 0) {
            EmptyText()
        } else {
            EventList(
                events = events,
                onEventRemoveClick = onEventRemoveClick,
                modifier = modifier.padding(innerPadding),
                now = now,
                lazyListState = lazyListState,
            )
        }
    }
}

@Composable
fun ActionsMenu(
    onSettingsMenuClick: () -> Unit,
    onLicensesMenuClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = null,
                tint = MaterialTheme.colors.primaryVariant,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = onSettingsMenuClick) {
                Text("Settings")
            }
            DropdownMenuItem(onClick = onLicensesMenuClick) {
                Text("Licenses")
            }
        }
    }
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Composable
fun EmptyText() {
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Let's write down when it happened.",
            style = MaterialTheme.typography.h6.copy(fontFamily = FontFamily.SansSerif),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun EventList(
    events: LazyPagingItems<Event>,
    onEventRemoveClick: (Event) -> Unit,
    modifier: Modifier = Modifier,
    now: Long = System.currentTimeMillis(),
    lazyListState: LazyListState = rememberLazyListState(),
) {
    Box(modifier = modifier) {
        LazyColumn(
            state = lazyListState,
        ) {
            items(count = events.itemCount) {
                val event = events[it]
                if (event != null) {
                    EventListItem(event, now, onEventRemoveClick)
                }
            }
        }
    }
}

@Composable
fun EventListItem(
    event: Event,
    now: Long,
    onRemoveClick: (Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Card {
            Column(
                modifier = modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth(),
            ) {
                Row {
                    val baseModifier = modifier.alignByBaseline()
                    Text(
                        text = event.years(now).toString(),
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = baseModifier,
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = "years",
                        modifier = baseModifier,
                    )
                    Spacer(Modifier.width(24.dp))
                    Text(
                        text = event.daysOfYears(now).toString(),
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = baseModifier,
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = "days",
                        modifier = baseModifier,
                    )
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                ) {
                    Row {
                        Text("from: ")
                        Text(
                            text = event.title + " ( ${event.year}-${event.month}-${event.dayOfMonth} )",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.primaryVariant,
                        )
                    }
                    Box {
                        IconButton(
                            onClick = { onRemoveClick(event) },
                            modifier = modifier.padding(0.dp)
                        ) {
                            Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewEventListScreen() {
    WhenitTheme {
        Surface {
            EventListScreen(
                events = flowOf(
                    PagingData.from(
                        listOf(
                            Event(id = 1, title = "foo", year = 2022, month = 2, dayOfMonth = 12),
                            Event(id = 2, title = "bar", year = 2022, month = 4, dayOfMonth = 13),
                        )
                    )
                ).collectAsLazyPagingItems(),
                onSettingsMenuClick = {},
                onLicensesMenuClick = {},
                onEventCreateClick = {},
                onEventRemoveClick = {},
            )
        }
    }
}
