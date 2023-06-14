package com.bancom.myapplication.ui.login


import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bancom.core.theme.deep_purple_500
import com.bancom.core.theme.grey_1000_b
import com.bancom.myapplication.R
import com.bancom.myapplication.ui.listado.ListPersonActivity
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ViewContainer(Modifier.align(Alignment.Center), viewModel)
    }

}


@Composable
fun ViewContainer(modifier: Modifier, viewModel: LoginViewModel) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val isStatusStartActivity: Boolean by viewModel.isStatusStartActivity.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(modifier = modifier) {
            Text(text = "Bienvenido")
            TextHeader(text = "Inicia Sesión")
            Spacer(modifier = Modifier.padding(16.dp))
            EmailField(email) { viewModel.onLoginChanged(it, password) }
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordField(password) { viewModel.onLoginChanged(email, it) }
            Spacer(modifier = Modifier.padding(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnable) {
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))
            ButtonGmail()
        }
    }

    if (isStatusStartActivity) {
        val context = LocalContext.current
        val intent = Intent(context, ListPersonActivity::class.java)
        intent.putExtra("key", email)
        context.startActivity(intent)
    }
}


@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },

        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(deep_purple_500)
    ) {
        Text(
            text = "Ingresar",
            color = Color.White,
            fontSize = 20.sp
        )
    }
}


@Composable
fun TextHeader(text: String) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold
    )
}


@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {
    //var email by remember { mutableStateOf("") }
    TextField(
        value = email, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(textColor = grey_1000_b)
    )
}


@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    /*
    TextField(
        value = password, onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(textColor = grey_1000_b),
    )*/

    TextField(
        value = password, onValueChange = { onTextFieldChanged(it) },
        placeholder = { Text("Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            textColor = Color.Black
        ),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        }
    )
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "¿Olvidaste tu contraseña?",
        modifier = modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF3F51B5)
    )
}

@Composable
fun ButtonGmail() {
    Row(
        modifier = modifierRedSocial,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Image(
            painter = painterResource(id = R.drawable.gmail),
            contentDescription = stringResource(id = R.string.loginGoogle), modifier = Modifier
                .width(30.dp)
                .height(30.dp)
        )
        Text(
            color = Color.White,
            fontSize = 20.sp,
            text = stringResource(id = R.string.loginGoogle),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
    }
}

val modifierRedSocial = Modifier
    .fillMaxWidth()
    .height(52.dp) // Tamaño fijo en píxeles
    .border(
        1.dp,
        Color.Transparent,
        shape = RoundedCornerShape(8.dp)
    )
    .background(
        color = Color.LightGray,
        shape = RoundedCornerShape(8.dp)
    )
    .padding(8.dp)