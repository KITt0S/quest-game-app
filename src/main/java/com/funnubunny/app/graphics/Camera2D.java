package com.funnubunny.app.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera2D {
    private final Matrix4f projection;
    private final Matrix4f view;
    private final Matrix4f projectionView;

    private final Vector3f position;

    private float zoom;

    public Camera2D(float width, float height) {
        position = new Vector3f(0, 0, 0);
        zoom = 1.0f;
        projection = new Matrix4f();
        view = new Matrix4f();
        projectionView = new Matrix4f();

        setProjection(width, height);
    }

    public void setProjection(float width, float height) {
        projection.ortho2D(-width / 2, width / 2, -height / 2, height / 2);
    }

    public void update() {
        view.identity();
        view.translate(position.x, position.y, 0);
        projectionView.set(projection).mul(view);
    }

    public Matrix4f getProjectionView() {
        return projectionView;
    }

    public void move(float x, float y) {
        position.x += x;
        position.y += y;
    }

    public void setPosition(float x, float y) {
        position.set(x, y, 0);
    }

    public Vector3f getPosition() {
        return position;
    }
}
