package com.flannery.javademo.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import com.flannery.javademo.bean.Person;

import java.util.List;

class Proxy implements IPersonManager {
    private static final String DESCRIPTOR = "com.enjoy.binder.common.IPersonManager";

    private IBinder mRemote;

    public Proxy(IBinder mRemote) {
        this.mRemote = mRemote;
    }

    @Override
    public void addPerson(Person person) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (person != null) {
                data.writeInt(1);
                person.writeToParcel(data, 0);
            } else {
                data.writeInt(0);
            }
            Log.e("leo", "Proxy,addPerson: " + Thread.currentThread());
            mRemote.transact(Stub.TRANSACTION_addPerson, data, reply, 0);
        } finally {
            reply.recycle();
            data.recycle();
        }

    }

    @Override
    public List<Person> getPersonList() throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        List<Person> result;
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            mRemote.transact(Stub.TRANSACTION_getPersonList, data, reply, 0);
            reply.readException();
            result = reply.createTypedArrayList(Person.CREATOR);
        } finally {
            reply.recycle();
            data.recycle();
        }
        return result;
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}