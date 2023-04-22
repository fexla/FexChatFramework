package chat.util;

import chat.FragType;
import chat.frag.*;
import chat.frag.pkg.ListPkg;
import chat.frag.pkg.MapPkg;
import chat.frag.pkg.TaskPkg;

import java.io.*;

public class StructuredContentIO {
    public static void SendInt(OutputStream output, int num) throws IOException {
        DataOutputStream dos = new DataOutputStream(output);
        dos.writeInt(num);
    }

    public static int ReadInt(InputStream input) throws IOException {
        DataInputStream dis = new DataInputStream(input);
        return dis.readInt();
    }

    public static void SendText(OutputStream output, String content) throws IOException {
        DataOutputStream dos = new DataOutputStream(output);
        byte[] bytes = content.getBytes("UTF-8");
        dos.writeInt(bytes.length);
        output.write(bytes);
    }

    public static String ReadText(InputStream input) throws IOException {
        DataInputStream dis = new DataInputStream(input);
        int num = dis.readInt();
        byte[] bytes = new byte[40000];
        input.read(bytes, 0, num);
        String content = new String(bytes, 0, num, "UTF-8");
        return content;
    }

    public static boolean ReadBool(InputStream input) throws IOException {
        return input.read() == 1 ? true : false;
    }

    public static void SendBool(OutputStream outputStream, boolean bool) throws IOException {
        outputStream.write(bool ? 1 : 0);
    }

    public static byte[] ReadBytes(InputStream input) throws IOException {
        int length = ReadInt(input);
        return input.readNBytes(length);
    }

    public static void SendBytes(OutputStream outputStream, byte[] bytes) throws IOException {
        outputStream.write(bytes.length);
        outputStream.write(bytes);
    }

    public static void SendBytes(OutputStream outputStream, byte[] bytes, int offset, int length) throws IOException {
        outputStream.write(length);
        outputStream.write(bytes, offset, length);
    }


    public static MapPkg ReadAutoPkg(InputStream inputStream) throws IOException {
        MapPkg pkg = new MapPkg();
        pkg.read(inputStream);
        return pkg;
    }

    public static void SendAutoPkg(OutputStream outputStream, MapPkg pkg) throws IOException {
        pkg.write(outputStream);
    }

    public static TaskPkg ReadTaskPkg(InputStream inputStream) throws IOException {
        TaskPkg pkg = new TaskPkg();
        pkg.read(inputStream);
        return pkg;
    }

    public static DataFrag ReadDataFrag(InputStream input) throws IOException {
        FragType msgType = FragType.getType(input.read());
        DataFrag dataFrag = null;
        switch (msgType) {
            case ERROR, LOGIN, AUDIO -> {
            }
            case TEXT -> dataFrag = new TextFrag();
            case INT -> dataFrag = new IntegerFrag();
            case BYTES -> dataFrag = new BytesFrag();
            case MAP_PACKAGE -> dataFrag = new MapPkg();
            case LIST_PACKAGE -> dataFrag = new ListPkg();
        }
        dataFrag.read(input);
        return dataFrag;
    }

    public static void SenDataFrag(OutputStream output, DataFrag dataFrag) throws IOException {
        output.write(dataFrag.getType().code);
        dataFrag.write(output);
    }
}
