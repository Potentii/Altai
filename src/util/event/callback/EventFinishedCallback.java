package util.event.callback;

import java.util.Set;

/**
 * @author Guilherme Reginaldo
 * @since 02/02/2016
 */
public interface EventFinishedCallback {
    void onSuccess();
    void onFailure(Set<String> failedTags);
}
