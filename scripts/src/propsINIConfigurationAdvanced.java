package src;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalINIConfiguration;
import org.apache.commons.configuration.SubnodeConfiguration;

public class propsINIConfigurationAdvanced {

    String layer;

    public propsINIConfigurationAdvanced (){
        layer = "cmd".toLowerCase();
    }

    public static void main(String[] args) {
        propsINIConfigurationAdvanced props = new propsINIConfigurationAdvanced();

        System.out.println(props.getPropertiesUri(props.layer));
        System.out.println("**********************************************************************************************************************************************************************************************************************************************");
        System.out.println(props.getProperties("pathsOut", "l1_validation_output"));
        System.out.println("fin llamada");
    }

    /**
     * Define the uri value, depends of layer
     * without the properties variables
     *
     * @param layer
     * @return String parameter
     */
    private String getPropertiesUri(String layer) {
        if (layer.equals("urm")){
            return getProperties("DEFAULT", "urm_bucket");
        }else{
            return getProperties("DEFAULT", "cbd_bucket");
        }
    }

    /**
     * Create all line of the parameter
     * without the properties variables
     *
     * @param section
     * @param parameters
     * @return String parameter
     */
    private String getProperties(String section, String parameters) {

        boolean complete = false;
        String propertyPath = search(section, parameters);
        String part;
        if (propertyPath != null) {
            do {
                // NOTE: "%(" its the start of all property variables ( and ")s" its the end)
                if (!propertyPath.contains("%("))
                    complete = true;

                else {

                    // get the property variable and search their value in the same section
                    part = propertyPath.substring(propertyPath.indexOf("%(") + 2, propertyPath.indexOf(")s"));
                    String partSearch = search(section, part);

                    System.out.println("PARTSEARCH -->" + partSearch);
                    // if the variable value aren't in the same sectior, it search in the DEFAULT section
                    if (partSearch == null) {
                        partSearch = search("DEFAULT", part);

                        System.out.println("ps ------> " + partSearch);
                    }
                    // if the variable value are in the default section, remplace de variable with his value
                    if (partSearch != null) {
                        System.out.println("PATH_BEFORE ----> " + propertyPath);
                        propertyPath = propertyPath.replace("%(" + part + ")s", partSearch);
                        System.out.println("PATH_AFTER  ----> " + propertyPath);

                    }
                }
                System.out.println("\n*********" + propertyPath + "*********");
            }
            while (!complete);
        } else {
            System.out.println("ERROR - propertyPath are NULL");
            System.exit(-1);
        }
        return propertyPath;
    }

    /**
     * search in the file: properties.cfg
     *
     * search the parameter in section
     *
     * relative path = "properties/properties.cfg"
     *
     * @param section - section of the parameter
     * @param parameters - key of the parameter search
     * @return String with the value of the looked parameter
     */
    private String search(String section, String parameters) {
        try {
            HierarchicalINIConfiguration configuration = new HierarchicalINIConfiguration();
            configuration.load("properties/properties.cfg");
            SubnodeConfiguration sObj = configuration.getSection(section);

            String param1 = sObj.getString(parameters);

            System.out.println(" data in section " + section + "-> " + param1);
            return param1;

        } catch (ConfigurationException e) {
            System.out.println("ERROR " + e.hashCode() + ": " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("ERROR " + e.hashCode() + ": " + e.getMessage());
            System.exit(-1);
        }

        return null;
    }
}