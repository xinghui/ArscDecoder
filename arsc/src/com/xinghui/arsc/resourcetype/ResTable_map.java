package com.xinghui.arsc.resourcetype;

import java.io.IOException;

import okio.BufferedSource;

/**
 * A single name/value mapping that is part of a complex resource
 * entry.
 * <p/>
 * Created by hugo on 2014/11/14.
 */
public class ResTable_map {
    // The resource identifier defining this mapping's name.  For attribute
    // resources, 'name' can be one of the following special resource types
    // to supply meta-data about the attribute; for all other resource types
    // it must be an attribute resource.
    private ResTable_ref name;

    // Special values for 'name' when defining attribute resources.
    // This entry holds the attribute's type code.
    public static final int ATTR_TYPE = Res_MAKEINTERNAL(0),

    // For integral attributes, this is the minimum value it can hold.
    ATTR_MIN = Res_MAKEINTERNAL(1),

    // For integral attributes, this is the maximum value it can hold.
    ATTR_MAX = Res_MAKEINTERNAL(2),

    // Localization of this resource is can be encouraged or required with
    // an aapt flag if this is set
    ATTR_L10N = Res_MAKEINTERNAL(3),

    // for plural support, see android.content.res.PluralRules#attrForQuantity(int)
    ATTR_OTHER = Res_MAKEINTERNAL(4),
            ATTR_ZERO = Res_MAKEINTERNAL(5),
            ATTR_ONE = Res_MAKEINTERNAL(6),
            ATTR_TWO = Res_MAKEINTERNAL(7),
            ATTR_FEW = Res_MAKEINTERNAL(8),
            ATTR_MANY = Res_MAKEINTERNAL(9);

    // Bit mask of allowed types, for use with ATTR_TYPE.
    // No type has been defined for this attribute, use generic
    // type handling.  The low 16 bits are for types that can be
    // handled generically; the upper 16 require additional information
    // in the bag so can not be handled generically for TYPE_ANY.
    public static final int TYPE_ANY = 0x0000FFFF,

    // Attribute holds a references to another resource.
    TYPE_REFERENCE = 1 << 0,

    // Attribute holds a generic string.
    TYPE_STRING = 1 << 1,

    // Attribute holds an integer value.  ATTR_MIN and ATTR_MIN can
    // optionally specify a constrained range of possible integer values.
    TYPE_INTEGER = 1 << 2,

    // Attribute holds a boolean integer.
    TYPE_BOOLEAN = 1 << 3,

    // Attribute holds a color value.
    TYPE_COLOR = 1 << 4,

    // Attribute holds a floating point value.
    TYPE_FLOAT = 1 << 5,

    // Attribute holds a dimension value, such as "20px".
    TYPE_DIMENSION = 1 << 6,

    // Attribute holds a fraction value, such as "20%".
    TYPE_FRACTION = 1 << 7,

    // Attribute holds an enumeration.  The enumeration values are
    // supplied as additional entries in the map.
    TYPE_ENUM = 1 << 16,

    // Attribute holds a bitmaks of flags.  The flag bit values are
    // supplied as additional entries in the map.
    TYPE_FLAGS = 1 << 17;

    // Enum of localization modes, for use with ATTR_L10N.
    public static final int L10N_NOT_REQUIRED = 0,
            L10N_SUGGESTED = 1;

    // This mapping's value.
    private Res_value value;


    private static int Res_MAKEINTERNAL(int entry) {
        return 0x01000000 | (entry & 0xFFFF);
    }

    public ResTable_map(ResTable_ref name, Res_value value) {
        this.name = name;
        this.value = value;
    }

    public ResTable_ref getName() {
        return name;
    }

    public Res_value getValue() {
        return value;
    }

    public static ResTable_map build(BufferedSource source) throws IOException {
        ResTable_ref name = ResTable_ref.build(source);
        Res_value value = Res_value.build(source);

        return new ResTable_map(name, value);
    }

    public static int sizeof() {
        return ResTable_ref.sizeof() + Res_value.sizeof();
    }
}
