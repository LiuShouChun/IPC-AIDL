package com.example.administrator.ipc_aldl;
import android.os.Parcel;
import android.os.Parcelable;
 class Book implements Parcelable {
    private String name;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public Book(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    protected Book(Parcel in) {
       name = in.readString();
       price = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    public void readFromParcel(Parcel dest) {
        name = dest.readString();
        price = dest.readInt();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
    }

     @Override
     public String toString() {
         return "Book{" +
                 "name='" + name + '\'' +
                 ", price=" + price +
                 '}';
     }
 }
