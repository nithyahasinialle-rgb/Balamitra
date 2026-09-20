package com.balamitra.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.balamitra.core.localization.LocalAppStrings
import com.balamitra.core.model.OverrideReason

@Composable
fun WorkerOverrideDialog(
    onDismiss: () -> Unit,
    onConfirmOverride: (OverrideReason, String?) -> Unit
) {
    val s = LocalAppStrings.current
    var selectedReason by remember { mutableStateOf(OverrideReason.NOT_SUITABLE_TODAY) }

    val reasonLabels = mapOf(
        OverrideReason.NOT_SUITABLE_TODAY to s.reasonNotSuitable,
        OverrideReason.CHILD_TIRED to s.reasonChildTired,
        OverrideReason.ALREADY_DID_THIS to s.reasonAlreadyDid,
        OverrideReason.DIFFERENT_PREFERRED to s.reasonDifferentPreferred,
        OverrideReason.OTHER to s.reasonOther
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = s.overrideTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = s.overrideSubtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                OverrideReason.values().forEach { reason ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedReason = reason }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = selectedReason == reason,
                            onClick = { selectedReason = reason }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = reasonLabels[reason] ?: reason.label,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(s.btnCancel)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = { onConfirmOverride(selectedReason, null) },
                        modifier = Modifier.weight(1.2f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(s.btnSaveChange)
                    }
                }
            }
        }
    }
}
