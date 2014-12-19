package com.xinghui.arsc.resourcetype;

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 * Describes a particular resource configuration.
 * <p/>
 * Created by hugo on 2014/11/7.
 */
public class ResTable_config {


    private static final int ACONFIGURATION_ORIENTATION_ANY = 0x0000,
            ACONFIGURATION_ORIENTATION_PORT = 0x0001,
            ACONFIGURATION_ORIENTATION_LAND = 0x0002,
            ACONFIGURATION_ORIENTATION_SQUARE = 0x0003,

    ACONFIGURATION_TOUCHSCREEN_ANY = 0x0000,
            ACONFIGURATION_TOUCHSCREEN_NOTOUCH = 0x0001,
            ACONFIGURATION_TOUCHSCREEN_STYLUS = 0x0002,
            ACONFIGURATION_TOUCHSCREEN_FINGER = 0x0003,

    ACONFIGURATION_DENSITY_DEFAULT = 0,
            ACONFIGURATION_DENSITY_LOW = 120,
            ACONFIGURATION_DENSITY_MEDIUM = 160,
            ACONFIGURATION_DENSITY_TV = 213,
            ACONFIGURATION_DENSITY_HIGH = 240,
            ACONFIGURATION_DENSITY_XHIGH = 320,
            ACONFIGURATION_DENSITY_XXHIGH = 480,
            ACONFIGURATION_DENSITY_NONE = 0xffff,

    ACONFIGURATION_KEYBOARD_ANY = 0x0000,
            ACONFIGURATION_KEYBOARD_NOKEYS = 0x0001,
            ACONFIGURATION_KEYBOARD_QWERTY = 0x0002,
            ACONFIGURATION_KEYBOARD_12KEY = 0x0003,

    ACONFIGURATION_NAVIGATION_ANY = 0x0000,
            ACONFIGURATION_NAVIGATION_NONAV = 0x0001,
            ACONFIGURATION_NAVIGATION_DPAD = 0x0002,
            ACONFIGURATION_NAVIGATION_TRACKBALL = 0x0003,
            ACONFIGURATION_NAVIGATION_WHEEL = 0x0004,

    ACONFIGURATION_KEYSHIDDEN_ANY = 0x0000,
            ACONFIGURATION_KEYSHIDDEN_NO = 0x0001,
            ACONFIGURATION_KEYSHIDDEN_YES = 0x0002,
            ACONFIGURATION_KEYSHIDDEN_SOFT = 0x0003,

    ACONFIGURATION_NAVHIDDEN_ANY = 0x0000,
            ACONFIGURATION_NAVHIDDEN_NO = 0x0001,
            ACONFIGURATION_NAVHIDDEN_YES = 0x0002,

    ACONFIGURATION_SCREENSIZE_ANY = 0x00,
            ACONFIGURATION_SCREENSIZE_SMALL = 0x01,
            ACONFIGURATION_SCREENSIZE_NORMAL = 0x02,
            ACONFIGURATION_SCREENSIZE_LARGE = 0x03,
            ACONFIGURATION_SCREENSIZE_XLARGE = 0x04,

    ACONFIGURATION_SCREENLONG_ANY = 0x00,
            ACONFIGURATION_SCREENLONG_NO = 0x1,
            ACONFIGURATION_SCREENLONG_YES = 0x2,

    ACONFIGURATION_UI_MODE_TYPE_ANY = 0x00,
            ACONFIGURATION_UI_MODE_TYPE_NORMAL = 0x01,
            ACONFIGURATION_UI_MODE_TYPE_DESK = 0x02,
            ACONFIGURATION_UI_MODE_TYPE_CAR = 0x03,
            ACONFIGURATION_UI_MODE_TYPE_TELEVISION = 0x04,
            ACONFIGURATION_UI_MODE_TYPE_APPLIANCE = 0x05,

    ACONFIGURATION_UI_MODE_NIGHT_ANY = 0x00,
            ACONFIGURATION_UI_MODE_NIGHT_NO = 0x1,
            ACONFIGURATION_UI_MODE_NIGHT_YES = 0x2,

