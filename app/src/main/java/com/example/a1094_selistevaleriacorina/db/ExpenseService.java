package com.example.a1094_selistevaleriacorina.db;

import android.content.Context;

import com.example.a1094_selistevaleriacorina.Expense;
import com.example.a1094_selistevaleriacorina.asyncTask.AsyncTaskRunner;
import com.example.a1094_selistevaleriacorina.asyncTask.Callback;
import com.example.a1094_selistevaleriacorina.dao.ExpenseDao;

import java.util.List;
import java.util.concurrent.Callable;

public class ExpenseService {

    private final ExpenseDao expenseDao;
    private final AsyncTaskRunner taskRunner;

    public ExpenseService(Context context) {
        expenseDao = DatabaseManager.getInstance(context).getExpenseDao();
        taskRunner = new AsyncTaskRunner();
    }

    public List<Expense> getAllV2() {
        return expenseDao.getAll();
    }

    public void getAll(Callback<List<Expense>> callback) {
        Callable<List<Expense>> callable = new Callable<List<Expense>>() {
            @Override
            public List<Expense> call() {
                return expenseDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<Expense> callback, final Expense expense) {
        Callable<Expense> callable = new Callable<Expense>() {
            @Override
            public Expense call() {
                if (expense == null) {
                    return null;
                }
                long id = expenseDao.insert(expense);
                if (id == -1) {
                    return null;
                }
                expense.setId(id);
                return expense;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<Expense> callback, final Expense expense) {
        Callable<Expense> callable = new Callable<Expense>() {
            @Override
            public Expense call() {
                if (expense == null) {
                    return null;
                }
                int count = expenseDao.update(expense);
                if (count < 1) {
                    return null;
                }
                return expense;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Expense expense) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (expense == null) {
                    return -1;
                }
                return expenseDao.delete(expense);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
    //======================================================== SORTARE BD
    public void getSorted(Callback<List<Expense>> callback) {
        Callable<List<Expense>> callable = new Callable<List<Expense>>() {
            @Override
            public List<Expense> call() {
                return expenseDao.getSorted();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void getSortedDesc(Callback<List<Expense>> callback) {
        Callable<List<Expense>> callable = new Callable<List<Expense>>() {
            @Override
            public List<Expense> call() {
                return expenseDao.getSortedDesc();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
}
