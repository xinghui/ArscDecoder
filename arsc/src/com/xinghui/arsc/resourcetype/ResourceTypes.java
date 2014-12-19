package com.xinghui.arsc.resourcetype;

import java.io.IOException;
import java.nio.ByteBuffer;

import okio.BufferedSource;
import okio.ByteString;

/**
 * Created by hugo on 2014/11/3.
 */
public class ResourceTypes {

    public final static short RES_NULL_TYPE = 0x0000,
            RES_STRING_POOL_TYPE = 0x0001,
            RES_TABLE_TYPE = 0x0002,
            RES_XML_TYPE = 0x0003,

    // Chunk types in RES_XML_TYPE
    RES_XML_FIRST_CHUNK_TYPE = 0x0100,
            RES_XML_START_NAMESPACE_TYPE = 0x0100,
            RES_XML_END_NAMESPACE_TYPE = 0x0101,
            RES_XML_START_ELEMENT_TYPE = 0x0102,
            RES_XML_END_ELEMENT_TYPE = 0x0103,
            RES_XML_CDATA_TYPE = 0x0104,
            RES_XML_LAST_CHUNK_TYPE = 0x017f,
    // This contains a uint32_t array mapping strings in the string
    // pool back to resource identifiers.  It is optional.
    RES_XML_RESOURCE_MAP_TYPE = 0x0180,

    // Chunk types in RES_TABLE_TYPE
    RES_TABLE_PACKAGE_TYPE = 0x0200,
            RES_TABLE_TYPE_TYPE = 0x0201,
            RES_TABLE_TYPE_SPEC_TYPE = 0x0202;


//    public static short RES_NULL_TYPE_LITTLE = 0x0000,
//            RES_STRING_POOL_TYPE_LITTLE = 0x0001,
//            RES_TABLE_TYPE_LITTLE = 0x0002,
//            RES_XML_TYPE_LITTLE = 0x0003,
//
//    // Chunk types in RES_XML_TYPE
//    RES_XML_FIRST_CHUNK_TYPE_LITTLE = 0x0100,
//            RES_XML_START_NAMESPACE_TYPE_LITTLE = 0x0100,
//            RES_XML_END_NAMESPACE_TYPE_LITTLE = 0x0101,
//            RES_XML_START_ELEMENT_TYPE_LITTLE = 0x0102,
//            RES_XML_END_ELEMENT_TYPE_LITTLE = 0x0103,
//            RES_XML_CDATA_TYPE_LITTLE = 0x0104,
//            RES_XML_LAST_CHUNK_TYPE_LITTLE = 0x017f,
//    // This contains a uint32_t array mapping strings in the string
//    // pool back to resource identifiers.  It is optional.
//    RES_XML_RESOURCE_MAP_TYPE_LITTLE = 0x0180,
//
//    // Chunk types in RES_TABLE_TYPE
//    RES_TABLE_PACKAGE_TYPE_LITTLE = 0x0200,
//            RES_TABLE_TYPE_TYPE_LITTLE = 0x0201,
//            RES_TABLE_TYPE_SPEC_TYPE_LITTLE = 0x0202;


    class Res_value {
        // Number of bytes in this structure.
        short size;

        // Always set to 0.
        byte res0;
    }
}
