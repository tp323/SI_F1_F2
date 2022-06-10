package isel.sisinf.grp02.data_mappers;

public interface IDataMapper<T, TK> {
    TK create(T entity);
    T read(TK id);
    TK update(T entity);
    TK delete(T entity);
}