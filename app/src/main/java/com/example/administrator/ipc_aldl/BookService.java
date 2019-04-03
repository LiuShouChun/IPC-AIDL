package com.example.administrator.ipc_aldl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BookService extends Service {
    public final String TAG = "BookService";

    List<Book> mBooks = new ArrayList<Book>(){};
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"远程连接完成");
        Book Book = new Book();
        Book.setName("android开发IPC");
        Book.setPrice(100);
        mBooks.add(Book);
    }

    IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                Log.e(TAG, "invoking getBooks() method , now the list is : " + mBooks.toString());
                for (Book book : mBooks) {
                    Log.e(TAG, book.toString());
                }
                if (mBooks != null) {
                    return mBooks;
                }
                return new ArrayList<>();
            }
        }

        @Override
        public Book getBook() throws RemoteException {
            return null;
        }

        @Override
        public int getBookCount() throws RemoteException {
            return mBooks.size();
        }

        @Override
        public void setBookPrice(Book book, int price) throws RemoteException {
            book.setPrice(price);
        }

        @Override
        public void setBookName(Book book, String name) throws RemoteException {
            book.setName(name);
        }

        @Override
        public void addBookIn(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.e(TAG, "Book is null in In");
                    book = new Book();
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                book.setPrice(999999);
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
            }

        }

        @Override
        public void addBookOut(Book book) throws RemoteException {

        }

        @Override
        public void addBookInout(Book book) throws RemoteException {

        }


    };
}