package com.example.notesapp.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.notesapp.data.Note
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NoteCard(
    note: Note,
    navController: NavController,
    onDelete: () -> Unit,
    onFavoriteToggle: () -> Unit
) {

    val context = LocalContext.current

    ElevatedCard(

        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),

        shape = RoundedCornerShape(20.dp),

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(14.dp)
                .clickable {

                    val encodedTitle =
                        URLEncoder.encode(
                            note.title,
                            StandardCharsets.UTF_8.toString()
                        )

                    val encodedDescription =
                        URLEncoder.encode(
                            note.description,
                            StandardCharsets.UTF_8.toString()
                        )

                    navController.navigate(
                        "detail/${note.id}/$encodedTitle/$encodedDescription"
                    )
                }
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = note.title,
                    fontSize = 22.sp
                )

                Row {

                    IconButton(
                        onClick = {

                            onFavoriteToggle()

                            Toast.makeText(
                                context,

                                if (note.isFavorite)
                                    "Added to Favorites"
                                else
                                    "Removed from Favorites",

                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) {

                        Icon(
                            imageVector =
                                if (note.isFavorite)
                                    Icons.Default.Star
                                else
                                    Icons.Default.StarBorder,

                            contentDescription = "Favorite"
                        )
                    }

                    IconButton(
                        onClick = {

                            onDelete()

                            Toast.makeText(
                                context,
                                "Note Deleted",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = note.description,
                fontSize = 15.sp,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = note.createdAt,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}