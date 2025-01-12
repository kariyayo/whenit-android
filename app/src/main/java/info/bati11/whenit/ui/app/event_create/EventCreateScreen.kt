package info.bati11.whenit.ui.app.event_create

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import info.bati11.whenit.ui.ValidationError
import info.bati11.whenit.ui.theme.WhenitTheme

@Composable
fun EventCreateScreenRoute(
    navController: NavController,
    onClickDateField: (FocusState, EventCreateViewModel) -> Unit,
    viewModel: EventCreateViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isCompleted) {
        navController.navigate(EventCreateFragmentDirections.actionEventCreateFragmentToEventFragment())
    }
    EventCreateScreen(
        uiState = uiState,
        onClickDateField = { focusState ->
            onClickDateField(focusState, viewModel)
        },
        onSave = viewModel::onSaveClicked,
        onClose = {
            navController.navigate(EventCreateFragmentDirections.actionEventCreateFragmentToEventFragment())
        }
    )
}

@Composable
fun EventCreateScreen(
    uiState: EventCreateViewModel.UiState,
    onClickDateField: (FocusState) -> Unit,
    onSave: (titleText: String, dateInMillis: Long?) -> Unit,
    onClose: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        var titleText by remember { mutableStateOf("") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onClose,
            ) {
                Icon(Icons.Rounded.Close, contentDescription = "")
            }
            Button(
                onClick = { onSave(titleText, uiState.inputtedDateTimeInMilli) },
            ) {
                Text("SAVE")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = titleText,
            onValueChange = { titleText = it },
            label = { Text("title") },
            modifier = Modifier.fillMaxWidth(),
        )
        if (uiState.formTitleError == ValidationError.Required) {
            Text("Required")
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = uiState.inputtedDateString,
            onValueChange = {},
            label = { Text("date") },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged(onClickDateField)
                .focusable(),
        )
        if (uiState.formDateError == ValidationError.Required) {
            Text("Required")
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewEventCreateScreen() {
    WhenitTheme {
        Surface {
            EventCreateScreen(
                uiState = EventCreateViewModel.UiState(),
                onClickDateField = {},
                onSave = { _, _ -> },
                onClose = {},
            )
        }
    }
}
