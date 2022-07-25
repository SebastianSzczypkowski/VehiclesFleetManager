package pl.szczypkowski.vehiclesfleetmanager;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.szczypkowski.vehiclesfleetmanager.utils.ExportExel;

import javax.persistence.Access;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExportToExel {


    private Logger LOGGER = LoggerFactory.getLogger(ExportToExel.class);


    @Test
    public void export()
    {
        Path exportFile = null;
        try {
            exportFile = Files.createTempFile("vehicle_raport" + System.currentTimeMillis(), ".xlsx");
        } catch (IOException e) {
           e.getMessage();
        }
        List<List<String>> exportRows = new ArrayList<>();
        exportRows.add(Arrays.asList("ID", "Name", "VIN", "Registration Number", "Car Mileage", "Car LoadCapacity",
                "Lorry Semitrailer", "Number Of Seats", "Engine Capacity",  "Average Fuel Consumption", "Roadworthy", "Occupied"));

        exportRows.add(Arrays.asList("1","MAN","1343fgi3o43o43fg","STA 1234","456707","20000 kg","yes","2","15.200","28.0","yes"));

        assert exportFile != null;
        ExportExel.export(exportFile.toString(), exportRows,null, false);
        try {
            Resource resource = new UrlResource(exportFile.toUri());
            LOGGER.info(String.valueOf(resource));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


}
