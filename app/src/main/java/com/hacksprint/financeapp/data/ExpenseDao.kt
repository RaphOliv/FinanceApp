import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses")
    suspend fun getAll(): List<ExpenseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(expenseEntity: List<ExpenseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expenseEntity: ExpenseEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(expenseEntity: ExpenseEntity)

    @Delete
    suspend fun delete(expenseEntity: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE category = :categoryName")
    suspend fun getAllByCategoryName(categoryName: String): List<ExpenseEntity>

    @Query("DELETE FROM expenses WHERE category = :categoryName")
    suspend fun deleteAllByCategory(categoryName: String)
}
