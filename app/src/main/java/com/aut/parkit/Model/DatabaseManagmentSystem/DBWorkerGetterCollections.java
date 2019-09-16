package com.aut.parkit.Model.DatabaseManagmentSystem;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DBWorkerGetterCollections implements Runnable{
    private FirebaseFirestore mFStore = FirebaseFirestore.getInstance();
    private ThreadLock locker;
    private CollectionReference colRef;
    private List<DocumentSnapshot> data;

    public DBWorkerGetterCollections(String location, ThreadLock locker) {
        colRef = mFStore.collection(location);
        this.locker = locker;
    }

    @Override
    public void run() {
        final Task<QuerySnapshot> task = colRef.get();
        final  QuerySnapshot snap;
        data = new LinkedList<>();

        try {
            snap = Tasks.await(task);

            data = snap.getDocuments();
        } catch (InterruptedException e) {
            Log.e("Error:", e.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        locker.unlockAll();
    }

    public List<DocumentSnapshot> getData() {
        return data;
    }
}
