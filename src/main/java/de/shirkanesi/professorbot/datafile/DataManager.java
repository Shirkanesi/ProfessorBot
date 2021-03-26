package de.shirkanesi.professorbot.datafile;

import de.shirkanesi.professorbot.ButterBrot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataManager {

    private final String path;
    private List<String> data;

    public DataManager(String path) {
        this.path = "data/" + path;
        this.data = read();
    }

    public List<String> add(String line) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (String l : this.data) {
                writer.write(l);
                writer.write(System.lineSeparator());
            }
            writer.write(line);
            writer.write(System.lineSeparator());
            writer.close();
            data.add(line);
        } catch (IOException e) {
            ButterBrot.LOGGER.error("DataFile not found! (writing)", e);
        }
        return data;
    }

    public List<String> read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            data = reader.lines().collect(Collectors.toList());
            reader.close();
        } catch (IOException e) {
            try {
                File f = new File(path);
                if (f.createNewFile()) {
                    ButterBrot.LOGGER.warn(String.format("DataFile has been created! [%s]", path));
                } else {
                    ButterBrot.LOGGER.warn(String.format("DataFile not created. [%s]", path));
                }
            } catch (IOException e1) {
                ButterBrot.LOGGER.error(String.format("DataFile not found! [%s]", path), e);
            }
        }
        return data;
    }

    public String getPath() {
        return path;
    }

    public List<String> getData() {
        return new ArrayList<>(data);
    }
}
