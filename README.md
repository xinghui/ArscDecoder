ArscDecoder
===========

Decode Android resources.arsc file

STRUCTURE:
-----------------------

**COMMON STRUCTURE**

  // Header that appears at the front of every data chunk in a resource.
* ResChunk_header
    short type
    short headerSize
    int bodySize

    chunk size: 2 + 2 + 4;

**UNIQUE STRUCTURE**

  // Header for a resource table.
* ResTable_header
    ResChunk_header header
    int packageCount

    chunk size: ResChunk_header.size + 4;

  // Definition for a pool of strings.
* ResStringPool_header
    ResChunk_header header
    int stringCount
    int styleCount
    int flags
    int stringsStart
    int stylesStart

    chunk size: ResChunk_header.size + 4 * 5

  // Convenience class for assessing data in a ResStringPool resource.
* ResStringPool
    skip: stringsStart - header.headerSize = stringCount * 4 + styleCount * 4
        stringOffsets = int[stringCount]
        styleOffsets = int[styleCount]
    read string content, UTF8 or UTF16
    read style content

  // A collection of resource data types within a packages.
* ResTable_package
    ResChunk_header header
    int id
    char[128] name
    int typeStrings
    int lastPublicType
    int keyStrings
    int lastPublicKey

    chunk size: ResChunk_header.size + 4 * 5 + 128 * 2

  // A specification of the resources defined by a particular type.
* ResTable_typeSpec
    ResChunk_header header
    byte id
    byte res0
    short res1
    int entryCount

    chunk size: ResChunk_header.size + 1 * 2 + 2 + 4

  // A collection of resource entries for a particular resource data type.
* ResTable_type
    ResChunk_header
    byte id
    byte res0
    short res1
    int entryCount
    int entriesStart
    ResTable_config config

    chunk size: ResChunk_header.size + 1 * 2 + 2 + 4 * 2 + ResTable_config.size

  // Describes a particular resource configuration.
* ResTable_config
    int size
    short mcc
    short mnc
    char[2] language
    char[2] country
    byte orientation
    byte touchscreen
    short density
    byte keyboard
    byte navigation
    byte inputFlags
    byte inputPad0
    short screenWidth
    short screenHeight
    short sdkVersion
    short minorVersion
    byte screenLayout
    byte uiMode
    short smallestScreenWidthDp

    // connect with the size value
    // short screenWidthDp
    // short screenHeightDp
    // short layoutDirection

