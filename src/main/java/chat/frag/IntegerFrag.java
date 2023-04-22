package chat.frag;

import chat.FragType;
import chat.util.StructuredContentIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IntegerFrag extends DataFrag {
    public int value;

    public IntegerFrag() {
    }

    public IntegerFrag(int value) {
        this.value = value;
    }

    @Override
    public FragType getType() {
        return FragType.INT;
    }

    @Override
    public void read(InputStream input) throws IOException {
        value = StructuredContentIO.ReadInt(input);
    }

    @Override
    public void write(OutputStream output) throws IOException {
        StructuredContentIO.SendInt(output, value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
