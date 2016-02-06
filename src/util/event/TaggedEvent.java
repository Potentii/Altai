package util.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.event.callback.EventFinishedCallback;
import util.event.callback.TagRegisteredCallback;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Guilherme Reginaldo
 * @since 02/02/2016
 */
public class TaggedEvent {
    @NotNull
    private Set<String> allSteps;
    @NotNull
    private Map<String, Boolean> currentSteps;
    @NotNull
    private Set<String> failedSteps;

    private boolean alreadyFinished;

    private @Nullable EventFinishedCallback eventFinishedCallback;
    private @Nullable TagRegisteredCallback tagRegisteredCallback;



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Constructor:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public TaggedEvent(@NotNull Set<String> allSteps) {
        this();
        this.allSteps = allSteps;
    }
    public TaggedEvent(@NotNull String... tags){
        this();
        Set<String> set = new HashSet<>();
        for (String tag : tags) {
            set.add(tag);
        }
        this.allSteps = set;
    }
    private TaggedEvent(){
        currentSteps = new HashMap<>();
        failedSteps = new HashSet<>();
        alreadyFinished = false;
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Class methods:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    /**
     * Registers a (successful or not) tag for an event.
     * @param tag The event's tag that occurred
     * @param success The status of the tag
     */
    public synchronized void registerStep(@NotNull String tag, @Nullable Boolean success){
        success = (success==null) ? false:success;

        // *Putting the tag on the list:
        currentSteps.put(tag, success);

        // *Adds the tag to a failed steps list if it wasn't successful:
        if (!success) {
            failedSteps.add(tag);
        }

        // *Calling the registering callback:
        if(tagRegisteredCallback != null){
            if(success){
                tagRegisteredCallback.successful(tag);
            } else{
                tagRegisteredCallback.unsuccessful(tag);
            }
        }

        // *Checks if the event is finished:
        if (!alreadyFinished) {
            if (isCompleted()) {
                alreadyFinished = true;
                if(eventFinishedCallback != null) {
                    if (isSuccessful()) {
                        eventFinishedCallback.onSuccess();
                    } else {
                        eventFinishedCallback.onFailure(failedSteps);
                    }
                }
            }
        }
    }


    /**
     * Tells if all tags for an event were registered.
     * @return Returns true if all tags were registered, false otherwise
     */
    public boolean isCompleted(){
        return currentSteps.keySet().containsAll(allSteps);
    }


    /**
     * Tells if all occurred tags ware successful.
     * @return Returns true if all occurred tags ware successful, false otherwise
     */
    public boolean isSuccessful(){
        return !currentSteps.values().contains(false);
    }



    /*
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     *  * Getters and setters:
     *  * ========== * ========== * ========== * ========== * ========== * ========== * ========== * ========== *
     */
    public TaggedEvent setTagRegisteredCallback(@NotNull TagRegisteredCallback tagRegisteredCallback){
        this.tagRegisteredCallback = tagRegisteredCallback;
        return this;
    }

    public TaggedEvent setEventFinishedCallback(@NotNull EventFinishedCallback eventFinishedCallback){
        this.eventFinishedCallback = eventFinishedCallback;
        if(allSteps.size()==0){
            eventFinishedCallback.onSuccess();
        }
        return this;
    }

    @NotNull
    public Map<String, Boolean> getCurrentSteps() {
        return currentSteps;
    }

    @NotNull
    public Set<String> getFailedSteps() {
        return failedSteps;
    }

    @NotNull
    public Set<String> getAllSteps() {
        return allSteps;
    }
}
