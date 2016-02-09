package util.event;

import util.callback.SimpleResponseCallback;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guilherme Reginaldo
 * @since 08/02/2016
 */
@Deprecated // There is no use for this class right now
public class UntaggedEvent {
    private long numberOfEvents;
    private List<Boolean> eventStatusList;

    private SimpleResponseCallback eventFinishedCallback;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public UntaggedEvent(long numberOfEvents) {
        this.numberOfEvents = numberOfEvents;
        eventStatusList = new ArrayList<>();
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public synchronized void registerEvent(boolean status){
        eventStatusList.add(status);

        if(isCompleted()){
            if(eventFinishedCallback != null) {
                if (isSuccessful()) {
                    eventFinishedCallback.onSuccess();
                } else {
                    eventFinishedCallback.onFailure(new Exception());
                }
            }
        }
    }

    public boolean isCompleted(){
        return eventStatusList.size() == numberOfEvents;
    }

    public boolean isSuccessful(){
        return !eventStatusList.contains(false);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public SimpleResponseCallback getEventFinishedCallback() {
        return eventFinishedCallback;
    }

    public UntaggedEvent setEventFinishedCallback(SimpleResponseCallback eventFinishedCallback) {
        this.eventFinishedCallback = eventFinishedCallback;
        if(numberOfEvents <= 0){
            eventFinishedCallback.onSuccess();
        }
        return this;
    }
}
