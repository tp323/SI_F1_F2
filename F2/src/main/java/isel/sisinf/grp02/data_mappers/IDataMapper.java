package isel.sisinf.grp02.data_mappers;

public interface IDataMapper<T, TK> {
    T create(T entity);
    T read(T id);
    TK update(T entity);
    TK delete(T entity);
}