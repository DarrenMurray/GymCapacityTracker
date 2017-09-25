package Application.Models;

import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document
public class GymCapacity
{
   private int currentUsers;
   private String timestamp;

   public int getCurrentUsers()
   {
      return currentUsers;
   }

   public void setCurrentUsers(int currentUsers)
   {
      this.currentUsers = currentUsers;
   }

   public String getTimestamp()
   {
      return timestamp;
   }

   public void setTimestamp(String timestamp)
   {
      this.timestamp = timestamp;
   }
}
