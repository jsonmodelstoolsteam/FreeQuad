package application.stages.components;

public enum ObjectType {
    BLOCK("B", "BLOCK"), ITEM("I", "ITEM");

    private String code;
    private String type;

    ObjectType(String code, String type) {
        this.code = code;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public static ObjectType getByCode(String genderCode) {
        for (ObjectType g : ObjectType.values()) {
            if (g.code.equals(genderCode)) {
                return g;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.type;
    }
}