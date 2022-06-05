package isel.sisinf.grp02.repositories;

public interface IDataMapper<T, Tid> {
    T Create(T entity);
    T Update(T entity);
    T Delete(T entity);
}