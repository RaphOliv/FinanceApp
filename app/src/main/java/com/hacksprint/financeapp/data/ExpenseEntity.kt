package com.hacksprint.financeapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.hacksprint.financeapp.data.CategoryEntity

@Entity(
    // Define esta classe como uma entidade do banco de dados Room
    foreignKeys = [
        // Define uma chave estrangeira para a entidade de categoria
        ForeignKey(
            entity = CategoryEntity::class, // Define a classe de entidade de categoria como a entidade pai
            parentColumns = ["key"], // Define a coluna na entidade pai que é referenciada
            childColumns = ["category"], // Define a coluna nesta entidade que é referenciada
        )
    ]
)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, // Chave primária que é gerada automaticamente
    val amount: Double, // Representa o valor da despesa
    val category: String, // Representa a categoria da despesa
    val description: String, // Representa a descrição da despesa
    val date: Long // Representa a data da despesa em milissegundos
)
