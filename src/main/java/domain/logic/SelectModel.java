package domain.logic;

import data.contracts.SelectContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectModel implements SelectContract.Model {

    @Override
    public List<String> loadModelList(String directoryPath) {
        return listFilesInDirectory(directoryPath);
    }

    @Override
    public List<String> loadDataList(String directoryPath) {
        return listFilesInDirectory(directoryPath);
    }

    private List<String> listFilesInDirectory(String directoryPath) {
        List<String> files = new ArrayList<>();
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile()) {
                    files.add(file.getName());
                }
            }
        }
        return files;
    }
}