    ACONFIGURATION_SCREEN_WIDTH_DP_ANY = 0x0000,

    ACONFIGURATION_SCREEN_HEIGHT_DP_ANY = 0x0000,

    ACONFIGURATION_SMALLEST_SCREEN_WIDTH_DP_ANY = 0x0000,

    ACONFIGURATION_LAYOUTDIR_ANY = 0x00,
            ACONFIGURATION_LAYOUTDIR_LTR = 0x01,
            ACONFIGURATION_LAYOUTDIR_RTL = 0x02,

    ACONFIGURATION_MCC = 0x0001,
            ACONFIGURATION_MNC = 0x0002,
            ACONFIGURATION_LOCALE = 0x0004,
            ACONFIGURATION_TOUCHSCREEN = 0x0008,
            ACONFIGURATION_KEYBOARD = 0x0010,
            ACONFIGURATION_KEYBOARD_HIDDEN = 0x0020,
            ACONFIGURATION_NAVIGATION = 0x0040,
            ACONFIGURATION_ORIENTATION = 0x0080,
            ACONFIGURATION_DENSITY = 0x0100,
            ACONFIGURATION_SCREEN_SIZE = 0x0200,
            ACONFIGURATION_VERSION = 0x0400,
            ACONFIGURATION_SCREEN_LAYOUT = 0x0800,
            ACONFIGURATION_UI_MODE = 0x1000,
            ACONFIGURATION_SMALLEST_SCREEN_SIZE = 0x2000,
            ACONFIGURATION_LAYOUTDIR = 0x4000;


    public static final int ORIENTATION_ANY = ACONFIGURATION_ORIENTATION_ANY,
            ORIENTATION_PORT = ACONFIGURATION_ORIENTATION_PORT,
            ORIENTATION_LAND = ACONFIGURATION_ORIENTATION_LAND,
            ORIENTATION_SQUARE = ACONFIGURATION_ORIENTATION_SQUARE,

    TOUCHSCREEN_ANY = ACONFIGURATION_TOUCHSCREEN_ANY,
            TOUCHSCREEN_NOTOUCH = ACONFIGURATION_TOUCHSCREEN_NOTOUCH,
            TOUCHSCREEN_STYLUS = ACONFIGURATION_TOUCHSCREEN_STYLUS,
            TOUCHSCREEN_FINGER = ACONFIGURATION_TOUCHSCREEN_FINGER,

    DENSITY_DEFAULT = ACONFIGURATION_DENSITY_DEFAULT,
            DENSITY_LOW = ACONFIGURATION_DENSITY_LOW,
            DENSITY_MEDIUM = ACONFIGURATION_DENSITY_MEDIUM,
            DENSITY_TV = ACONFIGURATION_DENSITY_TV,
            DENSITY_HIGH = ACONFIGURATION_DENSITY_HIGH,
            DENSITY_XHIGH = ACONFIGURATION_DENSITY_XHIGH,
            DENSITY_XXHIGH = ACONFIGURATION_DENSITY_XXHIGH,
            DENSITY_NONE = ACONFIGURATION_DENSITY_NONE,

    KEYBOARD_ANY = ACONFIGURATION_KEYBOARD_ANY,
            KEYBOARD_NOKEYS = ACONFIGURATION_KEYBOARD_NOKEYS,
            KEYBOARD_QWERTY = ACONFIGURATION_KEYBOARD_QWERTY,
            KEYBOARD_12KEY = ACONFIGURATION_KEYBOARD_12KEY,

    NAVIGATION_ANY = ACONFIGURATION_NAVIGATION_ANY,
            NAVIGATION_NONAV = ACONFIGURATION_NAVIGATION_NONAV,
            NAVIGATION_DPAD = ACONFIGURATION_NAVIGATION_DPAD,
            NAVIGATION_TRACKBALL = ACONFIGURATION_NAVIGATION_TRACKBALL,
            NAVIGATION_WHEEL = ACONFIGURATION_NAVIGATION_WHEEL,

