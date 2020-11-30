/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Respository;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author bruno
 */
public class LerArquivoCSVFiltro {


    private static final String COMMA_DELIMITER = ",";

    public List LerArquivoComFiltroCSV(int coluna, String Filter) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/Users/bruno/Documents/csv/cidades.csv"));

            List empList = new ArrayList<>();

            String line = "";
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] employeeDetails = line.split(COMMA_DELIMITER);

                if (employeeDetails.length > 0) {
                    if (employeeDetails[coluna].equals(Filter)) {
                        empList.add(employeeDetails[coluna]);
                    }


                }
            }


            return empList;
        } catch (Exception ee) {

            ee.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException ie) {
                System.out.println("Error occured while closing the BufferedReader");
                ie.printStackTrace();
            }
        }
        return null;
    }

}
