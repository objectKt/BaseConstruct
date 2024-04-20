package com.backaudio.android.driver.bluetooth;

public enum EVirtualButton {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, ZERO, ASTERISK, WELL;

    public static final String parse(EVirtualButton button) {
        switch (button) {
        case ONE:
            return "1";
        case TWO:
            return "2";
        case THREE:
            return "3";
        case FOUR:
            return "4";
        case FIVE:
            return "5";
        case SIX:
            return "6";
        case SEVEN:
            return "7";
        case EIGHT:
            return "8";
        case NINE:
            return "9";
        case ZERO:
            return "0";
        case ASTERISK:
            return "*";
        case WELL:
            return "#";
        default:
            return null;
        }
    }

    public static final String parse(char button) {
        if (button == '*' || button == '#' || (button >= '0' && button <= '9')) {
            return String.valueOf(button);
        }
        return null;
    }

}
