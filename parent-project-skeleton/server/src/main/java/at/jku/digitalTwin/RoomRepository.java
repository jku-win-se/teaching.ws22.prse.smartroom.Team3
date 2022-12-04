package at.jku.digitalTwin;

import at.jku.digitalTwin.objects.Room_Object;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room_Object, String> {
}
