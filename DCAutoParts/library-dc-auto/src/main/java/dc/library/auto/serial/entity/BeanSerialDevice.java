package dc.library.auto.serial.entity;

import java.io.File;

public class BeanSerialDevice {

    private String name;
    private String root;
    private File file;

    public BeanSerialDevice(String name, String root, File file) {
        this.file = file;
        this.name = name;
        this.root = root;
    }

    public String getName() {
        return name;
    }

    public String getRoot() {
        return root;
    }

    public File getFile() {
        return file;
    }
}
