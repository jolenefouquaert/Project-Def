package org.ibcn.gso.utils.entitysystemframework.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.ibcn.gso.utils.entitysystemframework.api.Component;
import org.ibcn.gso.utils.entitysystemframework.api.Engine;
import org.ibcn.gso.utils.entitysystemframework.api.Entity;
import org.ibcn.gso.utils.entitysystemframework.api.EntitySystem;
import org.ibcn.gso.utils.entitysystemframework.ioc.Container;
import org.ibcn.gso.utils.entitysystemframework.ioc.ContainerImpl;
import org.ibcn.gso.utils.entitysystemframework.ioc.ManagedInstance;
import org.ibcn.gso.utils.entitysystemframework.util.TaskQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IoCEngine implements Engine {

    private static final Logger LOGGER = LoggerFactory.getLogger(IoCEngine.class);

    private final Set<Entity> entities = new HashSet<>();

    private final Container container = new ContainerImpl();

    private final Set<ManagedInstance<? extends EntitySystem>> systems = new LinkedHashSet<>();
    private final TaskQueue doBefore = new TaskQueue();

    public IoCEngine() {
        //Makes sure that this Engine implementation can also be injected.
        container.bind(this, Engine.class);
    }

    @Override
    public void add(Entity entity) {
        entities.add(entity);
    }

    @Override
    public void remove(Entity entity) {
        entities.remove(entity);
    }

    @Override
    public Optional<Entity> getEntityWithComponents(
            Class<? extends Component>... componentClasses) {
        return getEntitiesWithComponents(componentClasses).stream().findAny();
    }

    @Override
    public Collection<Entity> getEntitiesWithComponents(
            Class<? extends Component>... componentClasses) {
        return entities
                .stream()
                .filter(e -> Arrays.stream(componentClasses).allMatch(
                        c -> e.has(c))).collect(Collectors.toSet());
    }

    @Override
    public Collection<Entity> getAllEntities() {
        return Collections.unmodifiableCollection(entities);
    }

    @Override
    public <T extends EntitySystem> T registerSystem(Class<T> systemClass)
            throws Exception {
        ManagedInstance<T> instance = container.instantiate(systemClass);
        systems.add(instance);
        return instance.getInstance();
    }

    @Override
    public void update() {
        doBefore.execute();
        systems.stream()
                .filter(mi -> mi.isResolved()).map(mi -> mi.getInstance()).forEach(this::safelyUpdate);
    }

    private void safelyUpdate(EntitySystem activeSystem) {
        try {
            activeSystem.update();
        } catch (Exception e) {
            LOGGER.warn("Could not execute "
                    + activeSystem.getClass().getSimpleName()
                    + " due to exception "
                    + e.toString());
            LOGGER.debug("Full error:", e);
        }
    }

    @Override
    public void unregisterSystem(EntitySystem system) {
        Optional<ManagedInstance<? extends EntitySystem>> instance = systems
                .stream().filter(mi -> mi.getInstance().equals(system))
                .findAny();
        instance.ifPresent(mi -> doBefore.queue(() -> systems.remove(mi)));
    }

    /**
     * Returns a reference to the IoC container that can be used to bind
     * injectable object instances.
     *
     * @return a Container instance
     */
    public Container getContainer() {
        return container;
    }

    @Override
    public <T extends EntitySystem> Optional<T> getSystem(Class<T> systemClass) {
        Optional<ManagedInstance<? extends EntitySystem>> result = systems
                .stream()
                .filter(mi -> mi.getInstance().getClass()
                        .isAssignableFrom(systemClass)).findAny();
        if (result.isPresent()) {
            return Optional.of((T) result.get().getInstance());
        } else {
            return Optional.empty();
        }
    }
}
