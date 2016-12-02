package org.ibcn.gso.utils.entitysystemframework.ioc;

import java.util.Set;

public interface ManagedInstance<T> {

    /**
     * Returns the instance that is wrapped in this ManagedInstance.
     *
     * @return The instance of the object created using
     * Container.instantiate(...)
     */
    T getInstance();

    /**
     * Notifies this ManagedInstance that a new object has been added to the
     * context. This can be caused by a call to Container.bind(...) or can be
     * triggered by another ManagedInstance that has reached a resolved state.
     *
     * @param instance The object-instance that was added to the context.
     * @param properties The associated classes of the object instance.
     */
    void notifyInstanceAdded(Object instance, Set<Class<?>> properties);

    /**
     * Notifies this ManagedInstance that an object has been removed from the
     * context. This can be caused by a call to Container.unbind(...) or can be
     * triggered by another ManagedInstance that has reached an unresolved state
     *
     * @param instance The object-instance that was removed from the context.
     */
    void notifyInstanceRemoved(Object instance);

    /**
     * Checks the state of the ManagedInstance.
     *
     * @return True if all of the ManagedInstance's dependencies are resolved,
     * false otherwise.
     */
    boolean isResolved();

    /**
     * Destroys the ManagedInstance.
     */
    void destroy();

}
