import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hacksprint.financeapp.data.ExpenseEntity

@Dao
interface ExpenseDao {
    // Define uma função para obter todas as despesas do banco de dados
    @Query("SELECT * FROM expenseentity")
    fun getAll(): LiveData<List<ExpenseEntity>>

    // Define uma função para inserir uma lista de despesas no banco de dados
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(expenseEntity: List<ExpenseEntity>)

    // Define uma função para inserir uma única despesa no banco de dados
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expenseEntity: ExpenseEntity)

    // Define uma função para atualizar uma despesa existente no banco de dados
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(expenseEntity: ExpenseEntity)

    // Define uma função para excluir uma despesa do banco de dados
    @Delete
    fun delete(expenseEntity: ExpenseEntity)

    // Define uma função para obter todas as despesas de uma determinada categoria do banco de dados
    @Query("Select * FROM expenseentity where category is :categoryName" )
    fun getAllByCategoryName(categoryName: String): List<ExpenseEntity>

    // Define uma função para excluir todas as despesas fornecidas da lista do banco de dados
    @Delete
    fun deleteAll(expenseEntity: List<ExpenseEntity>)
}
