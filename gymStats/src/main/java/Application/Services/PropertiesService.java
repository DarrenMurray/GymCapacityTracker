package Application.Services;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@Service
public class PropertiesService
{
    public PropertiesService()
    {
        try
        {
            InputStream config = getClass().getResourceAsStream("/application.properties");
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
