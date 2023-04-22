package chat.frag.pkg;

import chat.FragType;
import chat.frag.DataFrag;
import chat.frag.IntegerFrag;
import chat.frag.TextFrag;
import chat.util.StructuredContentIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class MapPkg extends AbstractPkg {
    protected Map<String, DataFrag> content;

    public MapPkg() {
        content = new HashMap<>();
    }

    private void readFrag(InputStream input) throws IOException {
        String name = StructuredContentIO.ReadText(input);
        DataFrag value = StructuredContentIO.ReadDataFrag(input);
        content.put(name, value);
    }

    private void writeFrag(OutputStream output, String name) throws IOException {
        StructuredContentIO.SendText(output, name);
        StructuredContentIO.SenDataFrag(output, content.get(name));
    }

    @Override
    public FragType getType() {
        return FragType.MAP_PACKAGE;
    }

    @Override
    public void read(InputStream input) throws IOException {
        content.clear();
        int fragNum = StructuredContentIO.ReadInt(input);
        for (int i = 0; i < fragNum; i++) {
            readFrag(input);
        }
    }

    @Override
    public void write(OutputStream output) throws IOException {
        StructuredContentIO.SendInt(output, content.size());
        for (var item : content.entrySet()) {
            writeFrag(output, item.getKey());
        }
    }

    public void PutFrag(String key, DataFrag data) {
        content.put(key, data);
    }

    public <T extends DataFrag> T GetFrag(String key) {
        return (T) content.get(key);
    }

    public void PutFrag(String key, String data) {
        content.put(key, new TextFrag(data));
    }

    public void PutFrag(String key, int data) {
        content.put(key, new IntegerFrag(data));
    }

    @Override
    public String toString() {
        return "MapPkg" + content;
    }
}
