
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hacksprint.financeapp.data.CategoryEntity
import com.hacksprint.financeapp.data.ExpenseEntity


@Database(entities = [CategoryEntity::class, ExpenseEntity::class], version = 1)
abstract class FinanceAppDataBase : RoomDatabase(){

    abstract fun getCategoryDao(): CategoryDao
    abstract fun getExpenseDao(): ExpenseDao

}
