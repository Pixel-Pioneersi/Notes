package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.notesapp.ui.theme.NotesAppTheme
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.TextStyle

@Composable
fun MainScreen() {
    var itemsList by remember { mutableStateOf(emptyList<String>()) }
    var expandedStates by remember { mutableStateOf(emptyList<Boolean>()) }
    var isEditDialogOpen by remember { mutableStateOf(false) }
    var selectedCardIndex by remember { mutableStateOf(-1) }

    val density = LocalDensity.current.density

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background_img),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(itemsList.size) { index ->
                val cardCreationDate = Instant.now()
                val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
                val formattedDate =
                    cardCreationDate.atZone(ZoneId.systemDefault()).format(dateFormatter)

                var cardSizes by remember { mutableStateOf(List(itemsList.size) { 1f }) }

                ElevatedCard(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(110.dp)
                        .clickable {
                            selectedCardIndex = index
                            isEditDialogOpen = true
                        }
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = itemsList[index],
                            modifier = Modifier.align(Alignment.TopCenter),
                            style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold)
                        )

                        Text(
                            text = if (expandedStates[index]) itemsList[index] else itemsList[index].take(
                                50
                            ) + " ...",
                            modifier = Modifier
                                .padding(top = 15.dp, start = 0.dp)
                                .scale(0.8f),
                            style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize()
                            .wrapContentSize(Alignment.BottomEnd)
                            .scale(0.5f)
                    ) {
                        Text(
                            text = "$formattedDate",
                            style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Normal)
                        )
                    }
                }
            }
        }

        if (isEditDialogOpen) {
            EditDialog(
                text = itemsList[selectedCardIndex],
                onDismiss = {
                    isEditDialogOpen = false
                },
                onConfirm = { newText ->
                    itemsList = itemsList.toMutableList().also {
                        it[selectedCardIndex] = newText
                    }
                    isEditDialogOpen = false
                }
            )
        }

        SmallFloatingActionButton(
            onClick = {
                itemsList = itemsList + "New Card ${itemsList.size + 1}"
                expandedStates = expandedStates + false
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .safeDrawingPadding(),
            containerColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDialog(text: String, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var editedText by remember { mutableStateOf(text) }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text("Edit Text")
        },
        text = {
            TextField(
                value = editedText,
                onValueChange = {
                    editedText = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onConfirm(editedText)
                    }
                )
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(editedText)
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}

