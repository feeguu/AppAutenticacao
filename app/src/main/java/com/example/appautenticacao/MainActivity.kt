package com.example.appautenticacao

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appautenticacao.ui.pages.Home
import com.example.appautenticacao.ui.pages.Login
import com.example.appautenticacao.ui.pages.Register
import com.example.appautenticacao.ui.theme.AppAutenticacaoTheme
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.serialization.Serializable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = Firebase.auth

        enableEdgeToEdge()
        setContent {
            AppAutenticacaoTheme {
                App(auth)
            }
        }
    }
}

@Serializable
object HomeScreen

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Preview(showBackground = true)
@Composable
fun App(auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    val navController = rememberNavController()
    val start = auth.currentUser?.let { HomeScreen } ?: LoginScreen
    AppAutenticacaoTheme {

        NavHost(navController = navController, startDestination = start) {
            composable<LoginScreen> {
                Login(onLogin = {
                    navController.navigate(HomeScreen)
                }, onRegisterPressed = {
                    navController.navigate(RegisterScreen)
                })
            }
            composable<HomeScreen> {
                Home(onLogout = {
                    navController.navigate(LoginScreen)
                })
            }
            composable<RegisterScreen> {
                Register(
                    onRegister = {
                        navController.navigate(HomeScreen)
                    },
                    onLoginPressed = {
                        navController.navigate(LoginScreen)
                    }
                )
            }
        }
    }
}