    MASK_KEYSHIDDEN = 0x0003,
            KEYSHIDDEN_ANY = ACONFIGURATION_KEYSHIDDEN_ANY,
            KEYSHIDDEN_NO = ACONFIGURATION_KEYSHIDDEN_NO,
            KEYSHIDDEN_YES = ACONFIGURATION_KEYSHIDDEN_YES,
            KEYSHIDDEN_SOFT = ACONFIGURATION_KEYSHIDDEN_SOFT,

    MASK_NAVHIDDEN = 0x000c,
            SHIFT_NAVHIDDEN = 2,
            NAVHIDDEN_ANY = ACONFIGURATION_NAVHIDDEN_ANY << SHIFT_NAVHIDDEN,
            NAVHIDDEN_NO = ACONFIGURATION_NAVHIDDEN_NO << SHIFT_NAVHIDDEN,
            NAVHIDDEN_YES = ACONFIGURATION_NAVHIDDEN_YES << SHIFT_NAVHIDDEN,

    SCREENWIDTH_ANY = 0,

    SCREENHEIGHT_ANY = 0,

    SDKVERSION_ANY = 0,

    MINORVERSION_ANY = 0,

    MASK_SCREENSIZE = 0x0f,
            SCREENSIZE_ANY = ACONFIGURATION_SCREENSIZE_ANY,
            SCREENSIZE_SMALL = ACONFIGURATION_SCREENSIZE_SMALL,
            SCREENSIZE_NORMAL = ACONFIGURATION_SCREENSIZE_NORMAL,
            SCREENSIZE_LARGE = ACONFIGURATION_SCREENSIZE_LARGE,
            SCREENSIZE_XLARGE = ACONFIGURATION_SCREENSIZE_XLARGE,

    // screenLayout bits for wide/long screen variation.
    MASK_SCREENLONG = 0x30,
            SHIFT_SCREENLONG = 4,
            SCREENLONG_ANY = ACONFIGURATION_SCREENLONG_ANY << SHIFT_SCREENLONG,
            SCREENLONG_NO = ACONFIGURATION_SCREENLONG_NO << SHIFT_SCREENLONG,
            SCREENLONG_YES = ACONFIGURATION_SCREENLONG_YES << SHIFT_SCREENLONG,

    // screenLayout bits for layout direction.
    MASK_LAYOUTDIR = 0xC0,
            SHIFT_LAYOUTDIR = 6,
            LAYOUTDIR_ANY = ACONFIGURATION_LAYOUTDIR_ANY << SHIFT_LAYOUTDIR,
            LAYOUTDIR_LTR = ACONFIGURATION_LAYOUTDIR_LTR << SHIFT_LAYOUTDIR,
            LAYOUTDIR_RTL = ACONFIGURATION_LAYOUTDIR_RTL << SHIFT_LAYOUTDIR,

    // uiMode bits for the mode type.
    MASK_UI_MODE_TYPE = 0x0f,
            UI_MODE_TYPE_ANY = ACONFIGURATION_UI_MODE_TYPE_ANY,
            UI_MODE_TYPE_NORMAL = ACONFIGURATION_UI_MODE_TYPE_NORMAL,
            UI_MODE_TYPE_DESK = ACONFIGURATION_UI_MODE_TYPE_DESK,
            UI_MODE_TYPE_CAR = ACONFIGURATION_UI_MODE_TYPE_CAR,
            UI_MODE_TYPE_TELEVISION = ACONFIGURATION_UI_MODE_TYPE_TELEVISION,
            UI_MODE_TYPE_APPLIANCE = ACONFIGURATION_UI_MODE_TYPE_APPLIANCE,

