import androidx.room.Database
import androidx.room.RoomDatabase
import com.hacksprint.financeapp.data.CategoryEntity

@Database(entities = [CategoryEntity::class, ExpenseEntity::class], version = 1)
abstract class FinanceAppDataBase : RoomDatabase() {

    abstract fun getCategoryDao(): CategoryDao
    abstract fun getExpenseDao(): ExpenseDao
}
