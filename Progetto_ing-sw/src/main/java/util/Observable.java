package util;

import Network.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class Observable{
    private boolean changed = false;
    private List<Observer> obs=new ArrayList<>();

    //Construct the object without the observer
    public Observable() {
        obs = new ArrayList<>();
    }

    //Adding observers
    public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.add(o);
        }
    }

    //Deleting observer
    public synchronized void deleteObserver(Observer o) {
        obs.remove(o);
    }

    //Notify observers if the object is changed
    public void notifyObservers(Message message) {
        /*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
        Object[] arrLocal;

        synchronized (this) {
            /* We don't want the Observer doing callbacks into
             * arbitrary code while holding its own Monitor.
             * The code where we extract each Observable from
             * the Vector and store the state of the Observer
             * needs synchronization, but notifying observers
             * does not (should not).  The worst result of any
             * potential race-condition here is that:
             * 1) a newly-added Observer will miss a
             *   notification in progress
             * 2) a recently unregistered Observer will be
             *   wrongly notified when it doesn't care
             */
            if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(message);
    }

    //Setting the change
    protected synchronized void setChanged() {
        changed = true;
    }

    protected synchronized void clearChanged() {
        changed = false;
    }

    public synchronized boolean hasChanged() {
        return changed;
    }
}
