package com.xinghui.arsc;


import android.util.TypedValue;

import com.xinghui.arsc.resourcetype.ResChunk_header;
import com.xinghui.arsc.resourcetype.ResStringPool;
import com.xinghui.arsc.resourcetype.ResStringPool_header;
import com.xinghui.arsc.resourcetype.ResTable_entry;
import com.xinghui.arsc.resourcetype.ResTable_header;
import com.xinghui.arsc.resourcetype.ResTable_map;
import com.xinghui.arsc.resourcetype.ResTable_map_entry;
import com.xinghui.arsc.resourcetype.ResTable_package;
import com.xinghui.arsc.resourcetype.ResTable_type;
import com.xinghui.arsc.resourcetype.ResTable_typeSpec;
import com.xinghui.arsc.resourcetype.Res_value;
import com.xinghui.arsc.resourcetype.ResourceTypes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import okio.BufferedSource;
import okio.Okio;

/**
 * \frameworks\base\include\androidfw
 * \frameworks\base\tools\aapt
 * <p/>
 * Created by hugo on 2014/10/31.
 */
public class ArscDecoder {

    public void decodeArsc(InputStream in) throws IOException {
        BufferedSource source = Okio.buffer(Okio.source(in));

        ResTable_header mTableHeader = ResTable_header.build(source);

        for (int p = 0; p < mTableHeader.getPackageCount(); p++) {
            if (mTableHeader.getHeader().getType() == ResourceTypes.RES_TABLE_TYPE) {
                System.out.println("\"...RES_TABLE_TYPE..............................\"");
                ResStringPool_header mStringPoolHeader = ResStringPool_header.build(source);

                ResStringPool mStringPool = new ResStringPool(source, mStringPoolHeader, true);

                System.out.println("\"...RES_TABLE_PACKAGE_TYPE..............................\"");
                ResTable_package mTablePackage = ResTable_package.build(source);
                if (mTablePackage.getHeader().getType() == ResourceTypes.RES_TABLE_PACKAGE_TYPE) {

                    System.out.println("\"...Type String Pool..............................\"");

                    // Type String Pool
                    ResStringPool_header mTypeStringPoolHeader = ResStringPool_header.build(source);

                    ResStringPool mTypeStringPool = new ResStringPool(source, mTypeStringPoolHeader, false);
                    ArrayList<String> type_string_pool_list = mTypeStringPool.getString_pool_list();

                    if (!mTypeStringPoolHeader.isUTF8()) {
                        Utils.skip(source, 2);
                    }

                    System.out.println("\"...Key String Pool..............................\"");
                    // Key String Pool
                    ResStringPool_header mKeyStringPoolHeader = ResStringPool_header.build(source);

                    ResStringPool mKeyStringPool = new ResStringPool(source, mKeyStringPoolHeader, false);
                    ArrayList<String> key_string_pool_list = mKeyStringPool.getString_pool_list();


                    /**
                     * 解析
                     * 类型规范数据块(RES_TABLE_TYPE_SPEC_TYPE)
                     * 和
                     * 类型资源项数据块(RES_TABLE_TYPE_TYPE)
                     */
                    int spec_type_count = 0;
                    ResChunk_header mChunkHeader = ResChunk_header.build(source);
                    while (spec_type_count < mTypeStringPoolHeader.getStringCount()){
                        System.out.println("\"...Header..............................\"");

                        if (mChunkHeader.getType() != ResourceTypes.RES_TABLE_TYPE_SPEC_TYPE) {
                            throw new IOException("The Chunk Type is NOT = RES_TABLE_TYPE_SPEC_TYPE, the type value is " + mChunkHeader.getType());
                        }
                        System.out.println("\"...RES_TABLE_TYPE_SPEC_TYPE..............................\"");
                        ResTable_typeSpec rttp = ResTable_typeSpec.build(mChunkHeader, source);
                        String type = type_string_pool_list.get(spec_type_count);
                        System.out.println("typeSpec ---- " + type);
                        spec_type_count++;
                        int[] resItemChunkOffsetArray = new int[rttp.getEntryCount()];
                        for (int i = 0; i < rttp.getEntryCount(); i++) {
                            // 即每一个uint32_t，用来描述一个资源项的配置差异性
                            resItemChunkOffsetArray[i] = Utils.readInt(type + " - uint32_t:", source);
                        }

                        if (source.exhausted()) {
                            break;
                        }
                        mChunkHeader = nextSpecTypeChunk(source);
                        if (mChunkHeader == null) {
                            break;
                        }
                    }
                    System.out.println("spec_type_count = " + spec_type_count + ",---type_string_pool.getStringCount() = " + mTypeStringPoolHeader.getStringCount());
                }
            }
        }
        if (Utils.getSize() != mTableHeader.getHeader().getSize()) {
            throw new AssertionError("Read Size And Chunk's Size is NOT EQUAL! \n" + "Read Size = " + Utils.getSize() + "-Chunk's size = " + mTableHeader.getHeader().getSize());
        }
        source.close();
    }