    // uiMode bits for the night switch.
    MASK_UI_MODE_NIGHT = 0x30,
            SHIFT_UI_MODE_NIGHT = 4,
            UI_MODE_NIGHT_ANY = ACONFIGURATION_UI_MODE_NIGHT_ANY << SHIFT_UI_MODE_NIGHT,
            UI_MODE_NIGHT_NO = ACONFIGURATION_UI_MODE_NIGHT_NO << SHIFT_UI_MODE_NIGHT,
            UI_MODE_NIGHT_YES = ACONFIGURATION_UI_MODE_NIGHT_YES << SHIFT_UI_MODE_NIGHT,

    CONFIG_MCC = ACONFIGURATION_MCC,
            CONFIG_MNC = ACONFIGURATION_MCC,
            CONFIG_LOCALE = ACONFIGURATION_LOCALE,
            CONFIG_TOUCHSCREEN = ACONFIGURATION_TOUCHSCREEN,
            CONFIG_KEYBOARD = ACONFIGURATION_KEYBOARD,
            CONFIG_KEYBOARD_HIDDEN = ACONFIGURATION_KEYBOARD_HIDDEN,
            CONFIG_NAVIGATION = ACONFIGURATION_NAVIGATION,
            CONFIG_ORIENTATION = ACONFIGURATION_ORIENTATION,
            CONFIG_DENSITY = ACONFIGURATION_DENSITY,
            CONFIG_SCREEN_SIZE = ACONFIGURATION_SCREEN_SIZE,
            CONFIG_SMALLEST_SCREEN_SIZE = ACONFIGURATION_SMALLEST_SCREEN_SIZE,
            CONFIG_VERSION = ACONFIGURATION_VERSION,
            CONFIG_SCREEN_LAYOUT = ACONFIGURATION_SCREEN_LAYOUT,
            CONFIG_UI_MODE = ACONFIGURATION_UI_MODE,
            CONFIG_LAYOUTDIR = ACONFIGURATION_LAYOUTDIR;




    // Number of bytes in this structure.
    private int size;

    // Mobile country code (from SIM).  0 means "any".
    private short mcc;
    // Mobile network code (from SIM).  0 means "any".
    private short mnc;
//    private int imsi;

    // \0\0 means "any".  Otherwise, en, fr, etc.
    private char[] language = new char[2];
    // \0\0 means "any".  Otherwise, US, CA, etc.
    private char[] country = new char[2];
//    private int locale;

    private byte orientation;
    private byte touchscreen;
    private short density;
//    private int screenType;


    private byte keyboard;
    private byte navigation;
    private byte inputFlags;
    private byte inputPad0;
//    private int input;

    private short screenWidth;
    private short screenHeight;
//    private int screenSize;

    private short sdkVersion;
    //    // For now minorVersion must always be 0!!!  Its meaning
//    // is currently undefined.
    private short minorVersion;
//    private int version;

    private byte screenLayout;
    private byte uiMode;
    private short smallestScreenWidthDp;
//    private int screenConfig;

    private short screenWidthDp;
    private short screenHeightDp;
//    private int screenSizeDp;

    private String mDescription;

    public ResTable_config(int size, short mcc, short mnc, char[] language, char[] country, byte orientation, byte touchscreen, short density, byte keyboard, byte navigation, byte inputFlags, byte inputPad0, short screenWidth, short screenHeight, short sdkVersion, short minorVersion, byte screenLayout, byte uiMode, short smallestScreenWidthDp, short screenWidthDp, short screenHeightDp) {
        this.size = size;
        this.mcc = mcc;
        this.mnc = mnc;
        this.language = language;
        this.country = country;
        this.orientation = orientation;
        this.touchscreen = touchscreen;
        this.density = density;
        this.keyboard = keyboard;
        this.navigation = navigation;
        this.inputFlags = inputFlags;
        this.inputPad0 = inputPad0;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.sdkVersion = sdkVersion;
        this.minorVersion = minorVersion;
        this.screenLayout = screenLayout;
        this.uiMode = uiMode;
        this.smallestScreenWidthDp = smallestScreenWidthDp;
        this.screenWidthDp = screenWidthDp;
        this.screenHeightDp = screenHeightDp;

        mDescription = generateDescription();
        System.out.println(mDescription);
    }

