package org.ibcn.gso.utils.entitysystemframework.ioc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerImpl implements Container {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContainerImpl.class);

    private final Set<ManagedInstance<?>> managedInstances = new HashSet<>();
    private final Map<Object, Set<Class<?>>> context = new HashMap<>();

    @Override
    public <T> ManagedInstance<T> instantiate(Class<T> clazz) throws Exception {
        ManagedInstance<T> instance = new ManagedInstanceImpl<T>(clazz, this) {

            @Override
            public void destroy() {
                context.remove(this.getInstance());
                managedInstances.remove(this);
            }

            @Override
            protected void onResolved() {
                LOGGER.info("Resolved " + this.instance.getClass());
                //The managed instance is now resolved, so we can bind it to the container allowing it to be injected into other managed instances.
                bind(this.getInstance());
            }

            @Override
            protected void onUnresolved() {
                LOGGER.info("Unresolved " + this.instance.getClass());
                //The managed instance is now unresolved, so we unbind it from the container.
                unbind(this.getInstance());
            }

        };
        managedInstances.add(instance);
        return instance;
    }

    @Override
    public <T> void bind(T instance, Class<? super T>... types) {
        if (!context.containsKey(instance)) {
            Set<Class<?>> classAssociations = getClassAssociations(instance,
                    types);
            context.put(instance, classAssociations);
            managedInstances.forEach(mi -> {
                if (instance != mi.getInstance()) {
                    mi.notifyInstanceAdded(instance, classAssociations);
                }
            });
        } else {
            throw new RuntimeException("IoC context already contains a binding for the given instance! (" + instance + ", " + context.get(instance) + ")");
        }
    }

    private Set<Class<?>> getClassAssociations(Object instance,
            Class<?>... properties) {
        Set<Class<?>> classes = Arrays.stream(properties).collect(
                Collectors.toSet());
        classes.add(instance.getClass());
        return classes;
    }

    @Override
    public void unbind(Object instance) {
        context.remove(instance);
        managedInstances.forEach(mi -> {
            if (instance != mi.getInstance()) {
                mi.notifyInstanceRemoved(instance);
            }
        });
    }

    @Override
    public <T> Set<T> find(Class<T> type) {
        return context
                .entrySet()
                .stream()
                .filter(e -> e.getValue()
                        .contains(type))
                .map(e -> (T) e.getKey()).collect(Collectors.toSet());
    }

}
