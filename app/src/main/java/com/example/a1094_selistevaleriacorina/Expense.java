package com.example.a1094_selistevaleriacorina;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity(tableName = "expenses")
public class Expense implements Parcelable {
    @ColumnInfo(name = "sum")
    private double sum;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "time")
    private Time time;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "status")
    private String status;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Expense(double sum, String name, Time time, String category, String status, long id) {
        this.sum = sum;
        this.name = name;
        this.time = time;
        this.category = category;
        this.status = status;
        this.id = id;
    }

    @Ignore
    protected Expense(Parcel in) {
        sum = in.readDouble();
        name = in.readString();
        time = (Time) in.readSerializable();
        category = in.readString();
        status = in.readString();
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "sum=" + sum +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Ignore
    public Expense(double sum, String name, Time time, String category, String status) {
        this.sum = sum;
        this.name = name;
        this.time = time;
        this.category = category;
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(sum);
        dest.writeString(name);
        dest.writeSerializable(time);
        dest.writeString(category);
        dest.writeString(status);
    }


}
