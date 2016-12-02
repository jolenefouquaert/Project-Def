package org.ibcn.gso.project.config;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameConfig {

    private final Map<Class<? extends Config>, Config> config = new HashMap<>();

    public void add(Config config) {
        this.config.put(config.getClass(), config);
    }

    @SuppressWarnings("unchecked")
    public <T extends Config> T get(Class<T> configClass) {
        return (T) this.config.get(configClass);
    }

    public Collection<Config> getAll() {
        return Collections.unmodifiableCollection(config.values());
    }

}
