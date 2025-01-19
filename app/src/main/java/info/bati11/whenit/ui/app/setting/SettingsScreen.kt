package info.bati11.whenit.ui.app.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import info.bati11.whenit.ui.theme.WhenitTheme

@Composable
fun SettingsRoute(
    navController: NavController,
    viewModel: SettingsViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsScreen(
        uiState = uiState,
        onBackClick = {
            navController.popBackStack()
        },
        changeNotification = viewModel::saveNotification,
        changeNotificationDay = viewModel::saveNotificationDay,
        changeNotificationWeek = viewModel::saveNotificationWeek,
        changeNotificationMonth = viewModel::saveNotificationMonth,
    )
}

@Composable
fun SettingsScreen(
    uiState: SettingsViewModel.UiState,
    onBackClick: () -> Unit,
    changeNotification: (Boolean) -> Unit,
    changeNotificationDay: (Boolean) -> Unit,
    changeNotificationWeek: (Boolean) -> Unit,
    changeNotificationMonth: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Settings")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "back")
                    }
                },
            )
        },
    ) { padding ->
        val textStyle =
            if (uiState.isEnableNotification) {
                MaterialTheme.typography.body1
            } else {
                MaterialTheme.typography.body1.let { textStyle ->
                    textStyle.copy(color = textStyle.color.copy(alpha = ContentAlpha.disabled))
                }
            }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Show notifications")
                Switch(
                    checked = uiState.isEnableNotification,
                    onCheckedChange = {
                        changeNotification(it)
                    },
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("1 day before", style = textStyle)
                Checkbox(
                    enabled = uiState.isEnableNotification,
                    checked = uiState.isEnableNotificationDay,
                    onCheckedChange = changeNotificationDay,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("1 week before", style = textStyle)
                Checkbox(
                    enabled = uiState.isEnableNotification,
                    checked = uiState.isEnableNotificationWeek,
                    onCheckedChange = changeNotificationWeek,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("1 month before", style = textStyle)
                Checkbox(
                    enabled = uiState.isEnableNotification,
                    checked = uiState.isEnableNotificationMonth,
                    onCheckedChange = changeNotificationMonth,
                )
            }
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
            SettingsScreen(
                uiState = SettingsViewModel.UiState(
                    isEnableNotification = true,
                    isEnableNotificationDay = true,
                    isEnableNotificationWeek = false,
                    isEnableNotificationMonth = false,
                ),
                onBackClick = {},
                changeNotification = {},
                changeNotificationDay = {},
                changeNotificationWeek = {},
                changeNotificationMonth = {},
            )
        }
    }
}
