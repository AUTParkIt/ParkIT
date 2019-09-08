package com.aut.parkit.Model;

import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;

public class DBWorkerGetter implements Runnable {
    private DocumentSnapshot doc;
    private DocumentReference docRef;
    private FirebaseFirestore mFStore = FirebaseFirestore.getInstance();
    private ThreadLock locker;

    public DBWorkerGetter(String location, ThreadLock locker) {
        docRef = mFStore.document(location);
        //docRef = mFStore.collection("Users").document("Test User");
        this.locker = locker;
    }

    @Override
    public void run() {
        final Task<DocumentSnapshot> task = docRef.get();


        doc = null;
        try {
            doc = Tasks.await(task);
        } catch (ExecutionException e) {
            //e.printStackTrace();
            Log.e("Error:", e.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        locker.unlockAll();
    }

    public DocumentSnapshot getDoc() {
        return doc;
    }
}