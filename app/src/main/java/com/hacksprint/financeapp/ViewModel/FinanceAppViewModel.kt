import com.hacksprint.financeapp.FinanceAppApplication
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hacksprint.financeapp.data.CategoryEntity
import com.hacksprint.financeapp.data.ExpenseEntity

// Definição da classe FinanceAppViewModel, que é uma subclasse de ViewModel
class FinanceAppViewModel(private val categoryDao: CategoryDao, private val expenseDao: ExpenseDao): ViewModel(){

    // LiveData para a lista de categorias
    val categoryListLiveDataLiveData: LiveData<List<CategoryEntity>> = categoryDao.getAll()

    // LiveData para a lista de despesas
    val expenseListLiveData: LiveData<List<ExpenseEntity>> = expenseDao.getAll()

    companion object {
        // Método estático para criar uma instância de FinanceAppViewModel
        fun create(application: Application): FinanceAppViewModel {
            // Obtém a instância do banco de dados do aplicativo
            val dataBaseInstance = (application as FinanceAppApplication).getFinanceAppDatabase()

            // Obtém os DAOs necessários do banco de dados
            val categorydao = dataBaseInstance.getCategoryDao()
            val expensedao = dataBaseInstance.getExpenseDao()

            // Retorna uma nova instância de FinanceAppViewModel com os DAOs
            return FinanceAppViewModel(categorydao, expensedao)
        }
    }
}