SAMPLE:
-----------------------

                  type:  : 02 00           2
            headerSize:  : 0C 00           12
                  size:  : 84 14 00 00           5252
          packageCount:  : 01 00 00 00           1
    "...RES_TABLE_TYPE.............................."
                  type:  : 01 00           1
            headerSize:  : 1C 00           28
                  size:  : 30 04 00 00           1072
           stringCount:  : 2E 00 00 00           46
            styleCount:  : 00 00 00 00           0
                 flags:  : 00 01 00 00           256
          stringsStart:  : D4 00 00 00           212
           stylesStart:  : 00 00 00 00           0
    SORTED_FLAG = 1
    UTF8_FLAG = 256
             intArray{0] : 00 00 00 00           0
             intArray{1] : 1B 00 00 00           27
             intArray{2] : 3B 00 00 00           59
             intArray{3] : 56 00 00 00           86
             intArray{4] : 73 00 00 00           115
             intArray{5] : 91 00 00 00           145
             intArray{6] : AE 00 00 00           174
             intArray{7] : C0 00 00 00           192
             intArray{8] : E7 00 00 00           231
             intArray{9] : 0E 01 00 00           270
            intArray{10] : 36 01 00 00           310
            intArray{11] : 5F 01 00 00           351
            intArray{12] : 6F 01 00 00           367
            intArray{13] : 77 01 00 00           375
            intArray{14] : 7B 01 00 00           379
            intArray{15] : 7F 01 00 00           383
            intArray{16] : 84 01 00 00           388
            intArray{17] : 8D 01 00 00           397
            intArray{18] : 9A 01 00 00           410
            intArray{19] : A3 01 00 00           419
            intArray{20] : AD 01 00 00           429
            intArray{21] : B7 01 00 00           439
            intArray{22] : BC 01 00 00           444
            intArray{23] : C1 01 00 00           449
            intArray{24] : CE 01 00 00           462
            intArray{25] : D4 01 00 00           468
            intArray{26] : DA 01 00 00           474
            intArray{27] : E5 01 00 00           485
            intArray{28] : F4 01 00 00           500
            intArray{29] : 03 02 00 00           515
            intArray{30] : 10 02 00 00           528
            intArray{31] : 56 02 00 00           598
            intArray{32] : 64 02 00 00           612
            intArray{33] : 6E 02 00 00           622
            intArray{34] : 7E 02 00 00           638
            intArray{35] : 87 02 00 00           647
            intArray{36] : A1 02 00 00           673
            intArray{37] : B0 02 00 00           688
            intArray{38] : CC 02 00 00           716
            intArray{39] : D7 02 00 00           727
            intArray{40] : F7 02 00 00           759
            intArray{41] : 08 03 00 00           776
            intArray{42] : 1F 03 00 00           799
            intArray{43] : 29 03 00 00           809
            intArray{44] : 3E 03 00 00           830
            intArray{45] : 55 03 00 00           853
    ----++--size = 860
              readString : 18 18 72 65 73 2F 78 6D 6C 2F 70 72 65 66 5F 67 65 6E 65 72 61 6C 2E 78 6D 6C 00 1D 1D 72 65 73 2F 78 6D 6C 2F 70 72 65 66 5F 6E 6F 74 69 66 69 63 61 74 69 6F 6E 2E 78 6D 6C 00 18 18 72 65 73 2F 78 6D 6C 2F 70 72 65 66 5F 68 65 61 64 65 72 73 2E 78 6D 6C 00 1A 1A 72 65 73 2F 6C 61 79 6F 75 74 2F 61 63 74 69 76 69 74 79 5F 6D 79 2E 78 6D 6C 00 1B 1B 72 65 73 2F 6C 61 79 6F 75 74 2F 66 6C 6F 61 74 5F 77 69 6E 64 6F 77 2E 78 6D 6C 00 1A 1A 72 65 73 2F 78 6D 6C 2F 70 72 65 66 5F 64 61 74 61 5F 73 79 6E 63 2E 78 6D 6C 00 0F 0F 72 65 73 2F 6D 65 6E 75 2F 6D 79 2E 78 6D 6C 00 24 24 72 65 73 2F 64 72 61 77 61 62 6C 65 2D 6D 64 70 69 2D 76 34 2F 69 63 5F 6C 61 75 6E 63 68 65 72 2E 70 6E 67 00 24 24 72 65 73 2F 64 72 61 77 61 62 6C 65 2D 68 64 70 69 2D 76 34 2F 69 63 5F 6C 61 75 6E 63 68 65 72 2E 70 6E 67 00 25 25 72 65 73 2F 64 72 61 77 61 62 6C 65 2D 78 68 64 70 69 2D 76 34 2F 69 63 5F 6C 61 75 6E 63 68 65 72 2E 70 6E 67 00 26 26 72 65 73 2F 64 72 61 77 61 62 6C 65 2D 78 78 68 64 70 69 2D 76 34 2F 69 63 5F 6C 61 75 6E 63 68 65 72 2E 70 6E 67 00 0D 0D 57 68 65 6E 20 70 6F 73 73 69 62 6C 65 00 05 05 4E 65 76 65 72 00 01 01 31 00 01 01 30 00 02 02 2D 31 00 06 06 41 6C 77 61 79 73 00 0A 0A 33 30 20 6D 69 6E 75 74 65 73 00 06 06 31 20 68 6F 75 72 00 07 07 33 20 68 6F 75 72 73 00 07 07 36 20 68 6F 75 72 73 00 02 02 31 35 00 02 02 33 30 00 0A 0A 31 35 20 6D 69 6E 75 74 65 73 00 03 03 31 38 30 00 03 03 33 36 30 00 08 08 53 65 74 74 69 6E 67 73 00 0C 0C 44 65 76 41 73 73 69 73 74 61 6E 74 00 0C 0C 48 65 6C 6C 6F 20 77 6F 72 6C 64 21 00 0A 0A 4A 6F 68 6E 20 53 6D 69 74 68 00 43 43 52 65 63 6F 6D 6D 65 6E 64 61 74 69 6F 6E 73 20 66 6F 72 20 70 65 6F 70 6C 65 20 74 6F 20 63 6F 6E 74 61 63 74 20 62 61 73 65 64 20 6F 6E 20 79 6F 75 72 20 6D 65 73 73 61 67 65 20 68 69 73 74 6F 72 79 00 0B 0B 44 61 74 61 20 26 20 73 79 6E 63 00 07 07 47 65 6E 65 72 61 6C 00 0D 0D 4E 6F 74 69 66 69 63 61 74 69 6F 6E 73 00 06 06 53 69 6C 65 6E 74 00 17 17 41 64 64 20 66 72 69 65 6E 64 73 20 74 6F 20 6D 65 73 73 61 67 65 73 00 0C 0C 44 69 73 70 6C 61 79 20 6E 61 6D 65 00 19 19 4E 65 77 20 6D 65 73 73 61 67 65 20 6E 6F 74 69 66 69 63 61 74 69 6F 6E 73 00 08 08 52 69 6E 67 74 6F 6E 65 00 1D 1D 45 6E 61 62 6C 65 20 73 6F 63 69 61 6C 20 72 65 63 6F 6D 6D 65 6E 64 61 74 69 6F 6E 73 00 0E 0E 53 79 6E 63 20 66 72 65 71 75 65 6E 63 79 00 14 14 53 79 73 74 65 6D 20 73 79 6E 63 20 73 65 74 74 69 6E 67 73 00 07 07 56 69 62 72 61 74 65 00 06 12 E6 98 BE E7 A4 BA E6 B5 AE E5 8A A8 E6 97 A5 E5 BF 97 00 0C 14 E6 98 BE E7 A4 BA E6 B5 AE E5 8A A8 41 63 74 69 76 69 74 79 00 02 02 36 30 00 00 00

     entry=== res/xml/pref_general.xml

     entry=== res/xml/pref_notification.xml

     entry=== res/xml/pref_headers.xml

     entry=== res/layout/activity_my.xml

     entry=== res/layout/float_window.xml

     entry=== res/xml/pref_data_sync.xml

     entry=== res/menu/my.xml

     entry=== res/drawable-mdpi-v4/ic_launcher.png

     entry=== res/drawable-hdpi-v4/ic_launcher.png

     entry=== res/drawable-xhdpi-v4/ic_launcher.png

     entry=== res/drawable-xxhdpi-v4/ic_launcher.png

     entry=== When possible

     entry=== Never

     entry=== 1

     entry=== 0

     entry=== -1

     entry=== Always

     entry=== 30 minutes

     entry=== 1 hour

     entry=== 3 hours

     entry=== 6 hours

     entry=== 15

     entry=== 30

     entry=== 15 minutes

     entry=== 180

     entry=== 360

     entry=== Settings

     entry=== DevAssistant

     entry=== Hello world!

     entry=== John Smith

     entry=== Recommendations for people to contact based on your message history

     entry=== Data & sync

     entry=== General

     entry=== Notifications

     entry=== Silent

     entry=== Add friends to messages

     entry=== Display name

     entry=== New message notifications

     entry=== Ringtone

     entry=== Enable social recommendations

     entry=== Sync frequency

     entry=== System sync settings

     entry=== Vibrate

     entry=== 显示浮动日志

     entry=== 显示浮动Activity

     entry=== 60
    "...RES_TABLE_PACKAGE_TYPE.............................."
                  type:  : 00 02           512
            headerSize:  : 1C 01           284
                  size:  : 48 10 00 00           4168
                    id:  : 7F 00 00 00           127
    63 00 6F 00 6D 00 2E 00 78 00 69 00 6E 00 67 00 68 00 75 00 69 00 2E 00 64 00 65 00 76 00 61 00 73 00 73 00 69 00 73 00 74 00 61 00 6E 00 74 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
    com.xinghui.devassistant
           typeStrings:  : 1C 01 00 00           284
        lastPublicType:  : 0B 00 00 00           11
            keyStrings:  : BC 01 00 00           444
         lastPublicKey:  : 2F 00 00 00           47
    "...Type String Pool.............................."
                  type:  : 01 00           1
            headerSize:  : 1C 00           28
                  size:  : A0 00 00 00           160
           stringCount:  : 0B 00 00 00           11
            styleCount:  : 00 00 00 00           0
                 flags:  : 00 01 00 00           256
          stringsStart:  : 48 00 00 00           72
           stylesStart:  : 00 00 00 00           0
    SORTED_FLAG = 1
    UTF8_FLAG = 256
             intArray{0] : 00 00 00 00           0
             intArray{1] : 07 00 00 00           7
             intArray{2] : 12 00 00 00           18
             intArray{3] : 1B 00 00 00           27
             intArray{4] : 21 00 00 00           33
             intArray{5] : 29 00 00 00           41
             intArray{6] : 31 00 00 00           49
             intArray{7] : 39 00 00 00           57
             intArray{8] : 42 00 00 00           66
             intArray{9] : 4A 00 00 00           74
            intArray{10] : 51 00 00 00           81
    ----++--size = 88
              readString : 04 04 61 74 74 72 00 08 08 64 72 61 77 61 62 6C 65 00 06 06 6C 61 79 6F 75 74 00 03 03 78 6D 6C 00 05 05 61 72 72 61 79 00 05 05 63 6F 6C 6F 72 00 05 05 64 69 6D 65 6E 00 06 06 73 74 72 69 6E 67 00 05 05 73 74 79 6C 65 00 04 04 6D 65 6E 75 00 02 02 69 64 00 00 00

     entry=== attr

     entry=== drawable

     entry=== layout

     entry=== xml

     entry=== array

     entry=== color

     entry=== dimen

     entry=== string

     entry=== style

     entry=== menu

     entry=== id
    "...Key String Pool.............................."
                  type:  : 01 00           1
            headerSize:  : 1C 00           28
                  size:  : B8 04 00 00           1208
           stringCount:  : 2F 00 00 00           47
            styleCount:  : 00 00 00 00           0
                 flags:  : 00 01 00 00           256
          stringsStart:  : D8 00 00 00           216
           stylesStart:  : 00 00 00 00           0
    SORTED_FLAG = 1
    UTF8_FLAG = 256
             intArray{0] : 00 00 00 00           0
             intArray{1] : 10 00 00 00           16
             intArray{2] : 23 00 00 00           35
             intArray{3] : 32 00 00 00           50
             intArray{4] : 44 00 00 00           68
             intArray{5] : 52 00 00 00           82
             intArray{6] : 60 00 00 00           96
             intArray{7] : 6F 00 00 00           111
             intArray{8] : 80 00 00 00           128
             intArray{9] : 8F 00 00 00           143
            intArray{10] : 9E 00 00 00           158
            intArray{11] : B2 00 00 00           178
            intArray{12] : CD 00 00 00           205
            intArray{13] : E8 00 00 00           232
            intArray{14] : 05 01 00 00           261
            intArray{15] : 22 01 00 00           290
            intArray{16] : 30 01 00 00           304
            intArray{17] : 3F 01 00 00           319
            intArray{18] : 5C 01 00 00           348
            intArray{19] : 77 01 00 00           375
            intArray{20] : 89 01 00 00           393
            intArray{21] : 94 01 00 00           404
            intArray{22] : A2 01 00 00           418
            intArray{23] : BE 01 00 00           446
            intArray{24] : E8 01 00 00           488
            intArray{25] : 00 02 00 00           512
            intArray{26] : 16 02 00 00           534
            intArray{27] : 32 02 00 00           562
            intArray{28] : 49 02 00 00           585
            intArray{29] : 6E 02 00 00           622
            intArray{30] : 88 02 00 00           648
            intArray{31] : AF 02 00 00           687
            intArray{32] : C5 02 00 00           709
            intArray{33] : E9 02 00 00           745
            intArray{34] : 05 03 00 00           773
            intArray{35] : 27 03 00 00           807
            intArray{36] : 3C 03 00 00           828
            intArray{37] : 52 03 00 00           850
            intArray{38] : 64 03 00 00           868
            intArray{39] : 7E 03 00 00           894
            intArray{40] : 89 03 00 00           905
            intArray{41] : 8E 03 00 00           910
            intArray{42] : 96 03 00 00           918
            intArray{43] : A0 03 00 00           928
            intArray{44] : AB 03 00 00           939
            intArray{45] : BE 03 00 00           958
            intArray{46] : CD 03 00 00           973
    ----++--size = 992
              readString : 0D 0D 65 78 61 6D 70 6C 65 53 74 72 69 6E 67 00 10 10 65 78 61 6D 70 6C 65 44 69 6D 65 6E 73 69 6F 6E 00 0C 0C 65 78 61 6D 70 6C 65 43 6F 6C 6F 72 00 0F 0F 65 78 61 6D 70 6C 65 44 72 61 77 61 62 6C 65 00 0B 0B 69 63 5F 6C 61 75 6E 63 68 65 72 00 0B 0B 61 63 74 69 76 69 74 79 5F 6D 79 00 0C 0C 66 6C 6F 61 74 5F 77 69 6E 64 6F 77 00 0E 0E 70 72 65 66 5F 64 61 74 61 5F 73 79 6E 63 00 0C 0C 70 72 65 66 5F 67 65 6E 65 72 61 6C 00 0C 0C 70 72 65 66 5F 68 65 61 64 65 72 73 00 11 11 70 72 65 66 5F 6E 6F 74 69 66 69 63 61 74 69 6F 6E 00 18 18 70 72 65 66 5F 65 78 61 6D 70 6C 65 5F 6C 69 73 74 5F 74 69 74 6C 65 73 00 18 18 70 72 65 66 5F 65 78 61 6D 70 6C 65 5F 6C 69 73 74 5F 76 61 6C 75 65 73 00 1A 1A 70 72 65 66 5F 73 79 6E 63 5F 66 72 65 71 75 65 6E 63 79 5F 74 69 74 6C 65 73 00 1A 1A 70 72 65 66 5F 73 79 6E 63 5F 66 72 65 71 75 65 6E 63 79 5F 76 61 6C 75 65 73 00 0B 0B 74 72 61 6E 73 5F 62 6C 61 63 6B 00 0C 0C 74 72 61 6E 73 5F 6F 72 61 6E 67 65 00 1A 1A 61 63 74 69 76 69 74 79 5F 68 6F 72 69 7A 6F 6E 74 61 6C 5F 6D 61 72 67 69 6E 00 18 18 61 63 74 69 76 69 74 79 5F 76 65 72 74 69 63 61 6C 5F 6D 61 72 67 69 6E 00 0F 0F 61 63 74 69 6F 6E 5F 73 65 74 74 69 6E 67 73 00 08 08 61 70 70 5F 6E 61 6D 65 00 0B 0B 68 65 6C 6C 6F 5F 77 6F 72 6C 64 00 19 19 70 72 65 66 5F 64 65 66 61 75 6C 74 5F 64 69 73 70 6C 61 79 5F 6E 61 6D 65 00 27 27 70 72 65 66 5F 64 65 73 63 72 69 70 74 69 6F 6E 5F 73 6F 63 69 61 6C 5F 72 65 63 6F 6D 6D 65 6E 64 61 74 69 6F 6E 73 00 15 15 70 72 65 66 5F 68 65 61 64 65 72 5F 64 61 74 61 5F 73 79 6E 63 00 13 13 70 72 65 66 5F 68 65 61 64 65 72 5F 67 65 6E 65 72 61 6C 00 19 19 70 72 65 66 5F 68 65 61 64 65 72 5F 6E 6F 74 69 66 69 63 61 74 69 6F 6E 73 00 14 14 70 72 65 66 5F 72 69 6E 67 74 6F 6E 65 5F 73 69 6C 65 6E 74 00 22 22 70 72 65 66 5F 74 69 74 6C 65 5F 61 64 64 5F 66 72 69 65 6E 64 73 5F 74 6F 5F 6D 65 73 73 61 67 65 73 00 17 17 70 72 65 66 5F 74 69 74 6C 65 5F 64 69 73 70 6C 61 79 5F 6E 61 6D 65 00 24 24 70 72 65 66 5F 74 69 74 6C 65 5F 6E 65 77 5F 6D 65 73 73 61 67 65 5F 6E 6F 74 69 66 69 63 61 74 69 6F 6E 73 00 13 13 70 72 65 66 5F 74 69 74 6C 65 5F 72 69 6E 67 74 6F 6E 65 00 21 21 70 72 65 66 5F 74 69 74 6C 65 5F 73 6F 63 69 61 6C 5F 72 65 63 6F 6D 6D 65 6E 64 61 74 69 6F 6E 73 00 19 19 70 72 65 66 5F 74 69 74 6C 65 5F 73 79 6E 63 5F 66 72 65 71 75 65 6E 63 79 00 1F 1F 70 72 65 66 5F 74 69 74 6C 65 5F 73 79 73 74 65 6D 5F 73 79 6E 63 5F 73 65 74 74 69 6E 67 73 00 12 12 70 72 65 66 5F 74 69 74 6C 65 5F 76 69 62 72 61 74 65 00 13 13 73 68 6F 77 5F 66 6C 6F 61 74 5F 6C 6F 67 5F 74 69 70 73 00 0F 0F 73 68 6F 77 5F 66 6C 6F 61 74 5F 74 69 70 73 00 17 17 74 69 74 6C 65 5F 61 63 74 69 76 69 74 79 5F 73 65 74 74 69 6E 67 73 00 08 08 41 70 70 54 68 65 6D 65 00 02 02 6D 79 00 05 05 74 65 78 74 31 00 07 07 73 77 69 74 63 68 31 00 08 08 6C 6F 67 5F 69 74 65 6D 00 10 10 66 6C 6F 61 74 5F 6C 6F 67 5F 73 77 69 74 63 68 00 0C 0C 70 61 63 6B 61 67 65 5F 6E 61 6D 65 00 0D 0D 61 63 74 69 76 69 74 79 5F 6E 61 6D 65 00 00 00 00

     entry=== exampleString

     entry=== exampleDimension

     entry=== exampleColor

     entry=== exampleDrawable

     entry=== ic_launcher

     entry=== activity_my

     entry=== float_window

     entry=== pref_data_sync

     entry=== pref_general

     entry=== pref_headers

     entry=== pref_notification

     entry=== pref_example_list_titles

     entry=== pref_example_list_values

     entry=== pref_sync_frequency_titles

     entry=== pref_sync_frequency_values

     entry=== trans_black

     entry=== trans_orange

     entry=== activity_horizontal_margin

     entry=== activity_vertical_margin

     entry=== action_settings

     entry=== app_name

     entry=== hello_world

     entry=== pref_default_display_name

     entry=== pref_description_social_recommendations

     entry=== pref_header_data_sync

     entry=== pref_header_general

     entry=== pref_header_notifications

     entry=== pref_ringtone_silent

     entry=== pref_title_add_friends_to_messages

     entry=== pref_title_display_name

     entry=== pref_title_new_message_notifications

     entry=== pref_title_ringtone

     entry=== pref_title_social_recommendations

     entry=== pref_title_sync_frequency

     entry=== pref_title_system_sync_settings

     entry=== pref_title_vibrate

     entry=== show_float_log_tips

     entry=== show_float_tips

     entry=== title_activity_settings

     entry=== AppTheme

     entry=== my

     entry=== text1

     entry=== switch1

     entry=== log_item

     entry=== float_log_switch

     entry=== package_name

     entry=== activity_name
                  type:  : 02 02           514
            headerSize:  : 10 00           16
                  size:  : 20 00 00 00           32
    "...Header.............................."
    "...RES_TABLE_TYPE_SPEC_TYPE.............................."
                     id: : 01           1
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 04 00 00 00           4
    typeSpec ---- attr
        attr - uint32_t: : 00 00 00 00           0
        attr - uint32_t: : 00 00 00 00           0
        attr - uint32_t: : 00 00 00 00           0
        attr - uint32_t: : 00 00 00 00           0
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : B8 00 00 00           184
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 01           1
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 04 00 00 00           4
           entriesStart: : 48 00 00 00           72
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 00 00

             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 00 00           0
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
         entryOffset[1]: : 1C 00 00 00           28
         entryOffset[2]: : 38 00 00 00           56
         entryOffset[3]: : 54 00 00 00           84
                   size: : 10 00           16
                  flags: : 01 00           1
                 index:  : 00 00 00 00           0
                  ident: : 00 00 00 00           0
                  count: : 01 00 00 00           1
    ResTable_map_entry---1
                  ident: : 00 00 00 01           16777216
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 10           16
                  data:  : 02 00 00 00           2
    readComplexEntry method ---type = 16, data = 2,----i = 0
                   size: : 10 00           16
                  flags: : 01 00           1
                 index:  : 01 00 00 00           1
                  ident: : 00 00 00 00           0
                  count: : 01 00 00 00           1
    ResTable_map_entry---1
                  ident: : 00 00 00 01           16777216
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 10           16
                  data:  : 40 00 00 00           64
    readComplexEntry method ---type = 16, data = 64,----i = 0
                   size: : 10 00           16
                  flags: : 01 00           1
                 index:  : 02 00 00 00           2
                  ident: : 00 00 00 00           0
                  count: : 01 00 00 00           1
    ResTable_map_entry---1
                  ident: : 00 00 00 01           16777216
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 10           16
                  data:  : 10 00 00 00           16
    readComplexEntry method ---type = 16, data = 16,----i = 0
                   size: : 10 00           16
                  flags: : 01 00           1
                 index:  : 03 00 00 00           3
                  ident: : 00 00 00 00           0
                  count: : 01 00 00 00           1
    ResTable_map_entry---1
                  ident: : 00 00 00 01           16777216
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 10           16
                  data:  : 11 00 00 00           17
    readComplexEntry method ---type = 16, data = 17,----i = 0
                  type:  : 02 02           514
            headerSize:  : 10 00           16
                  size:  : 14 00 00 00           20
    "...Header.............................."
    "...RES_TABLE_TYPE_SPEC_TYPE.............................."
                     id: : 02           2
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 01 00 00 00           1
    typeSpec ---- drawable
    drawable - uint32_t: : 00 01 00 00           256
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 4C 00 00 00           76
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 02           2
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 01 00 00 00           1
           entriesStart: : 3C 00 00 00           60
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 A0 00
     
             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 04 00           4
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 04 00 00 00           4
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 07 00 00 00           7
    readValue method type = 3, data = null
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 4C 00 00 00           76
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 02           2
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 01 00 00 00           1
           entriesStart: : 3C 00 00 00           60
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 F0 00
    ð
             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 04 00           4
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 04 00 00 00           4
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 08 00 00 00           8
    readValue method type = 3, data = null
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 4C 00 00 00           76
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 02           2
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 01 00 00 00           1
           entriesStart: : 3C 00 00 00           60
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 40 01
    ŀ
             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 04 00           4
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 04 00 00 00           4
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 09 00 00 00           9
    readValue method type = 3, data = null
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 4C 00 00 00           76
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 02           2
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 01 00 00 00           1
           entriesStart: : 3C 00 00 00           60
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 E0 01
    Ǡ
             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 04 00           4
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 04 00 00 00           4
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 0A 00 00 00           10
    readValue method type = 3, data = null
                  type:  : 02 02           514
            headerSize:  : 10 00           16
                  size:  : 18 00 00 00           24
    "...Header.............................."
    "...RES_TABLE_TYPE_SPEC_TYPE.............................."
                     id: : 03           3
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 02 00 00 00           2
    typeSpec ---- layout
      layout - uint32_t: : 00 00 00 00           0
      layout - uint32_t: : 00 00 00 00           0
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 60 00 00 00           96
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 03           3
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 02 00 00 00           2
           entriesStart: : 40 00 00 00           64
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 00 00

             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 00 00           0
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
         entryOffset[1]: : 10 00 00 00           16
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 05 00 00 00           5
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 03 00 00 00           3
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 06 00 00 00           6
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 04 00 00 00           4
    readValue method type = 3, data = null
                  type:  : 02 02           514
            headerSize:  : 10 00           16
                  size:  : 20 00 00 00           32
    "...Header.............................."
    "...RES_TABLE_TYPE_SPEC_TYPE.............................."
                     id: : 04           4
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 04 00 00 00           4
    typeSpec ---- xml
         xml - uint32_t: : 00 00 00 00           0
         xml - uint32_t: : 00 00 00 00           0
         xml - uint32_t: : 00 00 00 00           0
         xml - uint32_t: : 00 00 00 00           0
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 88 00 00 00           136
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 04           4
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 04 00 00 00           4
           entriesStart: : 48 00 00 00           72
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 00 00

             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 00 00           0
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
         entryOffset[1]: : 10 00 00 00           16
         entryOffset[2]: : 20 00 00 00           32
         entryOffset[3]: : 30 00 00 00           48
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 07 00 00 00           7
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 05 00 00 00           5
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 08 00 00 00           8
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 00 00 00 00           0
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 09 00 00 00           9
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 02 00 00 00           2
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 0A 00 00 00           10
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 01 00 00 00           1
    readValue method type = 3, data = null
                  type:  : 02 02           514
            headerSize:  : 10 00           16
                  size:  : 20 00 00 00           32
    "...Header.............................."
    "...RES_TABLE_TYPE_SPEC_TYPE.............................."
                     id: : 05           5
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 04 00 00 00           4
    typeSpec ---- array
       array - uint32_t: : 00 00 00 00           0
       array - uint32_t: : 00 00 00 00           0
       array - uint32_t: : 00 00 00 00           0
       array - uint32_t: : 00 00 00 00           0
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 60 01 00 00           352
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 05           5
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 04 00 00 00           4
           entriesStart: : 48 00 00 00           72
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 00 00

             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 00 00           0
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
         entryOffset[1]: : 34 00 00 00           52
         entryOffset[2]: : 68 00 00 00           104
         entryOffset[3]: : C0 00 00 00           192
                   size: : 10 00           16
                  flags: : 01 00           1
                 index:  : 0B 00 00 00           11
                  ident: : 00 00 00 00           0
                  count: : 03 00 00 00           3
    ResTable_map_entry---3
                  ident: : 00 00 00 02           33554432
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 10 00 00 00           16
    readComplexEntry method ---type = 3, data = null,----i = 0
                  ident: : 01 00 00 02           33554433
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 0B 00 00 00           11
    readComplexEntry method ---type = 3, data = null,----i = 1
                  ident: : 02 00 00 02           33554434
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 0C 00 00 00           12
    readComplexEntry method ---type = 3, data = null,----i = 2
                   size: : 10 00           16
                  flags: : 01 00           1
                 index:  : 0C 00 00 00           12
                  ident: : 00 00 00 00           0
                  count: : 03 00 00 00           3
    ResTable_map_entry---3
                  ident: : 00 00 00 02           33554432
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 0D 00 00 00           13
    readComplexEntry method ---type = 3, data = null,----i = 0
                  ident: : 01 00 00 02           33554433
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 0E 00 00 00           14
    readComplexEntry method ---type = 3, data = null,----i = 1
                  ident: : 02 00 00 02           33554434
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 0F 00 00 00           15
    readComplexEntry method ---type = 3, data = null,----i = 2
                   size: : 10 00           16
                  flags: : 01 00           1
                 index:  : 0D 00 00 00           13
                  ident: : 00 00 00 00           0
                  count: : 06 00 00 00           6
    ResTable_map_entry---6
                  ident: : 00 00 00 02           33554432
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 17 00 00 00           23
    readComplexEntry method ---type = 3, data = null,----i = 0
                  ident: : 01 00 00 02           33554433
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 11 00 00 00           17
    readComplexEntry method ---type = 3, data = null,----i = 1
                  ident: : 02 00 00 02           33554434
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 12 00 00 00           18
    readComplexEntry method ---type = 3, data = null,----i = 2
                  ident: : 03 00 00 02           33554435
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 13 00 00 00           19
    readComplexEntry method ---type = 3, data = null,----i = 3
                  ident: : 04 00 00 02           33554436
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 14 00 00 00           20
    readComplexEntry method ---type = 3, data = null,----i = 4
                  ident: : 05 00 00 02           33554437
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 0C 00 00 00           12
    readComplexEntry method ---type = 3, data = null,----i = 5
                   size: : 10 00           16
                  flags: : 01 00           1
                 index:  : 0E 00 00 00           14
                  ident: : 00 00 00 00           0
                  count: : 06 00 00 00           6
    ResTable_map_entry---6
                  ident: : 00 00 00 02           33554432
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 15 00 00 00           21
    readComplexEntry method ---type = 3, data = null,----i = 0
                  ident: : 01 00 00 02           33554433
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 16 00 00 00           22
    readComplexEntry method ---type = 3, data = null,----i = 1
                  ident: : 02 00 00 02           33554434
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 2D 00 00 00           45
    readComplexEntry method ---type = 3, data = null,----i = 2
                  ident: : 03 00 00 02           33554435
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 18 00 00 00           24
    readComplexEntry method ---type = 3, data = null,----i = 3
                  ident: : 04 00 00 02           33554436
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 19 00 00 00           25
    readComplexEntry method ---type = 3, data = null,----i = 4
                  ident: : 05 00 00 02           33554437
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 0F 00 00 00           15
    readComplexEntry method ---type = 3, data = null,----i = 5
                  type:  : 02 02           514
            headerSize:  : 10 00           16
                  size:  : 18 00 00 00           24
    "...Header.............................."
    "...RES_TABLE_TYPE_SPEC_TYPE.............................."
                     id: : 06           6
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 02 00 00 00           2
    typeSpec ---- color
       color - uint32_t: : 00 00 00 00           0
       color - uint32_t: : 00 00 00 00           0
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 60 00 00 00           96
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 06           6
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 02 00 00 00           2
           entriesStart: : 40 00 00 00           64
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 00 00

             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 00 00           0
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
         entryOffset[1]: : 10 00 00 00           16
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 0F 00 00 00           15
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 1C           28
                  data:  : 00 00 00 66           1711276032
    readValue method type = 28, data = #66000000
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 10 00 00 00           16
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 1C           28
                  data:  : 33 BB FF 66           1728035635
    readValue method type = 28, data = #66ffbb33
                  type:  : 02 02           514
            headerSize:  : 10 00           16
                  size:  : 18 00 00 00           24
    "...Header.............................."
    "...RES_TABLE_TYPE_SPEC_TYPE.............................."
                     id: : 07           7
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 02 00 00 00           2
    typeSpec ---- dimen
       dimen - uint32_t: : 00 06 00 00           1536
       dimen - uint32_t: : 00 00 00 00           0
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 60 00 00 00           96
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 07           7
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 02 00 00 00           2
           entriesStart: : 40 00 00 00           64
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 00 00

             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 00 00           0
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
         entryOffset[1]: : 10 00 00 00           16
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 11 00 00 00           17
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 05           5
                  data:  : 01 10 00 00           4097
    readValue method type = 5, data = 16.0dip
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 12 00 00 00           18
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 05           5
                  data:  : 01 10 00 00           4097
    readValue method type = 5, data = 16.0dip
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 50 00 00 00           80
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 07           7
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 02 00 00 00           2
           entriesStart: : 40 00 00 00           64
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 00 00

             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 0D 00           13
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 34           52
                  uiMode : 03           3
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
         entryOffset[1]: : FF FF FF FF           -1
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 11 00 00 00           17
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 05           5
                  data:  : 01 40 00 00           16385
    readValue method type = 5, data = 64.0dip
                  type:  : 02 02           514
            headerSize:  : 10 00           16
                  size:  : 60 00 00 00           96
    "...Header.............................."
    "...RES_TABLE_TYPE_SPEC_TYPE.............................."
                     id: : 08           8
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 14 00 00 00           20
    typeSpec ---- string
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
      string - uint32_t: : 00 00 00 00           0
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : C8 01 00 00           456
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 08           8
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 14 00 00 00           20
           entriesStart: : 88 00 00 00           136
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 00 00

             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 00 00           0
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
         entryOffset[1]: : 10 00 00 00           16
         entryOffset[2]: : 20 00 00 00           32
         entryOffset[3]: : 30 00 00 00           48
         entryOffset[4]: : 40 00 00 00           64
         entryOffset[5]: : 50 00 00 00           80
         entryOffset[6]: : 60 00 00 00           96
         entryOffset[7]: : 70 00 00 00           112
         entryOffset[8]: : 80 00 00 00           128
         entryOffset[9]: : 90 00 00 00           144
        entryOffset[10]: : A0 00 00 00           160
        entryOffset[11]: : B0 00 00 00           176
        entryOffset[12]: : C0 00 00 00           192
        entryOffset[13]: : D0 00 00 00           208
        entryOffset[14]: : E0 00 00 00           224
        entryOffset[15]: : F0 00 00 00           240
        entryOffset[16]: : 00 01 00 00           256
        entryOffset[17]: : 10 01 00 00           272
        entryOffset[18]: : 20 01 00 00           288
        entryOffset[19]: : 30 01 00 00           304
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 13 00 00 00           19
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 1A 00 00 00           26
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 14 00 00 00           20
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 1B 00 00 00           27
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 15 00 00 00           21
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 1C 00 00 00           28
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 16 00 00 00           22
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 1D 00 00 00           29
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 17 00 00 00           23
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 1E 00 00 00           30
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 18 00 00 00           24
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 1F 00 00 00           31
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 19 00 00 00           25
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 20 00 00 00           32
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 1A 00 00 00           26
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 21 00 00 00           33
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 1B 00 00 00           27
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 22 00 00 00           34
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 1C 00 00 00           28
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 23 00 00 00           35
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 1D 00 00 00           29
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 24 00 00 00           36
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 1E 00 00 00           30
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 25 00 00 00           37
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 1F 00 00 00           31
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 26 00 00 00           38
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 20 00 00 00           32
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 27 00 00 00           39
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 21 00 00 00           33
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 28 00 00 00           40
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 22 00 00 00           34
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 29 00 00 00           41
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 23 00 00 00           35
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 2A 00 00 00           42
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 24 00 00 00           36
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 2B 00 00 00           43
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 25 00 00 00           37
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 2C 00 00 00           44
    readValue method type = 3, data = null
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 26 00 00 00           38
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 1A 00 00 00           26
    readValue method type = 3, data = null
                  type:  : 02 02           514
            headerSize:  : 10 00           16
                  size:  : 14 00 00 00           20
    "...Header.............................."
    "...RES_TABLE_TYPE_SPEC_TYPE.............................."
                     id: : 09           9
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 01 00 00 00           1
    typeSpec ---- style
       style - uint32_t: : 00 00 00 00           0
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 4C 00 00 00           76
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 09           9
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 01 00 00 00           1
           entriesStart: : 3C 00 00 00           60
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 00 00

             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 00 00           0
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
                   size: : 10 00           16
                  flags: : 01 00           1
                 index:  : 27 00 00 00           39
                  ident: : 19 01 03 01           16974105
                  count: : 00 00 00 00           0
    ResTable_map_entry---0
                  type:  : 02 02           514
            headerSize:  : 10 00           16
                  size:  : 14 00 00 00           20
    "...Header.............................."
    "...RES_TABLE_TYPE_SPEC_TYPE.............................."
                     id: : 0A           10
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 01 00 00 00           1
    typeSpec ---- menu
        menu - uint32_t: : 00 00 00 00           0
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : 4C 00 00 00           76
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 0A           10
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 01 00 00 00           1
           entriesStart: : 3C 00 00 00           60
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 00 00

             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 00 00           0
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 28 00 00 00           40
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 03           3
                  data:  : 06 00 00 00           6
    readValue method type = 3, data = null
                  type:  : 02 02           514
            headerSize:  : 10 00           16
                  size:  : 2C 00 00 00           44
    "...Header.............................."
    "...RES_TABLE_TYPE_SPEC_TYPE.............................."
                     id: : 0B           11
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 07 00 00 00           7
    typeSpec ---- id
          id - uint32_t: : 00 00 00 00           0
          id - uint32_t: : 00 00 00 00           0
          id - uint32_t: : 00 00 00 00           0
          id - uint32_t: : 00 00 00 00           0
          id - uint32_t: : 00 00 00 00           0
          id - uint32_t: : 00 00 00 00           0
          id - uint32_t: : 00 00 00 00           0
                  type:  : 01 02           513
            headerSize:  : 38 00           56
                  size:  : C4 00 00 00           196
    "...RES_TABLE_TYPE_TYPE.............................."
                     id: : 0B           11
                   res0: : 00           0
                   res1: : 00 00           0
             entryCount: : 07 00 00 00           7
           entriesStart: : 54 00 00 00           84
                   size: : 24 00 00 00           36
                    mcc: : 00 00           0
                    mnc: : 00 00           0
    00 00 00 00

    00 00 00 00

             orientation : 00           0
             touchscreen : 00           0
                density: : 00 00           0
                keyboard : 00           0
              navigation : 00           0
              inputFlags : 00           0
               inputPad0 : 00           0
            screenWidth: : 00 00           0
           screenHeight: : 00 00           0
             sdkVersion: : 00 00           0
           minorVersion: : 00 00           0
            screenLayout : 00           0
                  uiMode : 00           0
    smallestScreenWidthDp: : 00 00           0
    ResTable_config size = 36
    null
         entryOffset[0]: : 00 00 00 00           0
         entryOffset[1]: : 10 00 00 00           16
         entryOffset[2]: : 20 00 00 00           32
         entryOffset[3]: : 30 00 00 00           48
         entryOffset[4]: : 40 00 00 00           64
         entryOffset[5]: : 50 00 00 00           80
         entryOffset[6]: : 60 00 00 00           96
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 29 00 00 00           41
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 12           18
                  data:  : 00 00 00 00           0
    readValue method type = 18, data = false
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 2A 00 00 00           42
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 12           18
                  data:  : 00 00 00 00           0
    readValue method type = 18, data = false
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 2B 00 00 00           43
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 12           18
                  data:  : 00 00 00 00           0
    readValue method type = 18, data = false
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 2C 00 00 00           44
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 12           18
                  data:  : 00 00 00 00           0
    readValue method type = 18, data = false
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 2D 00 00 00           45
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 12           18
                  data:  : 00 00 00 00           0
    readValue method type = 18, data = false
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 2E 00 00 00           46
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 12           18
                  data:  : 00 00 00 00           0
    readValue method type = 18, data = false
                   size: : 08 00           8
                  flags: : 00 00           0
                 index:  : 13 00 00 00           19
                  size:  : 08 00           8
                  res0:  : 00           0
              dataType:  : 12           18
                  data:  : 00 00 00 00           0
    readValue method type = 18, data = false


Libraries
-----------------------
Libraries: [com.squareup.okio:okio:1.0.1][1]

[1]: https://github.com/square/okio