package Application.Services;

import Application.Models.GymCapacity;
import Application.Repository.ICapacityRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class WebDriverService
{
   public WebDriverService()
   {
      propertiesService = new PropertiesService();
      System.setProperty("webdriver.chrome.driver", propertiesService.getDriverPath());
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--headless");
      webDriver = new ChromeDriver(options);
      capacityChecker();
   }

   private void capacityChecker()
   {
      final Runnable checker = new Runnable()
      {
         public void run()
         {
            capacityRepository.save(getMembers());
            System.out.println("Members Retrieved");
         }
      };

      final ScheduledFuture<?> capacityHandler = scheduler.scheduleAtFixedRate(checker, 1, 30, MINUTES);

      scheduler.schedule(new Runnable()
      {
         public void run()
         {
            System.out.print("\n ***\n \t Stopping service \n *** \n");
            capacityHandler.cancel(true);
         }
      }, 40, DAYS);
   }

   private GymCapacity getMembers()
   {
      GymCapacity gymCapacity = new GymCapacity();
      String currentUrl = getCurrentUrl();
      if (!currentUrl.equals("https://www.puregym.com/members/"))
         login();

      // Explicit wait
      webDriver.manage().timeouts().implicitlyWait(10, SECONDS);

      WebElement membersInGymElement = null;
      try
      {
         membersInGymElement = webDriver.findElement(By.xpath("//*[@id=\"main-content\"]/div[2]/div/div/div/div[1]/div/div/div/div/div[2]/div/div[1]/div"));
      }
      catch (Exception e)
      {
         System.out.print("unable to find element" + "\n\n" + e);
      }
      if (membersInGymElement != null)
      {
         gymCapacity.setCurrentUsers(matchMembers(membersInGymElement.getText()));
         gymCapacity.setTimestamp(LocalDateTime.now().toString());
         System.out.println("\n **** \n Retrieved Members Value:" + gymCapacity.getCurrentUsers() + "At: " + gymCapacity.getTimestamp() + "\n **** \n");
      }

      refresh();

      return gymCapacity;
   }

   private static int matchMembers(String membersString)
   {
      int result = 0;
      Pattern pattern = Pattern.compile("\\d+");
      Matcher matcher = pattern.matcher(membersString);
      if (matcher.find())
      {
         result = Integer.parseInt(matcher.group());
      }
      return result;
   }

   private String getCurrentUrl(){
      String currentUrl = "";
      try
      {
      currentUrl = webDriver.getCurrentUrl();
      }
      catch (Exception e)
      {
         System.out.println("Failed to get current url" + "\n\n" + e);
      }
      return currentUrl;
   }

   private void login()
   {
      try
      {
         webDriver.get("https://www.puregym.com/login/");
      }
      catch (Exception e)
      {
         System.out.println("Failed to go to Login Page" + "\n\n" + e);
      }
      WebElement email = webDriver.findElement(By.id("email"));
      WebElement password = webDriver.findElement(By.id("pin"));
      WebElement loginButton = webDriver.findElement(By.id("login-submit"));
      email.sendKeys(propertiesService.getUser());
      password.sendKeys(propertiesService.getPassword());
      loginButton.click();
   }

   private void refresh()
   {
       webDriver.navigate().refresh();
       webDriver.manage().timeouts().implicitlyWait(3, SECONDS);
   }

    private void logOut()
    {
        WebElement logout = webDriver.findElement(By.id("loginStatus"));
        logout.click();
        webDriver.manage().timeouts().implicitlyWait(3, SECONDS);
    }
   private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
   private WebDriver webDriver;

   private PropertiesService propertiesService;
   @Autowired
   private ICapacityRepository capacityRepository;

}
