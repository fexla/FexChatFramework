package chat;

public enum FragType {
    ERROR(-1),
    LOGIN(0),
    TEXT(1),
    INT(2),
    FLOAT(3),
    DOUBLE(4),
    BOOL(5),
    BYTES(6),
    BYTES_SEG(7),
    MAP_PACKAGE(10),
    LIST_PACKAGE(11),
    AUDIO(100);

    public int code;

    FragType(int code) {
        this.code = code;
    }

    public static FragType getType(int code) {
        return switch (code) {
            case 0 -> LOGIN;
            case 1 -> TEXT;
            case 2 -> INT;
            case 3 -> FLOAT;
            case 4 -> DOUBLE;
            case 5 -> BOOL;
            case 6 -> BYTES;
            case 7 -> BYTES_SEG;
            case 10 -> MAP_PACKAGE;
            case 11 -> LIST_PACKAGE;
            case 100 -> AUDIO;
            default -> ERROR;
        };
    }
}
