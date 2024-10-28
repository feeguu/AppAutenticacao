package com.example.appautenticacao.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(onLogout: () -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(text = "Logado como: ${user?.displayName}")
            Text(text = "E-mail: ${user?.email}")
            Button(onClick = {
                auth.signOut()
                onLogout()
            }) {
                Text(text = "Sair")
            }
        }
    }
}