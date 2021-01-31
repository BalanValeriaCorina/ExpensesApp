package com.example.a1094_selistevaleriacorina.asyncTask;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}
