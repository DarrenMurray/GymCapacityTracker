package Application.Services;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebDriverService
{
   private WebDriver webDriver;
   private String username;
   private String userPassword;
   private boolean loggedOut;

   public int getMembers()
   {
      System.setProperty("webdriver.chrome.driver", propertiesService.getDriverPath());

      if(loggedOut)
         login();

      // Explicit wait
      webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

      WebElement membersInGymElement = webDriver.findElement(By.xpath("//*[@id=\"main-content\"]/div[2]/div/div/div/div[1]/div/div/div/div/div[2]/div/div[1]/div/p[1]/span"));
      return matchMembers(membersInGymElement.getText());
   }

   private static int matchMembers(String membersString)
   {
      int result = 0;
      Pattern pattern = Pattern.compile("\\d+");
      Matcher matcher = pattern.matcher(membersString);
      if (matcher.find()) {
         result = Integer.parseInt(matcher.group());
      }
      return result;

   }

   private void login()
   {
      webDriver = new ChromeDriver();
      webDriver.get("https://www.puregym.com/login/");
      WebElement email = webDriver.findElement(By.id("email"));
      WebElement password = webDriver.findElement(By.id("pin"));
      WebElement loginButton = webDriver.findElement(By.id("login-submit"));
      email.sendKeys(propertiesService.getUser());
      password.sendKeys(propertiesService.getPassword());
      loginButton.click();

   }

@Autowired
private PropertiesService propertiesService;

}
