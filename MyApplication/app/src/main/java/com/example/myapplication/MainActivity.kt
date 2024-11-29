import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val funcionario = Funcionario("João")
    private val listaHorarios = mutableListOf<String>() // Lista para exibir horários disponíveis
    private lateinit var adapter: ArrayAdapter<String> // Adapter para conectar à ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referências do layout
        val calendarView: CalendarView = findViewById(R.id.calendarView)
        val btnAdicionarHorario: Button = findViewById(R.id.btnAdicionarHorario)
        val listViewDisponibilidades: ListView = findViewById(R.id.listViewDisponibilidades)

        // Configuração da lista
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaHorarios)
        listViewDisponibilidades.adapter = adapter

        var selectedDate: String = SimpleDateFormat("dd/MM/yyyy").format(Date()) // Data inicial

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
        }

        btnAdicionarHorario.setOnClickListener {
            TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    val horario = String.format("%02d:%02d", hourOfDay, minute)
                    val disponibilidade = "$selectedDate às $horario"

                    funcionario.adicionarDisponibilidade(selectedDate, horario)
                    listaHorarios.add(disponibilidade)
                    adapter.notifyDataSetChanged() // Atualizar a lista
                },
                9, 0, true
            ).show()
        }
    }
}

data class HorarioDisponivel(val dia: String, val horario: String)

class Funcionario(val nome: String) {
    private val horariosDisponiveis: MutableList<HorarioDisponivel> = mutableListOf()

    fun adicionarDisponibilidade(dia: String, horario: String) {
        if (horariosDisponiveis.any { it.dia == dia && it.horario == horario }) {
            Toast.makeText(null, "Horário já marcado!", Toast.LENGTH_SHORT).show()
        } else {
            horariosDisponiveis.add(HorarioDisponivel(dia, horario))
        }
    }
}
