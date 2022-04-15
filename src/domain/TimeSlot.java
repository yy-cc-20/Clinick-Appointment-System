package domain;

public enum TimeSlot {
    // 1 slot = 30 minutes
    SLOT_1("0800AM"),
    SLOT_2("0830AM"),
    SLOT_3("0900AM"),
    SLOT_4("0930AM"),
    SLOT_5("1000AM"),
    SLOT_6("1030AM"),
    SLOT_7("1100AM"),
    SLOT_8("1130AM"),
    // Lunch break: 1200AM - 0200PM
    SLOT_9("0200PM"),
    SLOT_10("0230PM"),
    SLOT_11("0300PM"),
    SLOT_12("0330PM"),
    SLOT_13("0400PM"),
    SLOT_14("0430PM");

    private final String time;

    TimeSlot(String time) {
        this.time = time;
    }

    public String toString() {
        return time;
    }

    public static String getSlot(int slot) {
        switch (slot) {
            case 1 -> {
                return SLOT_1.time;
            }
            case 2 -> {
                return SLOT_2.time;
            }
            case 3 -> {
                return SLOT_3.time;
            }
            case 4 -> {
                return SLOT_4.time;
            }
            case 5 -> {
                return SLOT_5.time;
            }
            case 6 -> {
                return SLOT_6.time;
            }
            case 7 -> {
                return SLOT_7.time;
            }
            case 8 -> {
                return SLOT_8.time;
            }
            case 9 -> {
                return SLOT_9.time;
            }
            case 10 -> {
                return SLOT_10.time;
            }
            case 11 -> {
                return SLOT_11.time;
            }
            case 12 -> {
                return SLOT_12.time;
            }
            case 13 -> {
                return SLOT_13.time;
            }
            default -> {
                return SLOT_14.time;
            }
        }
    }
}
