/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ibcn.gso.project.systems;

import java.util.Collection;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.ibcn.gso.project.components.PlayerComponent;
import org.ibcn.gso.project.components.TextureComponent;
import org.ibcn.gso.project.components.WorldComponent;
import org.ibcn.gso.utils.entitysystemframework.annotations.Inject;
import org.ibcn.gso.utils.entitysystemframework.api.Component;
import org.ibcn.gso.utils.entitysystemframework.api.Engine;
import org.ibcn.gso.utils.entitysystemframework.api.Entity;
import org.ibcn.gso.utils.entitysystemframework.api.EntitySystem;

/**
 *
 * @author Jolene
 */
public class RenderSystem implements EntitySystem {

    @Inject
    private Engine engine;
    @Inject
    private GraphicsContext gc;


    @Override
    public void update() {

        Collection<Entity> entitiesWithComponents = engine.getEntitiesWithComponents(TextureComponent.class);

        //uit lijst de entity's de juiste halen, en entity waarover je gaat 
        //getComponent; 
        for (Entity e : entitiesWithComponents) {

            if (e.has(WorldComponent.class)) {

                TextureComponent texture = e.get(TextureComponent.class);

                gc.drawImage(texture.getImage(), 0, 0);
            }
            
             if (e.has(PlayerComponent.class)) {

                TextureComponent texture = e.get(TextureComponent.class);

                gc.drawImage(texture.getImage(), 300, 300);
            }
            
            

        }

        //checken of entity de worldclasse bevat
        //texturecomponent ophalen de image er uit halen
        // texture component toekennen aan de  gc
        // GraphicsContext gc = canvas.getGraphicsContext2D();
        // gc.drawImage(textureComponent, 0, 0);
        // uit game de scene halen ! en dan die gc er aan toekennen
        //checken of entity de worldclasse bevat 
        //texturecomponent ophalen de image er uit halen
        // texture component toekennen aan de  gc 
        // GraphicsContext gc = canvas.getGraphicsContext2D();  
        // gc.drawImage(textureComponent, 0, 0);
        // uit game de scene halen ! en dan die gc er aan toekennen 
    }

}
