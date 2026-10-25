package dart_monitoring.persistence;

import dart_monitoring.model.Turn;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

@NullMarked
public interface TurnRepository extends JpaRepository<Turn, Integer> {
}
