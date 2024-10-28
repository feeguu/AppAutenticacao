package com.example.appautenticacao.ui.pages

import android.graphics.drawable.shapes.Shape
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

@Composable
fun Login(onLogin: () -> Unit, onRegisterPressed: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()

    val toast =
        Toast.makeText(LocalContext.current, "E-mail/Senha incorreto(s)", Toast.LENGTH_LONG);

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(24.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.None,
                    imeAction = ImeAction.Next,
                    autoCorrectEnabled = false,
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    capitalization = KeyboardCapitalization.None,
                    imeAction = ImeAction.Done,
                    autoCorrectEnabled = false,
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.size(12.dp))

            TextButton(modifier = Modifier.fillMaxWidth(0.8f), onClick = {
                onRegisterPressed()
            }) {
                Text(text = "Ainda não tem conta? Crie uma")
            }

            Spacer(modifier = Modifier.size(8.dp))

            Button(modifier = Modifier.fillMaxWidth(0.8f), onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    toast.show()
                } else {
                    auth.signInWithEmailAndPassword(
                        email, password
                    ).addOnSuccessListener {
                        onLogin()
                    }.addOnFailureListener {
                        toast.show()
                        password = ""
                    }
                }
            }) {
                Text(text = "Entrar")
            }
        }
    }
}