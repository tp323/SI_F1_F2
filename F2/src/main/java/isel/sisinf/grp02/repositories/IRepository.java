package isel.sisinf.grp02.repositories;

import java.util.List;

public interface IRepository<T,TCol,TK> {
    T findByKey(TK key);
    TCol find(String jpql, Object... params);

    List findAll();
}