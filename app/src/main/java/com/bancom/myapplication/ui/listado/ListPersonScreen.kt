package com.bancom.myapplication.ui.listado

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bancom.core.theme.deep_purple_400
import com.bancom.domain.model.UserDataEntity
import com.bancom.myapplication.R
import com.bancom.myapplication.ui.listado.result.UsersEventResult


@Composable
fun ViewContainer(viewModel: ListPersonViewModel) {
    Scaffold(topBar = {
        HomeTopBar()
    }) { innerPadding ->
        HomeContent(modifier = Modifier.padding(innerPadding), viewModel)

    }
}

@Composable
private fun HomeContent(modifier: Modifier = Modifier, viewModel: ListPersonViewModel) {
    val state = viewModel.usersObserver.observeAsState(initial = emptyArray<UsersEventResult>())


    LaunchedEffect(key1 = true) {
        viewModel.initGetList()
    }



    when (state.value) {
        is UsersEventResult.GetListUsers -> {
            val resultadoLista = (state.value as UsersEventResult.GetListUsers).usersList
            val listEdit = resultadoLista.subList(0, 8)
            UsersList(usersList = listEdit, modifier = modifier)
        }
    }
}


@Composable
fun UsersList(usersList: MutableList<UserDataEntity>, modifier: Modifier) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(144.dp),
        //columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {

        header {
            Text(
                text = "Users",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight =
                    FontWeight.ExtraBold
                )
            )
        }
        items(usersList.size) { index ->
            val users = usersList[index]

            UsersCardItem(
                users = users
            )
        }
    }
}

@Composable
fun UsersCardItem(
    users: UserDataEntity
) {

    Card(
        shape = RoundedCornerShape(24.dp),
        contentColor = Color.White,
        elevation = 10.dp,
        modifier = Modifier.clearAndSetSemantics {
            contentDescription = users.name
        },
        backgroundColor = deep_purple_400
    ) {
        Box(modifier = Modifier
            .clickable { }
            .fillMaxWidth()
            .aspectRatio(1.4f)) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 24.dp, top = 24.dp)
            ) {
                Text(
                    users.name,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    users.userName,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}


fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@Composable
private fun HomeTopBar() {
    TopAppBar(title = {
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center
        )
    }, navigationIcon = {
        IconButton(onClick = {}) {
            Icon(
                Icons.Filled.ArrowBack, tint = Color.White, contentDescription = "backIcon"
            )
        }
    })
}