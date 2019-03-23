package spring.batch.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.batch.example.entities.User;

/**
 * Created by Adservio on 10/03/2019.
 */
public interface UserRepository extends JpaRepository<User,Long> {
}
