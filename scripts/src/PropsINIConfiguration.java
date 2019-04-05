package src;
//import org.apache.commons.
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

public class PropsINIConfiguration {
    public static void main(String[] args) {
        PropsINIConfiguration props = new PropsINIConfiguration();
        System.out.println(props.getVersionProperties("DEFAULT", "data"));
        System.out.println(props.getVersionProperties("Secret", "data"));
        System.out.println("fin llamada");
    }

    private String getVersionProperties(String section, String parameters) {
        try {
            HierarchicalINIConfiguration configuration = new HierarchicalINIConfiguration();
            configuration.load("properties/properties.cfg");
            SubnodeConfiguration sObj = configuration.getSection(section);

            String param1 = sObj.getString(parameters);

            return " data in section " + section + "-> " + param1;
        } catch (Exception e) {
            System.out.println("ERROR " + e.hashCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return "ERROR";
    }
}