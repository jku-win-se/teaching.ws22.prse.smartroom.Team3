package at.jku.digitalTwin.Repositories;

import at.jku.digitalTwin.objects.Lights_Object;
import at.jku.digitalTwin.objects.Room_Object;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LightRepository extends JpaRepository<Lights_Object, String> {
    List<Lights_Object> findByRoom(String room);
    List<Lights_Object> findByRoom(Room_Object room);
    Lights_Object findByIdAndRoom(String light_id, Room_Object room);

    @Transactional
    void deleteByIdAndRoom(String light_id, Room_Object room);
}
