package ipict;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author karin
 */
public class Comparison {
    private static BufferedReader inFile1, inFile2;
    private String errorMsg;
    private String errorLocations;
    
    public Comparison(String path1, String path2)throws NotIPIException{
        compareFiles(path1, path2);
    }
    
    private void compareFiles(String path1, String path2) throws NotIPIException{
        String line1, line2;
        String [] columns1, columns2;
        String eMsg = "";
        String eLocations = "";
        int count = 0;
        
        try{
            inFile1 = new BufferedReader(new FileReader(path1));
            inFile2 = new BufferedReader(new FileReader(path2));
            while((line1 = inFile1.readLine()) != null ){
                line2 = inFile2.readLine();
                count++;
                if(!line1.equals(line2)){
                    columns1 = line1.split("\t");
                    columns2 = line2.split("\t");
                    System.out.println(columns1.length);
                    if(columns1.length != 4 || columns1.length != 4){
                        throw new NotIPIException();
                    }
                    for ( int i = 0; i < 4; i++){
                        if(!columns1[i].equals(columns2[i])){
                            eMsg += "Fout op regel: ";
                            eMsg += Integer.toString(count);
                            eMsg += " Fout in kolom: ";
                            eMsg += Integer.toString(i+1);
                            eMsg += "\n";
                            eLocations += Integer.toString(i+1);
                        }else {
                            eLocations += Integer.toString(0);
                        }
                    }
                }
            }   
        }catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "Bestand niet gevonden");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Kan bestand niet lezen");
        } catch(ArrayIndexOutOfBoundsException e){
            throw new NotIPIException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Onbekende fout");
        }
        errorMsg = eMsg;
        errorLocations = eLocations;
    }
    
    public String getErrorMsg(){
        return errorMsg;
    }
    
        public String getErrorLocations(){
        return errorLocations;
    }
}
