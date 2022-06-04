package isel.sisinf.grp02.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


import isel.sisinf.grp02.JPAObjects.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;


public class JPAContext implements IContext{


    private EntityManagerFactory _emf;
    private EntityManager _em;

    private EntityTransaction _tx;
    private int _txcount;

    private IClienteRepository _clienteRepository;
    //private IStudentRepository _studentRepository;


    protected List helperQueryImpl(String jpql, Object... params) {
        Query q = _em.createQuery(jpql);

        for(int i = 0; i < params.length; ++i)
            q.setParameter(i+1, params[i]);

        return q.getResultList();
    }
    protected class ClienteRepository implements IClienteRepository {

        @Override
        public Cliente findByKey(Integer key) {
            return _em.createNamedQuery("Cliente.findByKey",Cliente.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<Cliente> find(String jpql, Object... params) {
            return helperQueryImpl( jpql, params);
        }

    }

    /*protected class StudentRepository implements IStudentRepository {

        @Override
        public Student findByKey(Integer key) {
            return _em.createNamedQuery("Student.findByKey",Student.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<Student> find(String jpql, Object... params) {

            return helperQueryImpl( jpql, params);
        }

        @Override
        public Collection<Student> getEntolledStudents(Course c){
            return _em.createNamedQuery("Student.EnrolledInCourse",Student.class)
                    .setParameter("key", c.getCourseId())
                    .getResultList();
        }
    }*/

    @Override
    public void beginTransaction() {
        if(_tx == null)
        {
            _tx = _em.getTransaction();
            _tx.begin();
            _txcount=0;
        }
        ++_txcount;
    }

    @Override
    public void commit() {
        --_txcount;
        if(_txcount==0 && _tx != null) {
            _tx.commit();
            _tx = null;
        }
    }

    @Override
    public void flush() {_em.flush();}

    public JPAContext() {this("postgres");}

    public JPAContext(String persistentCtx) {
        super();

        this._emf = Persistence.createEntityManagerFactory(persistentCtx);
        this._em = _emf.createEntityManager();
        this._clienteRepository = new ClienteRepository();
        //this._studentRepository = new StudentRepository();
    }


    @Override
    public void close() throws Exception {
        if(_tx != null)
            _tx.rollback();
        _em.close();
        _emf.close();
    }

    @Override
    public IClienteRepository getClientes() {
        return _clienteRepository;
    }

    /*@Override
    public IStudentRepository getStudents() {return _studentRepository;}*/


    //Example using a table function
    //Do note that, in this case, the implementation is eager, thus less efficient and error prone.
    //In such cases, we should use proxies, by implementing the virtual proxy pattern.
    //With simply queries, like thi one, it is better to use  NamedQueries.
    public Cliente fromCliente(int nif) {
        /*StoredProcedureQuery q = _em.createNamedStoredProcedureQuery("namedfromCountry");
        q.setParameter(1, country);
        q.execute();
        List<Object[]> tmp = (List<Object[]>) q.getResultList();*/
        return getClientes().findByKey(nif);
    }
/*
    public List<Student> fromCountry2(int country) {
        return _em.createNamedStoredProcedureQuery("altnamedfromCountry").setParameter(1, country).getResultList();
    }*/

}
