package com.gitee.karken.util.vertex;

import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.google.common.collect.Maps;
import net.minecraft.core.Direction;

import java.util.EnumMap;
import java.util.Map;

public class KarkenVertexSet {

    private EnumMap<Type, KarkenVertex> mapping = Maps.newEnumMap(Type.class);

    public KarkenVertexSet(KarkenVector3d origin, KarkenVector3d size, double inflation) {
        for (Type type : Type.values()) {
            this.setQuad(type, origin, size, inflation);
        }
    }

    public void setQuad(Type type, KarkenVertex vertex) {
        this.mapping.put(type, vertex);
    }

    public void setQuad(Type type, KarkenVector3d origin, KarkenVector3d size, double inflation) {
        this.setQuad(type, new KarkenVertex(type.build(origin, size, inflation), null));
    }

    public KarkenVertex getKarkenVertex(Type type) {
        return this.mapping.get(type);
    }

    public Face getFaceByDirection(Direction direction) {
        return switch (direction) {
            case UP -> faceUp();
            case DOWN -> faceDown();
            case EAST -> faceEast();
            case WEST -> faceWest();
            case NORTH -> faceNorth();
            case SOUTH -> faceSouth();
        };
    }


    public Face faceUp() {
        return new Face(Direction.UP, new KarkenVertex[]{
                getKarkenVertex(Type.TOP_LEFT_BACK),
                getKarkenVertex(Type.TOP_LEFT_FRONT),
                getKarkenVertex(Type.TOP_RIGHT_FRONT),
                getKarkenVertex(Type.TOP_RIGHT_BACK)
        });
    }

    public Face faceEast() {
        return new Face(Direction.EAST, new KarkenVertex[]{
                getKarkenVertex(Type.TOP_RIGHT_FRONT),
                getKarkenVertex(Type.BOTTOM_RIGHT_FRONT),
                getKarkenVertex(Type.BOTTOM_LEFT_FRONT),
                getKarkenVertex(Type.TOP_LEFT_FRONT)
        });
    }

    public Face faceWest() {
        return new Face(Direction.WEST, new KarkenVertex[]{
                getKarkenVertex(Type.TOP_LEFT_BACK),
                getKarkenVertex(Type.BOTTOM_LEFT_BACK),
                getKarkenVertex(Type.BOTTOM_RIGHT_BACK),
                getKarkenVertex(Type.TOP_RIGHT_BACK)
        });
    }

    public Face faceNorth() {
        return new Face(Direction.NORTH, new KarkenVertex[]{
                getKarkenVertex(Type.TOP_RIGHT_BACK),
                getKarkenVertex(Type.TOP_LEFT_BACK),
                getKarkenVertex(Type.BOTTOM_LEFT_BACK),
                getKarkenVertex(Type.BOTTOM_RIGHT_BACK)
        });
    }

    public Face faceSouth() {
        return new Face(Direction.SOUTH, new KarkenVertex[]{
                getKarkenVertex(Type.TOP_LEFT_FRONT),
                getKarkenVertex(Type.TOP_RIGHT_FRONT),
                getKarkenVertex(Type.BOTTOM_RIGHT_FRONT),
                getKarkenVertex(Type.BOTTOM_LEFT_FRONT)
        });
    }

    public Face faceDown() {
        return new Face(Direction.DOWN, new KarkenVertex[]{
                getKarkenVertex(Type.BOTTOM_LEFT_FRONT),
                getKarkenVertex(Type.BOTTOM_RIGHT_FRONT),
                getKarkenVertex(Type.BOTTOM_RIGHT_BACK),
                getKarkenVertex(Type.BOTTOM_LEFT_BACK)
        });
    }


    public static class Face {

        private Direction direction;

        private KarkenVertex[] vertices;

        public Face(Direction direction, KarkenVertex[] vertices) {
            this.direction = direction;
            this.vertices = vertices;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public KarkenVertex[] getVertices() {
            return vertices;
        }

        public void setVertices(KarkenVertex[] vertices) {
            this.vertices = vertices;
        }
    }

    public enum Type {

