package com.funnubunny.app.world;

import com.funnubunny.app.entity.Entity;

public class Lighthouse extends Entity {
    private boolean active = false;

    public Lighthouse() {
        super();
        setSize(100, 280);
        setColor(0.85f, 0.85f, 0.75f);
        transform.setPosition(0, 340);
    }

    public void setActive(boolean active) {
        this.active = active;

        if (active) {
            setColor(1.0f, 0.95f, 0.7f);
        } else {
            setColor(0.85f, 0.85f, 0.75f);
        }
    }

    @Override
    public void update() {

    }
}
