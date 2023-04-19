package chat.frag;

import chat.FragType;
import chat.util.StructuredContentIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TextFrag extends DataFrag {
    public String content;

    public TextFrag() {
    }

    public TextFrag(String content) {
        this.content = content;
    }

    @Override
    public FragType getType() {
        return FragType.TEXT;
    }

    @Override
    public void read(InputStream input) throws IOException {
        content = StructuredContentIO.ReadText(input);
    }

    @Override
    public void write(OutputStream output) throws IOException {
        StructuredContentIO.SendText(output, content);
    }

    @Override
    public String toString() {
        return '"' + content + '"';
    }
}
