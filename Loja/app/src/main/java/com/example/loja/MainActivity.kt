package com.example.loja

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.loja.classes.Carrinho
import com.example.loja.classes.Produto
import com.example.loja.classes.Utilizador

class MainActivity : AppCompatActivity() {

    private lateinit var utilizador: Utilizador
    private lateinit var carrinho: Carrinho

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val teclado = Produto(1, "Teclado Gamer", 150.0, 10)
        val mouse = Produto(2, "Mouse Sem Fio", 100.0, 5)


        utilizador = Utilizador(id = 1, nome = "João", email = "email1@gmail.com", pass = "senha123")
        carrinho = utilizador.carrinho


        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(16, 16, 16, 16)


        val btnAdicionarTeclado = Button(this).apply {
            text = "Adicionar Teclado ao Carrinho"
            setOnClickListener {
                carrinho.adicionarProduto(teclado, 1)
            }
        }

        val btnAdicionarMouse = Button(this).apply {
            text = "Adicionar Mouse ao Carrinho"
            setOnClickListener {
                carrinho.adicionarProduto(mouse, 1)
            }
        }


        val btnListarCarrinho = Button(this).apply {
            text = "Listar Produtos no Carrinho"
            setOnClickListener {
                carrinho.listarProdutos()
            }
        }


        val btnTotalCarrinho = Button(this).apply {
            text = "Ver Total do Carrinho"
            setOnClickListener {
                val total = carrinho.calcularTotal()
                println("Total do Carrinho: R$ $total")
            }
        }


        val btnRemoverTeclado = Button(this).apply {
            text = "Remover Teclado do Carrinho"
            setOnClickListener {
                carrinho.removerProduto(teclado, 1)
            }
        }


        layout.addView(btnAdicionarTeclado)
        layout.addView(btnAdicionarMouse)
        layout.addView(btnListarCarrinho)
        layout.addView(btnTotalCarrinho)
        layout.addView(btnRemoverTeclado)

        // Definindo o layout da Activity
        setContentView(layout)
    }
}
