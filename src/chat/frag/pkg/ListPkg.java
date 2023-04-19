package chat.frag.pkg;

import chat.FragType;
import chat.frag.DataFrag;
import chat.util.StructuredContentIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ListPkg extends AbstractPkg {
    public List<DataFrag> list;

    public ListPkg() {
        list = new ArrayList<>();
    }

    public ListPkg(List<DataFrag> list) {
        this.list = list;
    }

    @Override
    public FragType getType() {
        return FragType.LIST_PACKAGE;
    }

    @Override
    public void read(InputStream input) throws IOException {
        list.clear();
        int length = StructuredContentIO.ReadInt(input);
        for (int i = 0; i < length; i++) {
            DataFrag frag = StructuredContentIO.ReadDataFrag(input);
            list.add(frag);
        }
    }

    @Override
    public void write(OutputStream output) throws IOException {
        StructuredContentIO.SendInt(output, list.size());
        for (var item : list) {
            item.write(output);
        }
    }
}
