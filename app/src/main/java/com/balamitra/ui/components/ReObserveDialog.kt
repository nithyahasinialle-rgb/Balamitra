package com.balamitra.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.balamitra.core.localization.LocalAppStrings
import com.balamitra.core.model.ActivityOutcome

@Composable
fun ReObserveDialog(
    childName: String,
    activityTitle: String,
    onDismiss: () -> Unit,
    onOutcomeSelected: (ActivityOutcome) -> Unit
) {
    val s = LocalAppStrings.current

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = s.reobserveTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "$childName — $activityTitle",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Outcome 1: Independent
                OutcomeCard(
                    emoji = "😊",
                    label = s.outcomeIndependent,
                    subtext = s.outcomeIndependentDesc,
                    containerColor = Color(0xFFE8F8F5),
                    textColor = Color(0xFF0E6251),
                    onClick = { onOutcomeSelected(ActivityOutcome.INDEPENDENT) }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Outcome 2: Needed Help
                OutcomeCard(
                    emoji = "😐",
                    label = s.outcomeNeededHelp,
                    subtext = s.outcomeNeededHelpDesc,
                    containerColor = Color(0xFFFEF9E7),
                    textColor = Color(0xFF7D6608),
                    onClick = { onOutcomeSelected(ActivityOutcome.NEEDED_HELP) }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Outcome 3: Could Not Do
                OutcomeCard(
                    emoji = "😟",
                    label = s.outcomeCouldNotDo,
                    subtext = s.outcomeCouldNotDoDesc,
                    containerColor = Color(0xFFFDEDEC),
                    textColor = Color(0xFF962D22),
                    onClick = { onOutcomeSelected(ActivityOutcome.COULD_NOT_DO) }
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = s.adaptiveLearnMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 11.sp,
                    color = Color(0xFF566573)
                )
            }
        }
    }
}

@Composable
private fun OutcomeCard(
    emoji: String,
    label: String,
    subtext: String,
    containerColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = emoji,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = subtext,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 11.sp,
                    color = textColor.copy(alpha = 0.85f)
                )
            }
        }
    }
}
