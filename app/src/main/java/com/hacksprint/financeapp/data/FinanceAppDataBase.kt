import com.hacksprint.financeapp.data.ExpenseEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import com.hacksprint.financeapp.data.CategoryEntity

// Definição da classe FinanceAppDataBase, que é uma subclasse de RoomDatabase
@Database(entities = [CategoryEntity::class, ExpenseEntity::class], version = 1) // Especifica as entidades e a versão do banco de dados
abstract class FinanceAppDataBase : RoomDatabase(){

    // Métodos abstratos para acessar os DAOs (Data Access Objects)
    abstract fun getCategoryDao(): CategoryDao // Retorna um objeto CategoryDao para acessar as operações do banco de dados relacionadas às categorias
    abstract fun getExpenseDao(): ExpenseDao // Retorna um objeto ExpenseDao para acessar as operações do banco de dados relacionadas às despesas

}
