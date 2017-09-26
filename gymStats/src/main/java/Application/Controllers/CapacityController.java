package Application.Controllers;

import Application.Models.GymCapacity;
import Application.Repository.ICapacityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class CapacityController
{
   @RequestMapping(value = "api/capacities", method = GET)
   @ResponseBody
   public List<GymCapacity> getAllCapacities()
   {
    return capacityRepository.findAll();
   }

   @RequestMapping(value = "api/test", method = GET)
   @ResponseBody
   public String testController()
   {
      return "success";
   }

   @Autowired
   private ICapacityRepository capacityRepository;
}
