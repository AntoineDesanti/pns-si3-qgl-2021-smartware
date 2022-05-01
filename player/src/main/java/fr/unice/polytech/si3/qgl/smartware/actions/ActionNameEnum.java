package fr.unice.polytech.si3.qgl.smartware.actions;

public enum ActionNameEnum {
    MOVING("MOVING"),
    LIFT_SAIL("LIFT_SAIL"),
    LOWER_SAIL("LOWER_SAIL"),
    TURN("TURN"),
    OAR("OAR"),
    USE_WATCH("USE_WATCH");

    public String getType() {
        return type;
    }

    private final String type;

    ActionNameEnum(String type) {
        this.type = type;
    }
}
