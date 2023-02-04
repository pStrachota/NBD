package repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T> {

    public T add(T item);

    public void remove(T item);

    public Optional<T> findByID(UUID id);

    public boolean update(T item);

    public List<T> getItems();
}
