package data;

import java.util.List;
import java.util.Optional;

public interface GenericCRUD<T, ID> {

    T save(T t);

    Optional<T> findById(ID id);

    List<T> findAll();

    boolean delete(ID id);
}
