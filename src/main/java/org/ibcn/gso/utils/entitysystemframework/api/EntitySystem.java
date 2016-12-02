package org.ibcn.gso.utils.entitysystemframework.api;

/**
 * This interface is used to specify an Entity System, implement it to provide a
 * part of the game logic that operates on a set of entities (that have a
 * specified set of components).
 * <p>
 * If you have a TransformComponent that represents the location of an Entity in
 * the game world, you can implement movement as a MovementSystem (EntitySystem
 * implementation) that will apply a movement vector (e.g. stored in
 * PhysicsComponent) to all entities that have both a TransformComponent and
 * PhysicsComponent.
 */
public interface EntitySystem {

    /**
     * This method is called by the Engine implementation for each game loop
     * iteration.
     */
    void update();

}