        /**
         * 后 下左
         * 从原点向左、向下、向后各移动 inflation 的距离。
         */
        BOTTOM_LEFT_BACK {
            @Override
            KarkenVector3d build(KarkenVector3d origin, KarkenVector3d size, double inflation) {
                return new KarkenVector3d(
                        origin.getX() - inflation,
                        origin.getY() - inflation,
                        origin.getZ() - inflation
                );
            }
        },
        /**
         * 后 下右
         * 从原点向左、向下移动 inflation 的距离，向后移动 vertexSize.z 加上 inflation 的距离。
         */
        BOTTOM_RIGHT_BACK {
            @Override
            KarkenVector3d build(KarkenVector3d origin, KarkenVector3d size, double inflation) {
                return new KarkenVector3d(
                        origin.getX() - inflation,
                        origin.getY() - inflation,
                        origin.getZ() + size.getZ() + inflation
                );
            }
        },
        /**
         * 前 下左
         * 从原点向右移动 vertexSize.x 加上 inflation 的距离，向下移动 inflation 的距离，向后移动 inflation 的距离。
         */
        BOTTOM_LEFT_FRONT {
            @Override
            KarkenVector3d build(KarkenVector3d origin, KarkenVector3d size, double inflation) {
                return new KarkenVector3d(
                        origin.getX() + size.getX() + inflation,
                        origin.getY() + inflation,
                        origin.getZ() + inflation
                );
            }
        },
        //
        /**
         * 前 下右
         * 从原点向右移动 vertexSize.x 加上 inflation 的距离，向下移动 inflation 的距离，向后移动 vertexSize.z 加上 inflation 的距离。
         */
        BOTTOM_RIGHT_FRONT {
            @Override
            KarkenVector3d build(KarkenVector3d origin, KarkenVector3d size, double inflation) {
                return new KarkenVector3d(
                        origin.getX() + size.getX() + inflation,
                        origin.getY() + inflation,
                        origin.getZ() + size.getZ() + inflation
                );
            }
        },
        /**
         * 后 上左
         * 从原点向左、向后移动 inflation 的距离，向上移动 vertexSize.y 加上 inflation 的距离。
         */
        TOP_LEFT_BACK {
            @Override
            KarkenVector3d build(KarkenVector3d origin, KarkenVector3d size, double inflation) {
                return new KarkenVector3d(
                        origin.getX() + inflation,
                        origin.getY() + size.getY() + inflation,
                        origin.getZ() + inflation
                );
            }
        },
        /**
         * 后 上右
         * 从原点向左移动 inflation 的距离，向上移动 vertexSize.y 加上 inflation 的距离，向后移动 vertexSize.z 加上 inflation 的距离。
         */
        TOP_RIGHT_BACK {
            @Override
            KarkenVector3d build(KarkenVector3d origin, KarkenVector3d size, double inflation) {
                return new KarkenVector3d(
                        origin.getX() + inflation,
                        origin.getY() + size.getY() + inflation,
                        origin.getZ() + size.getZ() + inflation
                );
            }
        },
        /**
         * 前 上左
         * 从原点向右移动 vertexSize.x 加上 inflation 的距离，向上移动 vertexSize.y 加上 inflation 的距离，向后移动 inflation 的距离。
         */
        TOP_LEFT_FRONT {
            @Override
            KarkenVector3d build(KarkenVector3d origin, KarkenVector3d size, double inflation) {
                return new KarkenVector3d(
                        origin.getX() + size.getX() + inflation,
                        origin.getY() + size.getY() + inflation,
                        origin.getZ() + inflation
                );
            }
        },
        /**
         * 前 上右
         * 从原点向右移动 vertexSize.x 加上 inflation 的距离，向上移动 vertexSize.y 加上 inflation 的距离，向后移动 vertexSize.z 加上 inflation 的距离。
         */
        TOP_RIGHT_FRONT {
            @Override
            KarkenVector3d build(KarkenVector3d origin, KarkenVector3d size, double inflation) {
                return new KarkenVector3d(
                        origin.getX() + size.getX() + inflation,
                        origin.getY() + size.getY() + inflation,
                        origin.getZ() + size.getY() + inflation
                );
            }
        };

        abstract KarkenVector3d build(KarkenVector3d origin, KarkenVector3d size, double inflation);

    }

}
