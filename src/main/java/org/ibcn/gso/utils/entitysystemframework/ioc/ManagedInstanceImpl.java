package org.ibcn.gso.utils.entitysystemframework.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.ibcn.gso.utils.entitysystemframework.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ManagedInstanceImpl<T> implements ManagedInstance<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagedInstanceImpl.class);

    protected static class Dependency {

        public boolean resolved = false;
        public Field target;
        public Class<?> type;

        public Dependency(Field target) {
            this.target = target;
            if (Collection.class.equals(target.getType())) {
                ParameterizedType listType = (ParameterizedType) target
                        .getGenericType();
                type = (Class<?>) listType.getActualTypeArguments()[0];
            } else {
                type = target.getType();
            }
        }
    }

    protected T instance;
    protected Set<Dependency> dependencies = new HashSet<>();

    public ManagedInstanceImpl(Class<T> clazz, Container container)
            throws Exception {
        instance = clazz.newInstance();
        Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Inject.class))
                .map(Dependency::new)
                .forEach(dependencies::add);
        Iterator<Dependency> iter = dependencies.iterator();
        while (iter.hasNext()) {
            Dependency dep = iter.next();
            Set<?> candidates = container.find(dep.type);
            if (!candidates.isEmpty()) {
                // Set field and add to resolved dependencies
                candidates.forEach(c -> setField(dep.target, c));
                dep.resolved = true;
            }
        }

        if (isResolved()) {
            onResolved();
        }
    }

    @Override
    public T getInstance() {
        return instance;
    }

    @Override
    public void notifyInstanceAdded(Object instance, Set<Class<?>> properties) {
        boolean wasResolved = isResolved();

        dependencies.stream().filter(dep -> properties.contains(dep.type))
                .forEach(dep -> {
                    setField(dep.target, instance);
                    dep.resolved = true;
                });

        if (!wasResolved && isResolved()) {
            onResolved();
        }
    }

    @Override
    public void notifyInstanceRemoved(Object instance) {
        boolean wasResolved = isResolved();

        dependencies.stream().forEach(dep -> {
            dep.resolved = !clearField(dep.target, instance);
        });

        if (wasResolved && !isResolved()) {
            onUnresolved();
        }
    }

    /**
     * Sets the field: Normal fields are set to the injected value. For
     * collection fields, the injected value is added as an entry.
     *
     * @param field
     * @param instance
     */
    private void setField(Field field, Object injectedValue) {
        try {
            field.setAccessible(true);
            if (Collection.class.equals(field.getType())) {
                // Initialize the collection if null
                if (field.get(instance) == null) {
                    field.set(instance, new HashSet());
                }
                ((Collection) field.get(instance)).add(injectedValue);
            } else if (field.get(instance) == null) {
                field.set(instance, injectedValue);
            }
        } catch (Exception e) {
            LOGGER.warn("Error while injecting instance!", e);
        }
    }

    /**
     * Clears the field of the injected value: Normal fields are set to null if
     * the injected value matches the field's value. For collection fields, the
     * injected value is removed.
     *
     * Returns true if the field was invalidated by this operation.
     *
     * @param field
     * @param value
     */
    private boolean clearField(Field field, Object injectedValue) {
        try {
            field.setAccessible(true);
            if (Collection.class.equals(field.getType())) {
                Collection tmp = ((Collection) field.get(instance));
                tmp.remove(injectedValue);
                return tmp.isEmpty();
            } else if (field.get(instance) == injectedValue) {
                field.set(instance, null);
                return true;
            }
        } catch (Exception e) {
            LOGGER.warn("Error while clearing injected instance!", e);
        }
        return false;
    }

    @Override
    public boolean isResolved() {
        return dependencies.stream().allMatch(dep -> dep.resolved);
    }

    protected abstract void onResolved();

    protected abstract void onUnresolved();

}
