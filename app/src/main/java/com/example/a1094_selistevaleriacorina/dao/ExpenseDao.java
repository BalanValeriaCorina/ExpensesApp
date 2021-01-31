package com.example.a1094_selistevaleriacorina.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a1094_selistevaleriacorina.Expense;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Query("select * from expenses")
    List<Expense> getAll();

    @Insert
    long insert(Expense expense);

    @Update
    int update(Expense expense);

    @Delete
    int delete(Expense expense);

    //==============================================================SORTARE BD
    @Query("select * from expenses order by sum")
    List<Expense> getSorted();

    @Query("select * from expenses order by sum desc")
    List<Expense> getSortedDesc();
}
