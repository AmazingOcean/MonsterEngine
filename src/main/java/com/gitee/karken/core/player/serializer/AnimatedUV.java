package com.gitee.karken.core.player.serializer;

import com.gitee.karken.util.vertex.KarkenVertex;
import com.gitee.karken.util.vertex.KarkenVertexSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.Direction;

import java.util.Map;

public interface AnimatedUV {

    static class Quad implements AnimatedUV {


        private Map<Direction, AnimatedUV.Texture> table;

        public Quad(Map<Direction, AnimatedUV.Texture> table) {
            this.table = table;
        }

        public AnimatedUV.Texture getAnimatedUVByDirection(Direction direction) {
            return table.get(direction);
        }

        public Map<Direction, Texture> getTextures() {
            return this.table;
        }

        public Texture getTexture(Direction direction) {
            return this.getTextures().get(direction);
        }

        @Override
        public String toString() {
            return "Quad{" +
                    "table=" + table +
                    '}';
        }
    }


    static class Box implements AnimatedUV {

        private double x;
        private double y;

        public Box(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }


    static class Scale implements AnimatedUV {

        private double x;

        private double y;

        public Scale(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    static class Texture implements AnimatedUV {

        private double x;
        private double y;
        private double width;
        private double height;

        public Texture(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }

        @Override
        public String toString() {
            return "Texture{" +
                    "x=" + x +
                    ", y=" + y +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }
    }


}
