package com.aut.parkit.Model;

public class ThreadLock {

    private boolean waiting = false;

    public void lockThread(){
        this.locker(false);
    }

    private synchronized void locker(boolean bool){
        if (bool){
            waitForThread(bool);
            notifyAll();
        }
        else {
            try {
                waiting = true;
                waitForThread(bool);
                wait();
                waiting = false;
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }
    }

    private synchronized void waitForThread(boolean bool){
        if (bool && !waiting){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            notifyAll();
        }
    }

    public void unlockAll(){
        this.locker(true);
    }
}
