package com.example.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.domain.repository.AuthRepository
import com.example.app.domain.repository.UtilizadorRepository
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val utilizadorRepository = UtilizadorRepository() // Seu repositório de utilizadores
    private val authRepository = AuthRepository() // Repositório de autenticação

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val senhaEditText = findViewById<EditText>(R.id.senhaEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        // Verificar se o usuário já está logado
        val currentUser = authRepository.getCurrentUser()
        if (currentUser != null) {
            // Se o usuário já estiver logado, redireciona ou exibe uma mensagem de boas-vindas
            Log.d("MainActivity", "Usuário logado: ${currentUser.email}")
            Toast.makeText(this, "Bem-vindo de volta, ${currentUser.email}", Toast.LENGTH_SHORT).show()

            // Redirecionar para outra tela se necessário (ex: tela principal)
            // startActivity(Intent(this, TelaPrincipalActivity::class.java))
            // finish() // Se não precisar voltar para a MainActivity
        } else {
            // Caso contrário, exibe a tela de login
            Log.d("MainActivity", "Nenhum usuário logado.")
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val senha = senhaEditText.text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                login(email, senha)
            } else {
                Toast.makeText(this, "Por favor, insira email e senha", Toast.LENGTH_SHORT).show()
            }
        }

        logoutButton.setOnClickListener {
            authRepository.logout()
            Toast.makeText(this, "Você foi deslogado", Toast.LENGTH_SHORT).show()
            Log.d("MainActivity", "Usuário deslogado.")
        }
    }

    private fun login(email: String, senha: String) {
        authRepository.login(email, senha) { success, result ->
            if (success) {
                // Login bem-sucedido
                Toast.makeText(this, "Login bem-sucedido", Toast.LENGTH_SHORT).show()
                Log.d("MainActivity", "Usuário logado com sucesso: $result")
                // Aqui você pode direcionar o usuário para outra atividade ou exibir dados
                // startActivity(Intent(this, TelaPrincipalActivity::class.java))
            } else {
                // Falha no login
                Toast.makeText(this, "Falha no login: $result", Toast.LENGTH_LONG).show()
                Log.d("MainActivity", "Erro no login: $result")
            }
        }
    }
}
