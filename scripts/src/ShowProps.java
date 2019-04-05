
import java.io.*;
import java.util.*;

public class ShowProps {

    public static void main(String[] args) {
        ShowProps props = new ShowProps();
        System.out.println(props.getVersionProperties());
        System.out.println("fin llamada");
    }

    private String getVersionProperties(){
        try{
            Properties propertyFile = new Properties();

            // coge solo un valor
            propertyFile.load(new FileInputStream("properties/properties.cfg"));

            // muestra el fichero entero
            propertyFile.list(System.out);

            String vers1 = propertyFile.getProperty("DEFAULT/version", "err");
            String vers2 = propertyFile.getProperty("version", "err");

            return "version1 = " + vers1 + "/// version2 = " + vers2;
        }
        catch (IOException e) {
            System.out.println("ERROR: conexion con el fichero fallido, NUM : " + e.hashCode());
            System.out.println("ERR: " + e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("ERROR " + e.hashCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return "0";
    }
}