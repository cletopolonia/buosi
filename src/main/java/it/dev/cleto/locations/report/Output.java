package it.dev.cleto.locations.report;

import it.dev.cleto.locations.model.buosi.BuosiOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.io.*;
import java.util.Optional;

@Getter
@Setter
@Log4j
@NoArgsConstructor
public class Output {

    private static final String SEPARATOR = ";";
    public static final String LINE = System.lineSeparator();
    private String exportFileName;

    private File exportFile;

    public Output(String exportFileName) {
        this.exportFileName = exportFileName;
        exportFile = new File(getExportFileName());
        if (!exportFile.exists()) {
            try {
                exportFile.createNewFile();
                writeUsingOutputStream(header());
                log.info("csv report created");
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public String header() {
        String STEP = "step";
        String ID = "id";
        String NAME = "name";
        String SURNAME = "surname";
        String ADDRESS = "address";
        String CITY = "city";
        final String[] properties = new String[]{STEP, ID, NAME, SURNAME, ADDRESS, CITY};
        return String.join(SEPARATOR, properties) + LINE;
    }

    protected String toColumn(final Object object) {
        return Optional.ofNullable(object).map(o -> o + SEPARATOR).orElse(empty());
    }

    protected String empty() {
        return SEPARATOR;
    }

    private void writeUsingOutputStream(String data) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(getExportFile());
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public void addRow(BuosiOrder row) throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(exportFileName, true));
        output.append(rowBuilder(row));
        output.append(LINE);
        output.close();
    }

    protected String rowBuilder(BuosiOrder row) {
        final StringBuilder builder = new StringBuilder();
        builder.append(Optional.ofNullable(row.getStopOrder()).map(this::toColumn).orElse(empty()));
        builder.append(Optional.ofNullable(row.getId()).map(this::toColumn).orElse(empty()));
        builder.append(Optional.ofNullable(row.getName()).map(this::toColumn).orElse(empty()));
        builder.append(Optional.ofNullable(row.getSurname()).map(this::toColumn).orElse(empty()));
        builder.append(Optional.ofNullable(row.getCompleteAddress()).map(this::toColumn).orElse(empty()));
        builder.append(row.getCity());
        return builder.toString();
    }

    public void addLastRow(String route) throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(exportFileName, true));
        output.append(route);
        output.append(LINE);
        output.close();
    }
}
