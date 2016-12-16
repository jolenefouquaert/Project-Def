/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ibcn.gso.project.components;

import javafx.scene.image.Image;
import org.ibcn.gso.utils.entitysystemframework.api.Component;

/**
 *
 * @author Jolene
 */
public class TextureComponent implements Component{
    
   protected  Image image;

    public TextureComponent(String string) {

      this.image = new Image(string);
       

    }

    public Image getImage() {
        return image;
    }
    
 
    
}
