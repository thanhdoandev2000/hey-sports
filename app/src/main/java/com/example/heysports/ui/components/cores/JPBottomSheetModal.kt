package com.example.heysports.ui.components.cores

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.heysports.ui.theme.BgColorPage
import com.example.heysports.ui.theme.GreenDark
import com.example.heysports.ui.theme.LightGreenBackground
import com.example.heysports.ui.theme.PrimaryGreen
import com.example.heysports.ui.theme.RedColor
import com.example.heysports.ui.theme.TextPrimary
import com.example.heysports.ui.theme.TextSecondary
import com.example.heysports.ui.theme.size_10dp
import com.example.heysports.ui.theme.size_12dp
import com.example.heysports.ui.theme.size_12sp
import com.example.heysports.ui.theme.size_14dp
import com.example.heysports.ui.theme.size_14sp
import com.example.heysports.ui.theme.size_16dp
import com.example.heysports.ui.theme.size_16sp
import com.example.heysports.ui.theme.size_20dp
import com.example.heysports.ui.theme.size_24dp
import com.example.heysports.ui.theme.size_36dp
import com.example.heysports.ui.theme.size_3dp
import com.example.heysports.ui.theme.size_40dp
import com.example.heysports.ui.theme.size_8dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JPBottomSheetModal(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = BgColorPage,
    showDragHandle: Boolean = false,
    gesturesEnabled: Boolean = false,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = false,
    skipPartiallyExpanded: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(
        start = size_16dp,
        top = size_16dp,
        end = size_16dp,
        bottom = size_8dp
    ),
    spacer: Dp = size_8dp,
    content: @Composable ColumnScope.(dismiss: () -> Unit) -> Unit
) {
    if (! visible) return

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    val scope = rememberCoroutineScope()

    fun dismiss() {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (! sheetState.isVisible) onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = { dismiss() },
        modifier = modifier,
        containerColor = containerColor,
        sheetState = sheetState,
        sheetGesturesEnabled = gesturesEnabled,
        dragHandle = if (showDragHandle) {
            { JPBottomSheetDragHandle() }
        } else null,
        contentWindowInsets = { WindowInsets(0) },
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = dismissOnBackPress,
            shouldDismissOnClickOutside = dismissOnClickOutside
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(spacer)
        ) {
            content(::dismiss)
        }
    }
}

@Composable
private fun JPBottomSheetDragHandle() {
    Box(
        modifier = Modifier
            .padding(vertical = size_12dp)
            .width(size_36dp)
            .height(size_3dp)
            .background(TextSecondary.copy(alpha = 0.35f), RoundedCornerShape(50))
    )
}
