/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Respository;

import Controller.CidadeDAO;
import Model.Cidades;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
 

/**
 *
 * @author bruno
 */
public class LerArquivoCSV {
    private static final String COMMA_DELIMITER = ",";
    
    public LerArquivoCSV()
    {
        BufferedReader br = null;
        try
        {
         
            br = new BufferedReader(new FileReader("/Users/bruno/Documents/csv/cidades.csv"));
            
            
            List<Cidades> empList = new ArrayList<Cidades>();
            
            String line = "";
          
            br.readLine();

            while ((line = br.readLine()) != null) 
            {
                String[] employeeDetails = line.split(COMMA_DELIMITER);
                
                if(employeeDetails.length > 0 )
                {

                    Cidades emp = new Cidades(Integer.parseInt(employeeDetails[0]),
                            employeeDetails[1],employeeDetails[2],
                            employeeDetails[3],
                            Double.parseDouble(employeeDetails[4]),
                            Double.parseDouble(employeeDetails[5]),
                            employeeDetails[6],employeeDetails[7],
                            employeeDetails[8],employeeDetails[9]);
                    empList.add(emp);
                    CidadeDAO dao= new CidadeDAO();
                dao.inserir(emp);
                }
            }
            

            for(Cidades e : empList)
            {

            }
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
        }
        finally
        {
            try
            {
                br.close();
            }
            catch(IOException ie)
            {
                System.out.println("Error occured while closing the BufferedReader");
                ie.printStackTrace();
            }
        }
        
    }
    
    
}
    