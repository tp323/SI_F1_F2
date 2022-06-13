package isel.sisinf.grp02.repositories;

import isel.sisinf.grp02.orm.TodosAlarmes;

import java.util.Collection;

public interface IReadOnlyRepository extends IRepository<TodosAlarmes, Collection<TodosAlarmes>, String> {
}
