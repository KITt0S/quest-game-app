package com.funnubunny.app.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera2D {
    private final Matrix4f projection;
    private final Matrix4f view;
    private final Matrix4f projectionView;

    private final Vector2f position;

    private final float width;
    private final float height;

    private final float deadZoneX = 120f;
    private final float deadZoneY = 80f;

    private final float smoothness = 4f;

    public Camera2D(float width, float height) {
        this.width = width;
        this.height = height;

        projection = new Matrix4f().ortho2D(-width / 2, width / 2, -height / 2, height / 2);
        view = new Matrix4f();
        projectionView = new Matrix4f();

        position = new Vector2f();
    }

    public void follow(Vector2f target, float deltaTime) {
        float dx = target.x - position.x;
        float dy = target.y - position.y;

        float targetX = position.x;
        float targetY = position.y;

        if (Math.abs(dx) > deadZoneX) {
            targetX += dx - Math.signum(dx) * deadZoneX;
        }

        if (Math.abs(dy) > deadZoneY) {
            targetY += dy - Math.signum(dy) * deadZoneY;
        }

        position.x += (targetX - position.x) * smoothness * deltaTime;
        position.y += (targetY - position.y) * smoothness * deltaTime;
    }

    public void update() {
        view.identity().translate(-position.x, -position.y, 0);
        projectionView.set(projection).mul(view);
    }

    public Matrix4f getProjectionView() {
        return projectionView;
    }

    public Vector2f getPosition() {
        return position;
    }
}