    private ResChunk_header nextSpecTypeChunk(BufferedSource source) throws IOException {
        ResChunk_header mChunkHeader = ResChunk_header.build(source);
        while (mChunkHeader.getType() == ResourceTypes.RES_TABLE_TYPE_TYPE) {
            System.out.println("\"...RES_TABLE_TYPE_TYPE..............................\"");

            ResTable_type rtt = ResTable_type.build(mChunkHeader, source);

            int[] entryOffset = new int[rtt.getEntryCount()];
            for (int i = 0; i < rtt.getEntryCount(); i++) {
                // 即每一个uint32_t，都是用来描述一个资源项数据块的偏移位置
                entryOffset[i] = Utils.readInt("entryOffset[" + i + "]:", source);
            }

            for (int i = 0; i < entryOffset.length; i++) {
                if (entryOffset[i] != ResTable_type.NO_ENTRY) {
                    ResTable_entry e = ResTable_entry.build(source);
                    if ((e.getFlags() & ResTable_entry.FLAG_PUBLIC) == 1) {
                        System.out.println("FLAG_PUBLIC" + i);
                    } else {
                        Res_value value = (e.getFlags() & ResTable_entry.FLAG_COMPLEX) == 0 ? readValue(source) : readComplexEntry(e, source);
                    }
                }
            }
            if (source.exhausted()) {
                return null;
            }
            mChunkHeader = ResChunk_header.build(source);
        }
        if (mChunkHeader.getType() == ResourceTypes.RES_TABLE_TYPE_SPEC_TYPE) {
            return mChunkHeader;
        } else {
            throw new IOException("Type is Wrong! " + mChunkHeader.getType());
        }
    }

    private Res_value readValue(BufferedSource source) throws IOException {
        Res_value value = Res_value.build(source);
        if (value.getSize() != 8) {
            throw new IOException("Res_value size is not 8, the value is " + value.getSize());
        }
        if (value.getRes0() != 0) {
            throw new IOException("Res_value res0 is not 0, the value is " + value.getRes0());
        }

        String s = TypedValue.coerceToString(value.getDataType(), value.getData());
        System.out.println("readValue method type = " + value.getDataType() + ", data = " + s);
        return null;
    }

    private Res_value readComplexEntry(ResTable_entry e, BufferedSource source) throws IOException {

        ResTable_map_entry entry = ResTable_map_entry.build(e, source);
        System.out.println("ResTable_map_entry---" + entry.getCount());
        for (int i = 0; i < entry.getCount(); i++) {
            ResTable_map map = ResTable_map.build(source);
            String s = TypedValue.coerceToString(map.getValue().getDataType(), map.getValue().getData());
            System.out.println("readComplexEntry method ---type = " + map.getValue().getDataType() + ", data = " + s + ",----i = " + i);
        }

        return null;
    }


    public static void main(String[] args) {

        ArscDecoder ge = new ArscDecoder();

        FileInputStream is;
        try {
//            is = new FileInputStream("F:\\Android\\android-studio\\workspace\\ArscDecoder\\arsc\\res\\resources.arsc");// UTF-16
            is = new FileInputStream("F:\\Android\\android-studio\\workspace\\ArscDecoder\\arsc\\res\\resources2.arsc");// UTF-8

            ge.decodeArsc(is);
//            Utils.printFileByte(is);

            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
