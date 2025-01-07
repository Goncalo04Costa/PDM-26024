package com.example.goncalostore.app.fronted.View

import com.example.goncalostore.app.fronted.ViewModel.LoginViewModel
import com.example.goncalostore.app.navigation.Routes
import com.example.goncalostore.app.ui.colors.DarkBlue
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBarScreen(
    content: @Composable (PaddingValues) -> Unit,
    navController: NavController,
    LoginViewModel: LoginViewModel
) {
    val configuration = LocalConfiguration.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val isLoggedIn = remember { mutableStateOf(auth.currentUser != null) }

    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(
                navController = navController,
                isLoggedIn = isLoggedIn.value,
                onLogout = {
                    LoginViewModel.signOut()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0)
                    }
                }
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open()
                                    else drawerState.close()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir Menu", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = DarkBlue
                    ),
                    modifier = Modifier.statusBarsPadding()
                )
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavController,
    isLoggedIn: Boolean,
    onLogout: () -> Unit
) {
    val scrollState = rememberScrollState()

    ModalDrawerSheet(
        drawerContainerColor = DarkBlue,
        modifier = Modifier.fillMaxWidth(0.7f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            // Feed Item
            NavigationDrawerItem(
                label = { Text("Feed", color = Color.White, fontSize = 18.sp) },
                selected = false,
                onClick = { navController.navigate(Routes.FEED) },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = DarkBlue
                )
            )


            if (isLoggedIn) {
                ProdutosSubmenu(navController)
                CarrinhosSubmenu(navController)
            }

            Spacer(modifier = Modifier.weight(1f))


            IconButton(
                onClick = { if (isLoggedIn) onLogout() else navController.navigate(Routes.LOGIN) },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = if (isLoggedIn) Icons.Outlined.ExitToApp else Icons.Outlined.Home,
                    contentDescription = if (isLoggedIn) "Logout" else "Login",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun ProdutosSubmenu(navController: NavController) {
    var showSubmenu by remember { mutableStateOf(false) }

    NavigationDrawerItem(
        label = { Text("Produtos", color = Color.White, fontSize = 18.sp) },
        selected = showSubmenu,
        onClick = { showSubmenu = !showSubmenu },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = DarkBlue
        )
    )

    if (showSubmenu) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
            NavigationDrawerItem(
                label = { Text("Adicionar Produto", color = Color.White, fontSize = 16.sp) },
                selected = false,
                onClick = {
                    navController.navigate(Routes.ADDPRODUCT) {
                        popUpTo(Routes.FEED) { inclusive = true }
                    }
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = DarkBlue
                )
            )
            NavigationDrawerItem(
                label = { Text("Lista de Produtos", color = Color.White, fontSize = 16.sp) },
                selected = false,
                onClick = {
                    navController.navigate(Routes.FEED) {
                        popUpTo(Routes.FEED) { inclusive = true }
                    }
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = DarkBlue
                )
            )
        }
    }
}

@Composable
fun CarrinhosSubmenu(navController: NavController) {
    var showSubmenu by remember { mutableStateOf(false) }

    NavigationDrawerItem(
        label = { Text("Carrinhos", color = Color.White, fontSize = 18.sp) },
        selected = showSubmenu,
        onClick = { showSubmenu = !showSubmenu },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = DarkBlue
        )
    )

    if (showSubmenu) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
            NavigationDrawerItem(
                label = { Text("Ver Carrinhos", color = Color.White, fontSize = 16.sp) },
                selected = false,
                onClick = {
                    navController.navigate(Routes.LISTCARTS) {
                        popUpTo(Routes.FEED) { inclusive = true }
                    }
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = DarkBlue
                )
            )

        }
    }
}


