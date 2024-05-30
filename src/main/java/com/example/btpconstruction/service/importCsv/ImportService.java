package com.example.prepa_eval2.service.importData;


import com.example.prepa_eval2.entity.importData.Importseance;
import com.example.prepa_eval2.repository.importData.ImportseanceRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Getter
@Setter
@Service
public class ImportService {
    ImportseanceRepository importseanceRepository;
    importSeanceService importSeanceService;

    public ImportService(ImportseanceRepository importseanceRepository, com.example.prepa_eval2.service.importData.importSeanceService importSeanceService) {
        this.importseanceRepository = importseanceRepository;
        this.importSeanceService = importSeanceService;
    }

    public   List<String> importData(List<List<String>> data){
        List<String> errorList=new ArrayList<>();
        data.remove(0);
        Importseance importseance=null;
        for(List<String> list : data)
        {
            try {
                importseance=new Importseance();
                importseance.setNumero(Integer.valueOf(list.get(0)));
                importseance.setFilm(list.get(1));
                importseance.setCategorie(list.get(2));
                importseance.setSalle(list.get(3));
                importseance.setDateseance(list.get(4));
                importseance.setHeure(list.get(5));
                importseance.setEtat(0);
                importSeanceService.save(importseance);
            }catch (Exception e)
            {
                e.printStackTrace();
//
//               importseance=null;
//                errorList.add(e.getMessage());

            }finally {
                if(importseance!=null)
                {
                    try {
                        importSeanceService.enregistrer(importseance);
                    }catch (Exception e)
                    {
//                        e.printStackTrace();
                        errorList.add(e.getMessage());
                    }
                }

            }

        }
        return errorList;
    }


    public  List<String> readCsv(InputStream inputStream) throws IOException {
        List<List<String>> data = new ArrayList<>();

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser) {
                List<String> rowData = new ArrayList<>();
                for (String value : csvRecord) {
                    rowData.add(value.trim());
                }
                data.add(rowData);
            }

        }

        return importData(data);
    }

}
