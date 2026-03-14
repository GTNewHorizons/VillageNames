package astrotibs.villagenames.igloo.utils;

public enum Mirror {

    NONE("no_mirror"),
    LEFT_RIGHT("mirror_left_right"),
    FRONT_BACK("mirror_front_back");

    private final String name;
    private static String[] mirrorNames = new String[values().length];

    private Mirror(String nameIn) {
        this.name = nameIn;
    }

    /**
     * Mirrors the given rotation like specified by this mirror. rotations start at 0 and go up to rotationCount-1. 0 is
     * front, rotationCount/2 is back.
     */
    public int mirrorRotation(int rotationIn, int rotationCount) {
        int i = rotationCount / 2;
        int j = rotationIn > i ? rotationIn - rotationCount : rotationIn;

        switch (this) {
            case LEFT_RIGHT:
                return (rotationCount - j) % rotationCount;
            case FRONT_BACK:
                return (i - j + rotationCount) % rotationCount;
            default:
                return rotationIn;
        }
    }

    /**
     * Determines the rotation that is equivalent to this mirror if the rotating object faces in the given direction
     */
    public Rotation toRotation(EnumFacing194 facing) {
        EnumFacing194.Axis enumfacing$axis = facing.getAxis();
        return (this != LEFT_RIGHT || enumfacing$axis != EnumFacing194.Axis.Z)
            && (this != FRONT_BACK || enumfacing$axis != EnumFacing194.Axis.X) ? Rotation.NONE : Rotation.CLOCKWISE_180;
    }

    /**
     * Mirror the given facing according to this mirror
     */
    public EnumFacing194 mirror(EnumFacing194 facing) {
        switch (this) {
            case LEFT_RIGHT:

                if (facing == EnumFacing194.WEST) {
                    return EnumFacing194.EAST;
                } else {
                    if (facing == EnumFacing194.EAST) {
                        return EnumFacing194.WEST;
                    }

                    return facing;
                }

            case FRONT_BACK:

                if (facing == EnumFacing194.NORTH) {
                    return EnumFacing194.SOUTH;
                } else {
                    if (facing == EnumFacing194.SOUTH) {
                        return EnumFacing194.NORTH;
                    }

                    return facing;
                }

            default:
                return facing;
        }
    }

    static {
        int i = 0;

        for (Mirror mirror : values()) {
            mirrorNames[i++] = mirror.name;
        }
    }
}