    private String generateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------");

        if (language.length <= 0) {
            return null;
        }
        if (country.length <= 0) {
            return null;
        }
        if (language[0] != '\00') {
            sb.append("language:").append(language);
        }
        if (country[0] != '\00') {
            sb.append("country:").append(country);
        }

        switch (screenLayout & MASK_LAYOUTDIR) {
            case LAYOUTDIR_LTR:
                sb.append("-ldltr");
                break;
            case LAYOUTDIR_RTL:
                sb.append("-ldrtl");
                break;
        }



        return sb.toString();
    }


//    public static int sizeof() {
//        return 4 + 2 + 2 + 4 + 4 + 1 + 1 + 2 + 1 + 1 + 1 + 1 + 2 + 2 + 2 + 2 + 1 + 1 + 2 + 2 + 2;
//    }

    public int getSize() {
        return size;
    }

    public short getMcc() {
        return mcc;
    }

    public short getMnc() {
        return mnc;
    }

    public char[] getLanguage() {
        return language;
    }

    public char[] getCountry() {
        return country;
    }

    public byte getOrientation() {
        return orientation;
    }

    public byte getTouchscreen() {
        return touchscreen;
    }

    public short getDensity() {
        return density;
    }

    public byte getKeyboard() {
        return keyboard;
    }

    public byte getNavigation() {
        return navigation;
    }

    public byte getInputFlags() {
        return inputFlags;
    }

    public byte getInputPad0() {
        return inputPad0;
    }

    public short getScreenWidth() {
        return screenWidth;
    }

    public short getScreenHeight() {
        return screenHeight;
    }

    public short getSdkVersion() {
        return sdkVersion;
    }

    public short getMinorVersion() {
        return minorVersion;
    }

    public byte getScreenLayout() {
        return screenLayout;
    }

    public byte getUiMode() {
        return uiMode;
    }

    public short getSmallestScreenWidthDp() {
        return smallestScreenWidthDp;
    }

    public short getScreenWidthDp() {
        return screenWidthDp;
    }

    public short getScreenHeightDp() {
        return screenHeightDp;
    }

    public static ResTable_config build(BufferedSource source) throws IOException {

        int size = Utils.readInt("size:", source);
        if (size < 28) {
            throw new IOException("Config size < 28, the value is " + size);
        }

        short mcc = Utils.readShort("mcc:", source);
        short mnc = Utils.readShort("mnc:", source);
        char[] language = Utils.readStringUTF16("language: ", source, 2).toCharArray();
        char[] country = Utils.readStringUTF16("country: ", source, 2).toCharArray();
        byte orientation = Utils.readByte("orientation", source);
        byte touchscreen = Utils.readByte("touchscreen", source);
        short density = Utils.readShort("density:", source);
        byte keyboard = Utils.readByte("keyboard", source);
        byte navigation = Utils.readByte("navigation", source);
        byte inputFlags = Utils.readByte("inputFlags", source);
        byte inputPad0 = Utils.readByte("inputPad0", source);
        short screenWidth = Utils.readShort("screenWidth:", source);
        short screenHeight = Utils.readShort("screenHeight:", source);
        short sdkVersion = Utils.readShort("sdkVersion:", source);
        short minorVersion = Utils.readShort("minorVersion:", source);
        if (minorVersion != 0) {
            throw new IOException("minorVersion must be 0, the value is " + minorVersion);
        }

        byte screenLayout = 0;
        byte uiMode = 0;
        short smallestScreenWidthDp = 0;
        if (size >= 32) {
            screenLayout = Utils.readByte("screenLayout", source);
            uiMode = Utils.readByte("uiMode", source);
            smallestScreenWidthDp = Utils.readShort("smallestScreenWidthDp:", source);
        }

        short screenWidthDp = 0;
        short screenHeightDp = 0;
//        if (size >= 36) {
//            screenWidthDp = Utils.readShort("screenWidthDp:", source);
//            screenHeightDp = Utils.readShort("screenHeightDp:", source);
//        }

//        short layoutDirection = 0;
//        if (size >= 38) {
//            layoutDirection = Utils.readShort("layoutDirection", source);
//        }

        System.out.println("ResTable_config size = " + size);

        return new ResTable_config(size, mcc, mnc, language, country, orientation, touchscreen, density, keyboard, navigation, inputFlags, inputPad0,
                screenWidth, screenHeight, sdkVersion, minorVersion, screenLayout, uiMode, smallestScreenWidthDp, screenWidthDp, screenHeightDp);
    }


