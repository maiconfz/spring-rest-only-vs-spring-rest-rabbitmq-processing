package io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.queue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.github.maiconfz.spring_rest_only_vs_rabbitmq_processing.data.model.JsonData;

@Component
public class JsonDataSavingQueueListenerRepository {
    private final List<JsonDataSavedListener> listeners = new ArrayList<>();

    public void addListener(JsonDataSavedListener listener) {
        listeners.add(listener);
    }

    public void removeListener(JsonDataSavedListener listener) {
        listeners.remove(listener);
    }

    public List<JsonDataSavedListener> getListeners() {
        return new ArrayList<>(listeners);
    }

    public interface JsonDataSavedListener {
        void onJsonDataSaved(JsonData jsonData);
    }
}
