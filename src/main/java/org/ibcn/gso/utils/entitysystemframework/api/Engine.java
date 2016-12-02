package org.ibcn.gso.utils.entitysystemframework.api;

import java.util.Collection;
import java.util.Optional;

/**
 * This interface specifies the Engine of the Entity System Framework.
 *
 * @author wkerckho
 */
public interface Engine {

    /**
     * Adds an entity to the engine.
     *
     * @param entity
     */
    void add(Entity entity);

    /**
     * Removes an entity from the engine.
     *
     * @param entity
     */
    void remove(Entity entity);

    /**
     * Retrieves all entities that own all of the specified components.
     *
     * @param componentClasses The components to match.
     * @return An unmodifiable view of the matching entities.
     */
    Collection<Entity> getEntitiesWithComponents(
            @SuppressWarnings("unchecked") Class<? extends Component>... componentClasses);

    /**
     * Retrieves the first entity that can be found that owns all of the
     * specified components.
     *
     * @param componentClasses The components to match.
     * @return An Optional instance that may contain a matching entity.
     */
    Optional<Entity> getEntityWithComponents(
            @SuppressWarnings("unchecked") Class<? extends Component>... componentClasses);

    /**
     * Retrieves all entities that are currently registered with the engine.
     *
     * @return An unmodifiable view of all the registered entities.
     */
    Collection<Entity> getAllEntities();

    /**
     * Registers a managed System for the specified class. The system will be
     * instantiated by the engine and all its fields with an injection
     * annotation will be resolved before the first time the update method is
     * called.
     * <p>
     * Note: the update methods of the registered systems will be executed
     * according to the registration order.
     *
     * @param <T> A subclass of EntitySystem.
     * @param systemClass The system to register with the Engine.
     * @return The instantiated System class that will be used by the Engine.
     * @throws Exception An Exception is thrown if the System instance could not
     * be loaded.
     */
    <T extends EntitySystem> T registerSystem(Class<T> systemClass)
            throws Exception;

    /**
     * Try to get a registered System of the specified type.
     *
     * @param <T> A subclass of EntitySystem
     * @param systemClass The type of System to find.
     * @return An Optional instance that may contain the found System.
     */
    <T extends EntitySystem> Optional<T> getSystem(Class<T> systemClass);

    /**
     * Unregisters a managed System. Once unregistered, the update method of the
     * specified method will no longer be called.
     *
     * @param system The System instance to unregister.
     */
    void unregisterSystem(EntitySystem system);

    /**
     * Triggers an update loop for this Engine. The update method of all
     * registered Systems will be called as a result (in the order that they
     * were registered).
     */
    void update();

}
