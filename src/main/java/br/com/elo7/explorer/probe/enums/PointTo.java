package br.com.elo7.explorer.probe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointTo {
    NORTH(1) {
        @Override
        public PointTo changeOrientation(Character direction) {
            return direction.equals(AllowedActions.L.getValue()) ? WEST : EAST;
        }

    },
    SOUTH(-1) {
        @Override
        public PointTo changeOrientation(Character direction) {
            return direction.equals(AllowedActions.L.getValue()) ? EAST : WEST;
        }
    },
    WEST(-1) {
        @Override
        public PointTo changeOrientation(Character direction) {
            return direction.equals(AllowedActions.L.getValue()) ? SOUTH : NORTH;
        }
    },
    EAST(1) {
        @Override
        public PointTo changeOrientation(Character direction) {
            return direction.equals(AllowedActions.L.getValue()) ? NORTH : SOUTH;
        }
    };

    private Integer step;

    public abstract PointTo changeOrientation(Character direction);

}
