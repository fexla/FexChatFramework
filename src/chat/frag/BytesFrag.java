package chat.frag;

import chat.FragType;
import chat.util.StructuredContentIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BytesFrag extends DataFrag {
    public byte[] bytes;

    @Override
    public FragType getType() {
        return FragType.BYTES;
    }

    @Override
    public void read(InputStream input) throws IOException {
        bytes = StructuredContentIO.ReadBytes(input);
    }

    @Override
    public void write(OutputStream output) throws IOException {
        StructuredContentIO.SendBytes(output, bytes);
    }
}
