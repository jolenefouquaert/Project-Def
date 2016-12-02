package org.ibcn.gso.utils.entitysystemframework.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This class is used to represent a game object (the player, an enemy, a UI
 * component) by acting as a container where the Component instances can be
 * attached to.
 */
public class Entity {

    protected final String id;
    protected Map<Class<? extends Component>, Component> components = new HashMap<>();

    /**
     * Creates a new Entity and assigns it a unique id.
     */
    public Entity() {
        id = UUID.randomUUID().toString();
    }

    /**
     * Retrieve the unique id
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Adds a Component to this Entity
     *
     * @param component
     */
    public void add(Component component) {
        components.put(component.getClass(), component);
    }

    /**
     * Removes a Component from this Entity
     *
     * @param component
     */
    public void remove(Component component) {
        components.remove(component.getClass());
    }

    /**
     * Removes the Component from the given Component subclass from this Entity
     *
     * @param componentClass
     */
    public void remove(Class<? extends Component> componentClass) {
        components.remove(componentClass);
    }

    /**
     * Returns the Component from the given class from this Entity.
     *
     * @param <T> The subclass type of the Component
     * @param componentClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Component> T get(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    /**
     * Checks whether the given Component subclass is present on this Entity.
     *
     * @param componentClass
     * @return
     */
    public boolean has(Class<? extends Component> componentClass) {
        return components.containsKey(componentClass);
    }

    /**
     * Returns a list of Component subclasses currently present on this Entity
     *
     * @return
     */
    public Collection<Class<? extends Component>> getComponentClasses() {
        return components.keySet();
    }

    /**
     * Returns all the Components currently attached to this Entity
     *
     * @return
     */
    public Collection<Component> getComponents() {
        return components.values();
    }

    @Override
    public String toString() {
        return "#"
                + id
                + ": "
                + components.values().stream()
                .map(c -> c.getClass().getSimpleName())
                .collect(Collectors.joining("|"));
    }

}
