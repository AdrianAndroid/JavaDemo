package com.flannery.javademo.common;

import android.os.IInterface;
import android.os.RemoteException;

import com.flannery.javademo.bean.Person;

import java.util.List;


public interface IPersonManager extends IInterface {

    void addPerson(Person person) throws RemoteException;

    List<Person> getPersonList() throws RemoteException;
}