//    public enum CONFIG {
//
//
//
//        ORIENTATION_ANY("any", ACONFIGURATION_ORIENTATION_ANY),
//        ORIENTATION_PORT("port", ACONFIGURATION_ORIENTATION_PORT),
//        ORIENTATION_LAND("land", ACONFIGURATION_ORIENTATION_LAND),
//        ORIENTATION_SQUARE("square", ACONFIGURATION_ORIENTATION_SQUARE),
//
//
//        TOUCHSCREEN_ANY("any", ACONFIGURATION_TOUCHSCREEN_ANY),
//        TOUCHSCREEN_NOTOUCH("notouch", ACONFIGURATION_TOUCHSCREEN_NOTOUCH),
//        TOUCHSCREEN_STYLUS("stylus", ACONFIGURATION_TOUCHSCREEN_STYLUS),
//        TOUCHSCREEN_FINGER("finger", ACONFIGURATION_TOUCHSCREEN_FINGER),
//
//        DENSITY_DEFAULT("any", ACONFIGURATION_DENSITY_DEFAULT),
//        DENSITY_LOW("ldpi", ACONFIGURATION_DENSITY_LOW),
//        DENSITY_MEDIUM("mdpi", ACONFIGURATION_DENSITY_MEDIUM),
//        DENSITY_TV("tvdpi", ACONFIGURATION_DENSITY_TV),
//        DENSITY_HIGH("hdpi", ACONFIGURATION_DENSITY_HIGH),
//        DENSITY_XHIGH("xhdpi", ACONFIGURATION_DENSITY_XHIGH),
//        DENSITY_XXHIGH("xxhdpi", ACONFIGURATION_DENSITY_XXHIGH),
//        DENSITY_NONE("nodpi", ACONFIGURATION_DENSITY_NONE),
//
//        KEYBOARD_ANY("any", ACONFIGURATION_KEYBOARD_ANY),
//        KEYBOARD_NOKEYS("nokeys", ACONFIGURATION_KEYBOARD_NOKEYS),
//        KEYBOARD_QWERTY("qwerty", ACONFIGURATION_KEYBOARD_QWERTY),
//        KEYBOARD_12KEY("12key", ACONFIGURATION_KEYBOARD_12KEY),
//
//        NAVIGATION_ANY("any", ACONFIGURATION_NAVIGATION_ANY),
//        NAVIGATION_NONAV("nonav", ACONFIGURATION_NAVIGATION_NONAV),
//        NAVIGATION_DPAD("dpad", ACONFIGURATION_NAVIGATION_DPAD),
//        NAVIGATION_TRACKBALL("trackball", ACONFIGURATION_NAVIGATION_TRACKBALL),
//        NAVIGATION_WHEEL("wheel", ACONFIGURATION_NAVIGATION_WHEEL),
//
//        MASK_KEYSHIDDEN(0x0003),
//        KEYSHIDDEN_ANY("any", ACONFIGURATION_KEYSHIDDEN_ANY),
//        KEYSHIDDEN_NO("keysexposed", ACONFIGURATION_KEYSHIDDEN_NO),
//        KEYSHIDDEN_YES("keyshidden", ACONFIGURATION_KEYSHIDDEN_YES),
//        KEYSHIDDEN_SOFT("keyssoft", ACONFIGURATION_KEYSHIDDEN_SOFT),
//
//        MASK_NAVHIDDEN(0x000c),
//        SHIFT_NAVHIDDEN(2),
//        NAVHIDDEN_ANY("any", ACONFIGURATION_NAVHIDDEN_ANY << SHIFT_NAVHIDDEN.getI()),
//        NAVHIDDEN_NO("navexposed", ACONFIGURATION_NAVHIDDEN_NO << SHIFT_NAVHIDDEN.getI()),
//        NAVHIDDEN_YES("navhidden", ACONFIGURATION_NAVHIDDEN_YES << SHIFT_NAVHIDDEN.getI()),
//
//        SCREENWIDTH_ANY(0),
//
//        SCREENHEIGHT_ANY(0),
//
//
//        SDKVERSION_ANY(0),
//
//        MINORVERSION_ANY(0),
//
//
//        // screenLayout bits for screen size class.
//        MASK_SCREENSIZE(0x0f),
//        SCREENSIZE_ANY("any", ACONFIGURATION_SCREENSIZE_ANY),
//        SCREENSIZE_SMALL("small", ACONFIGURATION_SCREENSIZE_SMALL),
//        SCREENSIZE_NORMAL("normal", ACONFIGURATION_SCREENSIZE_NORMAL),
//        SCREENSIZE_LARGE("large", ACONFIGURATION_SCREENSIZE_LARGE),
//        SCREENSIZE_XLARGE("xlarge", ACONFIGURATION_SCREENSIZE_XLARGE),
//
//        // screenLayout bits for wide/long screen variation.
//        MASK_SCREENLONG(0x30),
//        SHIFT_SCREENLONG(4),
//        SCREENLONG_ANY("any", ACONFIGURATION_SCREENLONG_ANY << SHIFT_SCREENLONG.getI()),
//        SCREENLONG_NO("notlong", ACONFIGURATION_SCREENLONG_NO << SHIFT_SCREENLONG.getI()),
//        SCREENLONG_YES("long", ACONFIGURATION_SCREENLONG_YES << SHIFT_SCREENLONG.getI()),
//
//        // screenLayout bits for layout direction.
//        MASK_LAYOUTDIR(0xC0),
//        SHIFT_LAYOUTDIR(6),
//        LAYOUTDIR_ANY("any", ACONFIGURATION_LAYOUTDIR_ANY << SHIFT_LAYOUTDIR.getI()),
//        LAYOUTDIR_LTR("ldltr", ACONFIGURATION_LAYOUTDIR_LTR << SHIFT_LAYOUTDIR.getI()),
//        LAYOUTDIR_RTL("ldrtl", ACONFIGURATION_LAYOUTDIR_RTL << SHIFT_LAYOUTDIR.getI()),
//
//        // uiMode bits for the mode type.
//        MASK_UI_MODE_TYPE(0x0f),
//        UI_MODE_TYPE_ANY("any", ACONFIGURATION_UI_MODE_TYPE_ANY),
//        UI_MODE_TYPE_NORMAL("normal", ACONFIGURATION_UI_MODE_TYPE_NORMAL),
//        UI_MODE_TYPE_DESK("desk", ACONFIGURATION_UI_MODE_TYPE_DESK),
//        UI_MODE_TYPE_CAR("car", ACONFIGURATION_UI_MODE_TYPE_CAR),
//        UI_MODE_TYPE_TELEVISION("television", ACONFIGURATION_UI_MODE_TYPE_TELEVISION),
//        UI_MODE_TYPE_APPLIANCE("appliance", ACONFIGURATION_UI_MODE_TYPE_APPLIANCE),
//
//        // uiMode bits for the night switch.
//        MASK_UI_MODE_NIGHT(0x30),
//        SHIFT_UI_MODE_NIGHT(4),
//        UI_MODE_NIGHT_ANY("any", ACONFIGURATION_UI_MODE_NIGHT_ANY << SHIFT_UI_MODE_NIGHT.getI()),
//        UI_MODE_NIGHT_NO("notnight", ACONFIGURATION_UI_MODE_NIGHT_NO << SHIFT_UI_MODE_NIGHT.getI()),
//        UI_MODE_NIGHT_YES("night", ACONFIGURATION_UI_MODE_NIGHT_YES << SHIFT_UI_MODE_NIGHT.getI()),
//
//        // Flags indicating a set of config values.  These flag constants must
//        // match the corresponding ones in android.content.pm.ActivityInfo and
//        // attrs_manifest.xml.
//        CONFIG_MCC("any", ACONFIGURATION_MCC),
//        CONFIG_MNC("any", ACONFIGURATION_MNC),
//        //        CONFIG_MNC("any", ACONFIGURATION_MCC),
//        CONFIG_LOCALE("any", ACONFIGURATION_LOCALE),
//        CONFIG_TOUCHSCREEN("any", ACONFIGURATION_TOUCHSCREEN),
//        CONFIG_KEYBOARD("any", ACONFIGURATION_KEYBOARD),
//        CONFIG_KEYBOARD_HIDDEN("any", ACONFIGURATION_KEYBOARD_HIDDEN),
//        CONFIG_NAVIGATION("any", ACONFIGURATION_NAVIGATION),
//        CONFIG_ORIENTATION("any", ACONFIGURATION_ORIENTATION),
//        CONFIG_DENSITY("any", ACONFIGURATION_DENSITY),
//        CONFIG_SCREEN_SIZE("any", ACONFIGURATION_SCREEN_SIZE),
//        CONFIG_SMALLEST_SCREEN_SIZE("any", ACONFIGURATION_SMALLEST_SCREEN_SIZE),
//        CONFIG_VERSION("any", ACONFIGURATION_VERSION),
//        CONFIG_SCREEN_LAYOUT("any", ACONFIGURATION_SCREEN_LAYOUT),
//        CONFIG_UI_MODE("any", ACONFIGURATION_UI_MODE),
//        CONFIG_LAYOUTDIR("any", ACONFIGURATION_LAYOUTDIR);
//
//        private int i;
//
//        private CONFIG(String str, int i) {
//            this.i = i;
//        }
//
//        private CONFIG(int i) {
//            this.i = i;
//        }
//
//        public int getI() {
//            return this.i;
//        }
//
//    }


