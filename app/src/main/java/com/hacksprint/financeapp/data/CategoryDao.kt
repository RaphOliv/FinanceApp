import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hacksprint.financeapp.data.CategoryEntity

@Dao
interface CategoryDao {
    // Define uma função para obter todas as categorias do banco de dados
    @Query("SELECT * FROM categoryentity")
    fun getAll(): LiveData<List<CategoryEntity>>

    // Define uma função para inserir uma nova categoria no banco de dados
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categoryEntity: CategoryEntity)

    // Define uma função para atualizar uma categoria existente no banco de dados
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(categoryEntity: CategoryEntity)

    // Define uma função para excluir uma categoria do banco de dados
    @Delete
    fun delete(categoryEntity: CategoryEntity)
}
