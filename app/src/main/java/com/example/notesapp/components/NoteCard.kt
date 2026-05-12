package com.example.notesapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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

    ElevatedCard(

        shape = RoundedCornerShape(24.dp),

        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp
        ),

        colors = CardDefaults.elevatedCardColors(
            containerColor =
                MaterialTheme.colorScheme.surface
        ),

        modifier = Modifier
            .fillMaxWidth()
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

        Column(
            modifier = Modifier.padding(18.dp)
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
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Row {

                    IconButton(
                        onClick = {
                            onFavoriteToggle()
                        }
                    ) {

                        Icon(
                            imageVector =
                                if (note.isFavorite.value)
                                    Icons.Default.Favorite
                                else
                                    Icons.Outlined.FavoriteBorder,

                            contentDescription = "Favorite"
                        )
                    }

                    IconButton(
                        onClick = {
                            onDelete()
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Delete,

                            contentDescription = "Delete"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = note.description,
                fontSize = 15.sp,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = note.createdAt,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}