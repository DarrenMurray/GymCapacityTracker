package Application.Repository;

import Application.Models.GymCapacity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICapacityRepository extends MongoRepository<GymCapacity, String>
{
}