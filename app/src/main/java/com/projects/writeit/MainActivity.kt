package com.projects.writeit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.projects.writeit.ui.theme.WriteItTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val shopList = ShopList.products

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WriteItTheme {
                Main(shopList, Modifier)
            }
        }
    }
}


@Composable
fun ProductItem(deletedList: SnapshotStateList<String>, product: String, modifier: Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .clickable {
                deletedList.add(product)
            },
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(modifier.padding(20.dp)) {
            Text(
                text = product,
                fontSize = 20.sp,
                modifier = modifier.padding(start = 10.dp)
            )
        }
    }

}

@Composable
fun DeleteProductItem(shopList: SnapshotStateList<String>, product: String, modifier: Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .clickable {
                shopList.add(product)
            },
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier.padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product,
                fontSize = 20.sp,
                modifier = modifier.padding(start = 10.dp)
            )

            Icon(
                imageVector = Icons.Filled.AddCircle, contentDescription = "",
                modifier = modifier.size(30.dp)
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(list: SnapshotStateList<String>, modifier: Modifier) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var firstText by remember {
        mutableStateOf("")
    }
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    val deletedItem = remember { mutableStateListOf<String>() }

    Scaffold(
        //floatingActionButton
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color.Black,
                contentColor = Color.White,
                onClick = {
                    showBottomSheet = true
                }
            ) {
                Icon(Icons.Filled.Add, "Add floating action button")
            }
        },
        modifier = modifier.fillMaxSize()
        //ModalBottomSheet
    ) { innerPadding ->
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(innerPadding)

                ) {
                    OutlinedTextField(
                        value = firstText,
                        onValueChange = { firstText = it },
                        textStyle = TextStyle(
                            fontStyle = FontStyle.Normal,
                            color = Color.Black
                        )
                    )
                    Spacer(
                        modifier = modifier.padding(20.dp)
                    )
                    Button(
                        onClick = {
                            list.add(firstText)
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                            firstText = ""
                        }
                    ) {
                        Text(
                            text = "OK",
                        )
                    }
                }
            }
        }
        Column(
            modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .fillMaxSize()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier =modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
            ){
                LazyColumn{
                    itemsIndexed(
                        items = list,
                        itemContent = { _, product ->
                            AnimatedVisibility(
                                visible = !deletedItem.contains(product),
                                enter = expandVertically(),
                                exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                            ) {
                                ProductItem(deletedItem, product, modifier)
                            }
                        })
                }
            }
            Spacer(
                modifier = modifier.fillMaxWidth().background(Color.Black).padding(5.dp)
            )
            Column (
                verticalArrangement = Arrangement.Top,
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ){
                LazyRow {
                    items(deletedItem){
                        deleteProduct ->
                            DeleteProductItem(list,deleteProduct,Modifier)
                    }
                }
            }
        }
    }
}


