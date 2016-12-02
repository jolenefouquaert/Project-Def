package org.ibcn.gso.project.config;

public class ControlsConfig implements Config {

    private String moveUp;
    private String moveDown;
    private String moveLeft;
    private String moveRight;
    private String reload;

    public String getMoveUp() {
        return moveUp;
    }

    public void setMoveUp(String moveUp) {
        this.moveUp = moveUp;
    }

    public String getMoveDown() {
        return moveDown;
    }

    public void setMoveDown(String moveDown) {
        this.moveDown = moveDown;
    }

    public String getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(String moveLeft) {
        this.moveLeft = moveLeft;
    }

    public String getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(String moveRight) {
        this.moveRight = moveRight;
    }

    public String getReload() {
        return reload;
    }

    public void setReload(String reload) {
        this.reload = reload;
    }

}
