package com.funnubunny.app.world;

import com.funnubunny.app.graphics.*;
import com.jogamp.opengl.GL3;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class IslandScene implements Renderable {
    private final List<WorldObject> worldObjects;

    @Setter
    private RenderContext context;

    private boolean lighthouseOn = false;

    public IslandScene(Mesh quadMesh) {
        worldObjects = new ArrayList<>();
        createWorld(quadMesh);
    }

    private void createWorld(Mesh mesh) {
        WorldObject islandGround = WorldObject
                .builder()
                .mesh(mesh)
                .x(0)
                .y(0)
                .width(900)
                .height(900)
                .r(0.12f)
                .g(0.18f)
                .b(0.12f)
                .affectedByLight(true)
                .build();

        WorldObject dock = WorldObject
                .builder()
                .mesh(mesh)
                .x(0)
                .y(-320)
                .width(220)
                .height(50)
                .r(0.35f)
                .g(0.26f)
                .b(0.18f)
                .affectedByLight(false)
                .build();

        WorldObject generatorRuins = WorldObject
                .builder()
                .mesh(mesh)
                .x(0)
                .y(40)
                .width(150)
                .height(120)
                .r(0.28f)
                .g(0.28f)
                .b(0.3f)
                .affectedByLight(true)
                .build();

        WorldObject leftTrees = WorldObject
                .builder()
                .mesh(mesh)
                .x(-260)
                .y(-80)
                .width(300)
                .height(300)
                .r(0.05f)
                .g(0.18f)
                .b(0.05f)
                .affectedByLight(true)
                .build();

        WorldObject rightTrees = WorldObject
                .builder()
                .mesh(mesh)
                .x(240)
                .y(-40)
                .width(300)
                .height(300)
                .r(0.05f)
                .g(0.18f)
                .b(0.05f)
                .affectedByLight(true)
                .build();

        worldObjects.addAll(List.of(islandGround, dock, generatorRuins, leftTrees, rightTrees));
    }

    public void setTreesSprite(Sprite sprite) {
        worldObjects.get(3).setSprite(sprite);
        worldObjects.get(4).setSprite(sprite);
    }

    public void setLighthouseOn(boolean lighthouseOn) {
        this.lighthouseOn = lighthouseOn;
        context.setLighthouseOn(lighthouseOn);
    }

    @Override
    public void render(GL3 gl, ShaderProgram shader, Camera2D camera) {
        for (int i = 0; i < worldObjects.size(); i++) {
            if (i != 3 && i != 4) {
                WorldObject element = worldObjects.get(i);
                element.render(gl, shader, camera, lighthouseOn);
                continue;
            }

            new WorldObjectRenderer().render(gl, worldObjects.get(i), context);
        }
    }
}
