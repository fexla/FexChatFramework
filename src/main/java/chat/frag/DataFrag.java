package chat.frag;

import chat.FragType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 数据片段
 * 数据传输中的基本单元
 * 可以嵌套
 */
public abstract class DataFrag {
    public abstract FragType getType();

    public abstract void read(InputStream input) throws IOException;

    public abstract void write(OutputStream output) throws IOException;
}
