package it.dev.cleto.locations.report;

import it.dev.cleto.locations.model.buosi.BuosiOrder;
import it.dev.cleto.locations.model.buosi.BuosiResults;
import lombok.Data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static it.dev.cleto.locations.utils.BuosiUtils.CSV_SEPARATOR;

@Data
public class FileManager {
    public static List<BuosiOrder> readOrdersFromCSV(int prefix) throws FileNotFoundException {
        String inputFileName = "/home/biadmin/Downloads/b/" + prefix + "ordini.csv";
        List<BuosiOrder> orders = new ArrayList<>();
        Path pathToFile = Paths.get(inputFileName);
        if (!pathToFile.toFile().exists()) throw new FileNotFoundException(inputFileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(CSV_SEPARATOR);
                BuosiOrder order = BuosiOrder.from(attributes);
                orders.add(order);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return orders;
    }

    public static void writeOrdersInCsv(int prefix, BuosiResults buosiResults) throws IOException {
        Output export = new Output("/home/biadmin/Downloads/b/" + prefix + "output.csv");
        for (BuosiOrder order : buosiResults.getOrders())
            export.addRow(order);
        export.addLastRow(buosiResults.getRoute());
    }
}