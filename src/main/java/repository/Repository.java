package repository;

import java.util.List;
import java.util.Optional;

public abstract class Repository<T> {

    public abstract T add(T item);

    public abstract boolean remove(T item);

    public abstract Optional<T> findByID(Long id);

    public abstract T update(T item);

    public abstract List<T> getItems();
}
