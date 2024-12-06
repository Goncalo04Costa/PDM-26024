import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RegistoViewModel : ViewModel() {
    var erroMensagem = mutableStateOf("")
        private set

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun registarUsuario(nome: String, email: String, senha: String) {
        if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
            erroMensagem.value = "Preencha todos os campos."
            return
        }

        // Autenticação no Firebase
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Obtém o ID do usuário autenticado
                    val userId = auth.currentUser?.uid ?: ""

                    // Salva o nome do usuário no Firestore
                    val usuario = mapOf(
                        "id" to userId,
                        "nome" to nome,
                        "email" to email
                    )

                    firestore.collection("usuarios")
                        .document(userId)
                        .set(usuario)
                        .addOnSuccessListener {
                            erroMensagem.value = "Registro bem-sucedido!"
                        }
                } else {
                    erroMensagem.value = "Erro no registro: ${task.exception?.message}"
                }
            }
    }
}