//    void copyFromDeviceNoSwap(const ResTable_config& o);
//
//    void copyFromDtoH(const ResTable_config& o);
//
//    void swapHtoD();
//
//    int compare(const ResTable_config& o) const;
//    int compareLogical(const ResTable_config& o) const;
//

//
//    // Compare two configuration, returning CONFIG_* flags set for each value
//    // that is different.
//    int diff(const ResTable_config& o) const;
//
//    // Return true if 'this' is more specific than 'o'.
//    bool isMoreSpecificThan(const ResTable_config& o) const;
//
//    // Return true if 'this' is a better match than 'o' for the 'requested'
//    // configuration.  This assumes that match() has already been used to
//    // remove any configurations that don't match the requested configuration
//    // at all; if they are not first filtered, non-matching results can be
//    // considered better than matching ones.
//    // The general rule per attribute: if the request cares about an attribute
//    // (it normally does), if the two (this and o) are equal it's a tie.  If
//    // they are not equal then one must be generic because only generic and
//    // '==requested' will pass the match() call.  So if this is not generic,
//    // it wins.  If this IS generic, o wins (return false).
//    bool isBetterThan(const ResTable_config& o, const ResTable_config* requested) const;
//
//    // Return true if 'this' can be considered a match for the parameters in
//    // 'settings'.
//    // Note this is asymetric.  A default piece of data will match every request
//    // but a request for the default should not match odd specifics
//    // (ie, request with no mcc should not match a particular mcc's data)
//    // settings is the requested settings
//    bool match(const ResTable_config& settings) const;
//
//    void getLocale(char str[6]) const;
//
//    String8 toString() const;
//};


}
