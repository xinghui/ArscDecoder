package com.xinghui.arsc.resourcetype;

import com.xinghui.arsc.Utils;

import java.io.IOException;

import okio.BufferedSource;

/**
 * A collection of resource data types within a package.  Followed by
 * one or more ResTable_type and ResTable_typeSpec structures containing the
 * entry values for each resource type.
 * <p/>
 * Created by hugo on 2014/11/4.
 */
public class ResTable_package {

    private ResChunk_header header;

    // If this is a base package, its ID.  Package IDs start
    // at 1 (corresponding to the value of the package bits in a
    // resource identifier).  0 means this is not a base package.
    private int id;

    // Actual name of this package, \0-terminated.
//    private char[] name = new char[128];//char16_t name[128];

    private String name;

    // Offset to a ResStringPool_header defining the resource
    // type symbol table.  If zero, this package is inheriting from
    // another base package (overriding specific values in it).
    private int typeStrings;

    // Last index into typeStrings that is for public use by others.
    private int lastPublicType;

    // Offset to a ResStringPool_header defining the resource
    // key symbol table.  If zero, this package is inheriting from
    // another base package (overriding specific values in it).
    private int keyStrings;

    // Last index into keyStrings that is for public use by others.
    private int lastPublicKey;

    private ResTable_package(ResChunk_header header, int id, char[] name, int typeStrings, int lastPublicType, int keyStrings, int lastPublicKey) {
        this.header = header;
        this.id = id;
        this.name = String.valueOf(name);
        this.typeStrings = typeStrings;
        this.lastPublicType = lastPublicType;
        this.keyStrings = keyStrings;
        this.lastPublicKey = lastPublicKey;
    }

    public ResChunk_header getHeader() {
        return header;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTypeStrings() {
        return typeStrings;
    }

    public int getLastPublicType() {
        return lastPublicType;
    }

    public int getKeyStrings() {
        return keyStrings;
    }

    public int getLastPublicKey() {
        return lastPublicKey;
    }

    public static ResTable_package build(BufferedSource source) throws IOException {
        ResChunk_header header = ResChunk_header.build(source);

        int id = Utils.readInt("id: ", source);
        if (id == 0) {
            throw new IOException("id = 0 means this is not a base package");
        }

        char[] name = Utils.readStringUTF16("name: ", source, 128).toCharArray();

        int typeStrings = Utils.readInt("typeStrings: ", source);
        if (typeStrings == 0) {
            throw new IOException("typeStrings If zero, this package is inheriting from another base package");
        }

        int lastPublicType = Utils.readInt("lastPublicType: ", source);

        int keyStrings = Utils.readInt("keyStrings: ", source);
        if (keyStrings == 0) {
            throw new IOException("keyStrings If zero, this package is inheriting from another base package");
        }

        int lastPublicKey = Utils.readInt("lastPublicKey: ", source);

        return new ResTable_package(header, id, name, typeStrings, lastPublicType, keyStrings, lastPublicKey);
    }

    public static int sizeof() {
        return ResChunk_header.sizeof() + 4 * 5 + 128 * 2;
    }
}
