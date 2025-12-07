package org.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVLoader {
    public static List<SaleRecord> load(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines
                    .skip(1)  // skip header
                    .map(line -> line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1))
                    .map(SaleRecord::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error reading CSV", e);
        }
    }

}
