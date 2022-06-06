package isel.sisinf.grp02.data_acess;

import java.util.Collection;
import java.util.List;


import isel.sisinf.grp02.data_mappers.*;
import isel.sisinf.grp02.orm.*;
import isel.sisinf.grp02.repositories.*;
import jakarta.persistence.*;


public class JPAContext implements IContext {


    private EntityManagerFactory _emf;
    private EntityManager _em;

    private EntityTransaction _tx;
    private int _txcount;

    private IClienteRepository _clienteRepository;
    private ICliente_ParticularRepository _clienteParticularRepository;
    private ICliente_InstitucionalRepository _clienteInstitucionalRepository;

    private IVeiculoRepository _veiculoRepository;
    private ICondutorRepository _condutorRepository;

    private IClienteMapper _clienteMapper;
    private ICliente_ParticularMapper _clienteParticularMapper;
    private ICliente_InstitucionalMapper _clienteInstitucionalMapper;

    private IVeiculoMapper _veiculoMapper;
    private ICondutorMapper _condutorMapper;


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
    protected class ClienteParticularRepository implements ICliente_ParticularRepository {

        @Override
        public Cliente_Particular findByKey(Integer key) {
            return _em.createNamedQuery("Cliente_Particular.findByKey",Cliente_Particular.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<Cliente_Particular> find(String jpql, Object... params) {return helperQueryImpl( jpql, params);}

    }

    protected class ClienteInstitucionalRepository implements ICliente_InstitucionalRepository {

        @Override
        public Cliente_Institucional findByKey(Integer key) {
            return _em.createNamedQuery("Cliente_Institucional.findByKey",Cliente_Institucional.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<Cliente_Institucional> find(String jpql, Object... params) {return helperQueryImpl( jpql, params);}

    }
    protected class VeiculoRepository implements IVeiculoRepository {

        @Override
        public Veiculo findByKey(String key) {
            return _em.createNamedQuery("Veiculo.findByKey",Veiculo.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<Veiculo> find(String jpql, Object... params) {

            return helperQueryImpl( jpql, params);
        }
    }

    protected class CondutorRepository implements ICondutorRepository {

        @Override
        public Condutor findByKey(Integer key) {
            return _em.createNamedQuery("Condutor.findByKey",Condutor.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<Condutor> find(String jpql, Object... params) {

            return helperQueryImpl( jpql, params);
        }
    }

    protected class ClienteMapper implements IClienteMapper {

        @Override
        public Integer create(Cliente entity) {
            beginTransaction();
            _em.persist(entity);
            commit();
            return entity.getNif();
        }

        @Override
        public Cliente read(Cliente id) {
            return _em.find(Cliente.class, id);
        }

        @Override
        public Integer update(Cliente entity) {
            beginTransaction();
            Cliente c = _em.find(Cliente.class, entity.getNif(), LockModeType.PESSIMISTIC_WRITE);
            if(c == null) {
                System.out.println("Cliente not found.");
                return null;
            }
            c.setMorada(entity.getMorada());
            commit();
            return entity.getNif();
        }

        @Override
        public Integer delete(Cliente entity) {
            beginTransaction();
            Cliente c = _em.find(Cliente.class, entity.getNif(), LockModeType.PESSIMISTIC_WRITE);
            if(c == null) {
                System.out.println("Cliente not found.");
                return null;
            }
            _em.remove(c);
            commit();
            return c.getNif();
        }
    }
    protected class ClienteParticularMapper implements ICliente_ParticularMapper {

        @Override
        public Long create(Cliente_Particular entity) {
            return null;
        }

        @Override
        public Cliente_Particular read(Cliente_Particular id) {
            return null;
        }

        @Override
        public Long update(Cliente_Particular entity) {
            return null;
        }

        @Override
        public Long delete(Cliente_Particular entity) {
            return null;
        }
    }

    protected class ClienteInstitucionalMapper implements ICliente_InstitucionalMapper {

        @Override
        public Long create(Cliente_Institucional entity) {
            return null;
        }

        @Override
        public Cliente_Institucional read(Cliente_Institucional id) {
            return null;
        }

        @Override
        public Long update(Cliente_Institucional entity) {
            return null;
        }

        @Override
        public Long delete(Cliente_Institucional entity) {
            return null;
        }
    }
    protected class VeiculoMapper implements IVeiculoMapper {
        @Override
        public Long create(Veiculo entity) {
            return null;
        }

        @Override
        public Veiculo read(Veiculo id) {
            return null;
        }

        @Override
        public Long update(Veiculo entity) {
            return null;
        }

        @Override
        public Long delete(Veiculo entity) {
            return null;
        }
    }

    protected class CondutorMapper implements ICondutorMapper {
        @Override
        public Long create(Condutor entity) {
            return null;
        }

        @Override
        public Condutor read(Condutor id) {
            return null;
        }

        @Override
        public Long update(Condutor entity) {
            return null;
        }

        @Override
        public Long delete(Condutor entity) {
            return null;
        }
    }


    @Override
    public void beginTransaction() {
        if(_tx == null) {
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
        this._clienteParticularRepository = new ClienteParticularRepository();
        this._clienteInstitucionalRepository = new ClienteInstitucionalRepository();

        this._veiculoRepository = new VeiculoRepository();
        this._condutorRepository = new CondutorRepository();

        this._clienteMapper = new ClienteMapper();
        this._clienteParticularMapper = new ClienteParticularMapper();
        this._clienteInstitucionalMapper = new ClienteInstitucionalMapper();

        this._veiculoMapper = new VeiculoMapper();
        this._condutorMapper = new CondutorMapper();
    }

    @Override
    public void rollback(){
        _tx.rollback();
    }


    @Override
    public void close() throws Exception {
        if(_tx != null)
            _tx.rollback();
        _em.close();
        _emf.close();
    }

    @Override
    public IClienteRepository getClientes() {return _clienteRepository;}

    @Override
    public ICliente_ParticularRepository getClientesParticulares(){return _clienteParticularRepository;}

    @Override
    public ICliente_InstitucionalRepository getClientesInstitucionais(){return _clienteInstitucionalRepository;}


    @Override
    public IVeiculoRepository getVeiculos() {
        return _veiculoRepository;
    }

    @Override
    public ICondutorRepository getCondutores() {return _condutorRepository;}

    public IClienteMapper getCliente() {return _clienteMapper;}


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

    //Just an example of what getting a single object with mapper could be
    public int createCliente(Cliente cliente) {
        return getCliente().create(cliente);
    }

    public int deleteCliente(Cliente cliente) {
        return getCliente().delete(cliente);
    }
}