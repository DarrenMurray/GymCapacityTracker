package Application.Services;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by darren.murray on 20/09/2017.
 */
public class PropertiesService
{
    public PropertiesService()
    {
        try {
            FileInputStream config = new FileInputStream("application.properties");
            properties = new Properties();
            properties.load(config);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        this.user = properties.getProperty("gym.prop1");
        this.password = properties.getProperty("gym.prop2");
        this.driverPath = properties.getProperty("driver.path");
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDriverPath() {
        return driverPath;
    }

    private String user;
    private String password;
    private String driverPath;
   private Properties properties;
}
