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
         }
      };

      final ScheduledFuture<?> capacityHandler = scheduler.scheduleAtFixedRate(checker, 1, 30, MINUTES);

      scheduler.schedule(new Runnable()
      {
         public void run()
         {
            capacityHandler.cancel(true);
         }
      }, 30, DAYS);
   }

   private GymCapacity getMembers()
   {
      GymCapacity gymCapacity = new GymCapacity();
      if (!webDriver.getCurrentUrl().equals("https://www.puregym.com/members/"))
         login();

      // Explicit wait
      webDriver.manage().timeouts().implicitlyWait(10, SECONDS);

      WebElement membersInGymElement = webDriver.findElement(By.xpath("//*[@id=\"main-content\"]/div[2]/div/div/div/div[1]/div/div/div/div/div[2]/div/div[1]/div"));
      gymCapacity.setCurrentUsers(matchMembers(membersInGymElement.getText()));
      gymCapacity.setTimestamp(LocalDateTime.now().toString());
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

   private void login()
   {
      webDriver.get("https://www.puregym.com/login/");
      WebElement email = webDriver.findElement(By.id("email"));
      WebElement password = webDriver.findElement(By.id("pin"));
      WebElement loginButton = webDriver.findElement(By.id("login-submit"));
      email.sendKeys(propertiesService.getUser());
      password.sendKeys(propertiesService.getPassword());
      loginButton.click();
   }

   private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
   private WebDriver webDriver;
   private PropertiesService propertiesService;
   @Autowired
   private ICapacityRepository capacityRepository;

}
