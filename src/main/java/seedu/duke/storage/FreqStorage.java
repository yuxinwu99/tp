package seedu.duke.storage;

import seedu.duke.BusData;
import seedu.duke.BusStops;
import seedu.duke.exceptions.CustomException;
import seedu.duke.exceptions.ExceptionType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FreqStorage extends Storage {
    private File file;

    public FreqStorage(String dir) {
        super(dir);
        file = getFile();
    }

    @Override
    public void readFile() throws CustomException {
        try {
            loadFile();
        } catch (FileNotFoundException e) {
            throw new CustomException(ExceptionType.READ_FILE_FAIL);
        }
    }

    @Override
    public void updateFile() throws CustomException {
        ArrayList<Integer> frequencyList = BusData.getAllSearchCount();
        try {
            saveFile(frequencyList);
        } catch (IOException e) {
            throw new CustomException(ExceptionType.UPDATE_FILE_FAIL);
        }
    }

    private void saveFile(ArrayList<Integer> frequencyList) throws IOException {
        File savedFile = new File(dir);
        FileWriter writer = new FileWriter(savedFile);
        for (int i = 0; i < frequencyList.size(); i++) {
            String currFreq = Integer.toString(frequencyList.get(i));
            writer.write(currFreq + System.lineSeparator());
        }
        writer.close();
    }

    private void loadFile() throws FileNotFoundException {
        File savedFile = new File(dir);
        Scanner fileScanner = new Scanner(savedFile);
        int index = 0;
        while (fileScanner.hasNext()) {
            int currInt = Integer.parseInt(fileScanner.nextLine());
            BusStops.values()[index].setCount(currInt);
            index++;
        }
    }
}