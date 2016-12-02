package org.ibcn.gso.utils.entitysystemframework.ioc;

import java.util.Set;

/**
 * This interface represents the IoC container that supports the managed
 * instances and keeps an inventory of all the injectable instance.
 */
public interface Container {

    /**
     * Instantiates a class based on the state of the Container. The
     * instantiated class will be managed by the container (dependencies will be
     * bound and unbound at runtime when changes to the state of the container
     * occur).
     *
     * @param <T> The type of the instance to manage.
     * @param clazz The class for which an object needs to be instantiated.
     * @return A ManagedInstance that wraps the created instance.
     * @throws java.lang.Exception
     */
    <T> ManagedInstance<T> instantiate(Class<T> clazz) throws Exception;

    /**
     * Returns all instances in the current container that match the specified
     * class type.
     *
     * @param <T> The type of the instance to find.
     * @param type The class that represents the type.
     * @return A set of matching instances.
     */
    <T> Set<T> find(Class<T> type);

    /**
     * Binds any object to the container with the specified associated types.
     * The container will inject this object in managed instances if an
     * appropriate field is encountered.
     *
     * @param <T> The superclass to accept for the types argument
     * @param instance The object-instance to bind.
     * @param types One or more classes to associate with this instance. The
     * class of the object-instance is automatically added.
     */
    <T> void bind(T instance, Class<? super T>... types);

    /**
     * Unbinds a bound instance. It will be unassigned from any field of managed
     * instances it was injected into by the container.
     *
     * @param instance The object-instance to unbind.
     */
    void unbind(Object instance);

}
