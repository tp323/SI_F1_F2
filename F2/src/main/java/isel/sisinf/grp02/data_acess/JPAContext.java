package isel.sisinf.grp02.data_acess;

import isel.sisinf.grp02.data_mappers.*;
import isel.sisinf.grp02.orm.*;
import isel.sisinf.grp02.repositories.*;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class JPAContext implements IContext {

    private EntityManagerFactory _emf;
    private EntityManager _em;

    private EntityTransaction _tx;
    private int _txcount;

    private IClienteRepository _clienteRepository;
    private ICliente_ParticularRepository _clienteParticularRepository;
    private ICliente_InstitucionalRepository _clienteInstitucionalRepository;
    private IEquipamentoRepository _equipamentoRepository;
    private IVeiculoRepository _veiculoRepository;
    private ICondutorRepository _condutorRepository;
    private IZonaVerdeRepository _zonaVerdeRepository;
    private ICoordenadasRepository _coordenadasRepository;
    private IBipRepository _bipRepository;


    private IClienteMapper _clienteMapper;
    private ICliente_ParticularMapper _clienteParticularMapper;
    private ICliente_InstitucionalMapper _clienteInstitucionalMapper;
    private IEquipamentoMapper _equipamentoMapper;
    private IVeiculoMapper _veiculoMapper;
    private ICondutorMapper _condutorMapper;
    private IZonaVerdeMapper _zonaVerdeMapper;
    private ICoordenadasMapper _coordenadasMapper;
    private IBipMapper _bipMapper;



    protected List helperQueryImpl(String jpql, Object... params) {
        Query q = _em.createQuery(jpql);

        for(int i = 0; i < params.length; ++i)
            q.setParameter(i+1, params[i]);

        return q.getResultList();
    }


    /***                REPOSITORIES                ***/

    protected class ClienteRepository implements IClienteRepository {

        @Override
        public Cliente findByKey(Integer key) {
            return _em.createNamedQuery("Cliente.findByKey",Cliente.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }
        @Override
        public List findAll() {
            return _em.createNamedQuery("Cliente.findAll",Cliente.class)
                    .getResultList();
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

        @Override
        public List findAll() {
            return _em.createNamedQuery("Cliente_Particular.findAll", Cliente_Particular.class)
                    .getResultList();
        }
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

        @Override
        public List findAll() {
            return _em.createNamedQuery("Cliente_Institucional.findAll", Cliente_Institucional.class)
                    .getResultList();
        }
    }

    protected class EquipamentoRepository implements IEquipamentoRepository {

        @Override
        public Equipamento_Eletronico findByKey(Long key) {
            return _em.createNamedQuery("Equipamento_Eletronico.findByKey",Equipamento_Eletronico.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<Equipamento_Eletronico> find(String jpql, Object... params) {
            return helperQueryImpl( jpql, params);
        }

        @Override
        public List findAll() {
            return _em.createNamedQuery("Equipamento_Eletronico.findAll",Equipamento_Eletronico.class)
                    .getResultList();
        }
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

        @Override
        public List findAll() {
            return _em.createNamedQuery("Veiculo.findAll",Veiculo.class)
                    .getResultList();
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

        @Override
        public List findAll() {
            return _em.createNamedQuery("Condutor.findAll",Condutor.class)
                    .getResultList();
        }
    }

    protected class ZonaVerdeRepository implements IZonaVerdeRepository {

        @Override
        public Zona_Verde findByKey(Long key) {
            return _em.createNamedQuery("Zona_Verde.findByKey",Zona_Verde.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<Zona_Verde> find(String jpql, Object... params) {
            return helperQueryImpl( jpql, params);
        }

        @Override
        public List findAll() {
            return _em.createNamedQuery("Zona_Verde.findAll",Zona_Verde.class)
                    .getResultList();
        }
    }

    protected class CoordenadasRepository implements ICoordenadasRepository {

        @Override
        public Coordenadas findByKey(Long key) {
            return _em.createNamedQuery("Coordenadas.findByKey",Coordenadas.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<Coordenadas> find(String jpql, Object... params) {
            return helperQueryImpl( jpql, params);
        }

        @Override
        public List findAll() {
            return _em.createNamedQuery("Coordenadas.findAll",Coordenadas.class)
                    .getResultList();
        }
    }

    protected class BipRepository implements IBipRepository {

        @Override
        public Bip findByKey(Long key) {
            return _em.createNamedQuery("Bip.findByKey",Bip.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<Bip> find(String jpql, Object... params) {
            return helperQueryImpl( jpql, params);
        }

        @Override
        public List findAll() {
            return _em.createNamedQuery("Bip.findAll",Bip.class)
                    .getResultList();
        }
    }



    /***                MAPPERS                ***/

    protected class ClienteMapper implements IClienteMapper {

        @Override
        public Integer create(Cliente entity) {
            beginTransaction();
            _em.persist(entity);
            commit();
            return entity.getNif();
        }

        @Override
        public Cliente read(Integer id) {
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
            c.setNome(entity.getNome());
            c.setMorada(entity.getMorada());
            c.setTelefone(entity.getTelefone());
            c.setRefCliente(entity.getRefCliente());
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
        public Integer create(Cliente_Particular entity) {
            beginTransaction();
            _em.persist(entity);
            commit();
            return entity.getCC();
        }

        @Override
        public Cliente_Particular read(Integer id) {
            return _em.find(Cliente_Particular.class, id);
        }

        @Override
        public Integer update(Cliente_Particular entity) {
            beginTransaction();
            Cliente_Particular cp = _em.find(Cliente_Particular.class, entity.getCC(), LockModeType.PESSIMISTIC_WRITE);
            if(cp == null) {
                System.out.println("Cliente Particular not found.");
                return null;
            }
            cp.setCliente(entity.getCliente());
            cp.setReferred(entity.getReferred());
            commit();
            return entity.getCC();
        }

        @Override
        public Integer delete(Cliente_Particular entity) {
            beginTransaction();
            Cliente_Particular c = _em.find(Cliente_Particular.class, entity.getCC(), LockModeType.PESSIMISTIC_WRITE);
            if(c == null) {
                System.out.println("Cliente Particular not found.");
                return null;
            }
            _em.remove(c);
            commit();
            return c.getCC();
        }
    }

    protected class ClienteInstitucionalMapper implements ICliente_InstitucionalMapper {

        @Override
        public Integer create(Cliente_Institucional entity) {
            return null;
        }

        @Override
        public Cliente_Institucional read(Integer nif) {
            return _em.find(Cliente_Institucional.class, nif);
        }

        @Override
        public Integer update(Cliente_Institucional entity) {
            return null;
        }

        @Override
        public Integer delete(Cliente_Institucional entity) {
            return null;
        }
    }

    protected class EquipamentoMapper implements IEquipamentoMapper {
        @Override
        public Long create(Equipamento_Eletronico entity) {
            return null;
        }

        @Override
        public Equipamento_Eletronico read(Long id) {
            return _em.find(Equipamento_Eletronico.class, id);
        }

        @Override
        public Long update(Equipamento_Eletronico entity) {
            return null;
        }

        @Override
        public Long delete(Equipamento_Eletronico entity) {
            return null;
        }
    }

    protected class VeiculoMapper implements IVeiculoMapper {
        @Override
        public String create(Veiculo entity) {
            beginTransaction();
            _em.persist(entity);
            commit();
            return entity.getMatricula();
        }

        @Override
        public Veiculo read(String matricula) {
            return _em.find(Veiculo.class, matricula);
        }

        @Override
        public String update(Veiculo entity) {
            return null;
        }

        @Override
        public String delete(Veiculo entity) {
            return null;
        }
    }

    protected class CondutorMapper implements ICondutorMapper {
        @Override
        public Integer create(Condutor entity) {
            beginTransaction();
            _em.persist(entity);
            commit();
            return entity.getCC();
        }

        @Override
        public Condutor read(Integer cc) {
            return _em.find(Condutor.class, cc);
        }

        @Override
        public Integer update(Condutor entity) {
            return null;
        }

        @Override
        public Integer delete(Condutor entity) {
            return null;
        }
    }

    protected class ZonaVerdeMapper implements IZonaVerdeMapper {
        @Override
        public Long create(Zona_Verde entity) {
            beginTransaction();
            _em.persist(entity);
            commit();
            return entity.getID();
        }

        @Override
        public Zona_Verde read(Long id) {
            return null;
        }

        @Override
        public Long update(Zona_Verde entity) {
            return null;
        }

        @Override
        public Long delete(Zona_Verde entity) {
            return null;
        }
    }

    protected class CoordenadasMapper implements ICoordenadasMapper {
        @Override
        public Long create(Coordenadas entity) {
            return null;
        }

        @Override
        public Coordenadas read(Long id) {
            return _em.find(Coordenadas.class, id);
        }

        @Override
        public Long update(Coordenadas entity) {
            return null;
        }

        @Override
        public Long delete(Coordenadas entity) {
            return null;
        }
    }

    protected class BipMapper implements IBipMapper {
        @Override
        public Long create(Bip entity) {
            beginTransaction();
            _em.persist(entity);
            commit();
            return entity.getID();
        }

        @Override
        public Bip read(Long id) {
            return _em.find(Bip.class, id);
        }

        @Override
        public Long update(Bip entity) {
            return null;
        }

        @Override
        public Long delete(Bip entity) {
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
    public void flush() {
        _em.flush();
    }

    @Override
    public void rollback(){
        if(_txcount==0 && _tx != null) {
            _tx.rollback();
            _tx = null;
        }
    }

    @Override
    public void close() throws Exception {
        if(_tx != null)
            _tx.rollback();
        _em.close();
        _emf.close();
    }



    public JPAContext() {
        this(System.getenv("POSTGRES_PERSISTENCE_NAME"));
    }

    public JPAContext(String persistentCtx) {
        super();

        this._emf = Persistence.createEntityManagerFactory(persistentCtx);
        this._em = _emf.createEntityManager();
        this._clienteRepository = new ClienteRepository();
        this._clienteParticularRepository = new ClienteParticularRepository();
        this._clienteInstitucionalRepository = new ClienteInstitucionalRepository();
        this._equipamentoRepository = new EquipamentoRepository();
        this._veiculoRepository = new VeiculoRepository();
        this._condutorRepository = new CondutorRepository();
        this._zonaVerdeRepository = new ZonaVerdeRepository();
        this._coordenadasRepository = new CoordenadasRepository();
        this._bipRepository = new BipRepository();

        this._clienteMapper = new ClienteMapper();
        this._clienteParticularMapper = new ClienteParticularMapper();
        this._clienteInstitucionalMapper = new ClienteInstitucionalMapper();
        this._equipamentoMapper = new EquipamentoMapper();
        this._veiculoMapper = new VeiculoMapper();
        this._condutorMapper = new CondutorMapper();
        this._zonaVerdeMapper = new ZonaVerdeMapper();
        this._coordenadasMapper = new CoordenadasMapper();
        this._bipMapper = new BipMapper();

    }


    /***                REPOSITORIES                ***/

    @Override
    public IClienteRepository getClientes() {return _clienteRepository;}

    @Override
    public ICliente_ParticularRepository getClientesParticulares(){return _clienteParticularRepository;}

    @Override
    public ICliente_InstitucionalRepository getClientesInstitucionais(){return _clienteInstitucionalRepository;}

    @Override
    public IEquipamentoRepository getEquipamentos() {return _equipamentoRepository;}

    @Override
    public IVeiculoRepository getVeiculos() {return _veiculoRepository;}

    @Override
    public ICondutorRepository getCondutores() {return _condutorRepository;}

    @Override
    public IZonaVerdeRepository getZonasVerdes() {return _zonaVerdeRepository;}

    @Override
    public ICoordenadasRepository getCoordenadas() {return _coordenadasRepository;}

    @Override
    public IBipRepository getBips() {return _bipRepository;}


    /***                MAPPERS                ***/

    public IClienteMapper getCliente() {return _clienteMapper;}

    public ICliente_ParticularMapper getClienteParticular() {return _clienteParticularMapper;}

    public IEquipamentoMapper getEquipamento() {return _equipamentoMapper;}

    public IVeiculoMapper getVeiculo() {return _veiculoMapper;}

    public ICondutorMapper getCondutor() {return _condutorMapper;}

    public IZonaVerdeMapper getZonaVerdeMapper() {return _zonaVerdeMapper;}

    public ICoordenadasMapper getCoordenada() {return _coordenadasMapper;}

    public IBipMapper getBip() {return _bipMapper;}


    /***                CREATE                ***/


    public Integer createCliente(Cliente cliente) {
        return getCliente().create(cliente);
    }

    public int deleteCliente(Cliente cliente) {
        return getCliente().delete(cliente);
    }

    public Integer createClienteParticular(Cliente_Particular cliente_particular) {
        return getClienteParticular().create(cliente_particular);
    }

    public String createVeiculo(Veiculo veiculo) {
        return getVeiculo().create(veiculo);
    }

    public long createBip(Bip bip) {
        return getBip().create(bip);
    }


    /***                READ                ***/

    public Cliente readCliente(int nif) {
        return getCliente().read(nif);
    }

    public Cliente_Particular readClienteParticular(int cc) {
        return getClienteParticular().read(cc);
    }

    public Equipamento_Eletronico readEquipamentoEletronico(long id) {
        return getEquipamento().read(id);
    }

    public Condutor readCondutor(int cc) {
        return getCondutor().read(cc);
    }

    public Coordenadas readCoordenada(long id) {
        return getCoordenada().read(id);
    }

    public Bip readBip(Long id) {
        return getBip().read(id);
    }


    /***                UPDATE                ***/

    public int updateCliente(Cliente cliente) {
        return getCliente().update(cliente);
    }

    public int updateClienteParticular(Cliente_Particular cliente_particular) {
        return getClienteParticular().update(cliente_particular);
    }


    /***                DELETE                ***/

    public int deleteClienteParticular(Cliente_Particular cliente_particular) {
        return getClienteParticular().delete(cliente_particular);
    }


    /***                PROCEDURES                ***/

    public int procedure_getAlarmNumber(String registration, int year) {
        if (registration.length() != 6) throw new IllegalArgumentException("Invalid registration");

        StoredProcedureQuery procedureQuery =
                _em.createNamedStoredProcedureQuery(Bip.alarm_number);
        procedureQuery.setParameter(1, registration);
        procedureQuery.setParameter(2, year);
        procedureQuery.execute();
        return procedureQuery.getFirstResult();
    }

    public void procedure_fetchRequests(){
        beginTransaction();
        Query q = _em.createNativeQuery("call processRequests()");
        q.executeUpdate();
        commit();
    }

    public void procedure_createVehicle(String registration, int driver, int equip, int cliente) {
        beginTransaction();
        Query q = _em.createNativeQuery("call createVehicle(?1, ?2, ?3, ?4)");
        q.setParameter(1, registration);
        q.setParameter(2, driver);
        q.setParameter(3, equip);
        q.setParameter(4, cliente);

        q.executeUpdate();
        commit();
    }

    public void procedure_createVehicle(String registration, int driver, int equip, int cliente, int raio, int lat, int log) {
        beginTransaction();

        if (registration.length() != 6) throw new IllegalArgumentException("Invalid registration");

        Query q = _em.createNativeQuery("call createVehicle(?1, ?2, ?3, ?4, ?5, ?6, ?7)");
        q.setParameter(1, registration);
        q.setParameter(2, driver);
        q.setParameter(3, equip);
        q.setParameter(4, cliente);
        q.setParameter(5, raio);
        q.setParameter(6, lat);
        q.setParameter(7, log);

        q.executeUpdate();
        commit();
    }

    public void procedure_clearRequests(){
        beginTransaction();

        Query q = _em.createNativeQuery("call deleteinvalids()");

        q.executeUpdate();
        commit();
    }


    /***                OTHERS                  ***/







    public List<Cliente_Particular> buildClienteFromInput(int nif, String name, String residence, String phone, int refClient, int cc) {
        if(getNumberSize(nif) < 9) throw new IllegalArgumentException("The NIF is not correct!");
        if(getNumberSize(cc) < 9) throw new IllegalArgumentException("The CC is not correct");

        Cliente client = new Cliente(nif, name, residence, phone, true);
        Cliente_Particular cp = new Cliente_Particular();
        Cliente_Particular ref = new Cliente_Particular();
        ref.setCC(refClient);
        client.setRefCliente(readClienteParticular(ref.getCC()));
        cp.setCC(cc);
        cp.setCliente(client);
        client.setClienteParticular(cp);
        beginTransaction();
        createCliente(client);
        commit();
        beginTransaction();
        int clientId = createClienteParticular(cp);
        commit();
        beginTransaction();
        Cliente_Particular insertedClient = readClienteParticular(clientId);
        commit();
        List<Cliente_Particular> clientList = new LinkedList<>();
        clientList.add(insertedClient);
        return clientList;
    }

    public List<Cliente_Particular> updateClienteFromInput(int nif, String name, String residence, String phone, int refClient, int cc) {
        if(getNumberSize(nif) < 9) throw new IllegalArgumentException("The NIF is not correct!");
        // TODO: Should be 8 not 9
        if(getNumberSize(cc) < 9) throw new IllegalArgumentException("The CC is not correct");

        Cliente client = new Cliente(nif, name, residence, phone, true);
        Cliente_Particular cp = new Cliente_Particular();
        client.setRefCliente(readClienteParticular(refClient));
        cp.setCC(cc);
        cp.setCliente(client);
        client.setClienteParticular(cp);
        beginTransaction();
        updateCliente(client);
        commit();
        beginTransaction();
        int clientId = updateClienteParticular(cp);
        commit();
        beginTransaction();
        Cliente_Particular insertedClient = readClienteParticular(clientId);
        commit();
        List<Cliente_Particular> clientList = new LinkedList<>();
        clientList.add(insertedClient);
        return clientList;
    }

    public String[][] deleteClienteParticularFromInput(int nif, int cc) {
        if(getNumberSize(nif) < 9) throw new IllegalArgumentException("The NIF is not correct!");
        if(getNumberSize(cc) < 9) throw new IllegalArgumentException("The CC is not correct");

        beginTransaction();
        Cliente clienteToDelete = readCliente(nif);
        commit();
        beginTransaction();
        Cliente_Particular clienteParticularToDelete = readClienteParticular(cc);
        commit();
        beginTransaction();
        deleteCliente(clienteToDelete);
        commit();
        beginTransaction();
        int clienteId = deleteClienteParticular(clienteParticularToDelete);
        commit();

        String[][] deletedIdList = new String[1][];
        deletedIdList[0] = new String[]{Integer.toString(clienteId)};
        return deletedIdList;
    }

    public String[][] deleteClienteFromInput(int nif) {
        if(getNumberSize(nif) < 9) throw new IllegalArgumentException("The NIF is not correct!");

        beginTransaction();
        Cliente clienteToDelete = readCliente(nif);
        commit();
        beginTransaction();
        int clienteId = deleteCliente(clienteToDelete);
        commit();

        String[][] deletedIdList = new String[1][];
        deletedIdList[0] = new String[]{Integer.toString(clienteId)};
        return deletedIdList;
    }

    public List<Bip> buildBipFromInput(int equipamento, Timestamp marca_temporal, int coordenadas, boolean alarme) {
        Bip bip = new Bip();
        bip.setEquipamento(readEquipamentoEletronico(equipamento));
        bip.setMarcaTemporal(marca_temporal);
        bip.setCoordenadas(readCoordenada(coordenadas));
        bip.setAlarme(alarme);
        beginTransaction();
        Long bipId = createBip(bip);
        commit();
        beginTransaction();
        Bip insertBip = readBip(bipId);
        commit();
        List<Bip> bipList = new LinkedList<>();
        bipList.add(insertBip);
        return bipList;
    }

    public void createVehicle(String registration, int driver, int equip, int cliente, Integer raio, Integer lat, Integer log) {
        beginTransaction();

        if (registration.length() != 6) throw new IllegalArgumentException("Invalid registration");
        if(raio==null || lat==null || log==null){
            /***    VERIFICAR USO DE NULS PARA CRIAR OU N ZONA VERDE    ***/
            //TODO(procedure talvez esteja errado)
        }
        Query q = _em.createNativeQuery("call createVehicle(?1, ?2, ?3, ?4, ?5, ?6, ?7)");
        q.setParameter(1, registration);
        q.setParameter(2, driver);
        q.setParameter(3, equip);
        q.setParameter(4, cliente);
        q.setParameter(5, raio);
        q.setParameter(6, lat);
        q.setParameter(7, log);

        q.executeUpdate();
        commit();
    }

    public void createView() {
        beginTransaction();

        Query q = _em.createNativeQuery("CREATE OR REPLACE VIEW todos_alarmes AS" +
                " SELECT matricula, nome, latitude, longitude, marca_temporal" +
                " FROM bip_equipamento_eletronico bip" +
                " INNER JOIN equipamento_eletronico eq ON equipamento = eq.id" +
                " INNER JOIN veiculo v ON eq.id = v.equipamento" +
                " INNER JOIN condutor cond ON v.condutor = cond.cc" +
                " INNER JOIN coordenadas coord ON coordenadas = coord.id");

        q.executeUpdate();
        commit();
    }

    public void insertView(String registration, String driverName, int latitude, int longitude, Timestamp date) {
        beginTransaction();

        Query q = _em.createNativeQuery("INSERT INTO todos_alarmes VALUES(?1, ?2, ?3, ?4, ?5)");
        q.setParameter(1, registration);
        q.setParameter(2, driverName);
        q.setParameter(3, latitude);
        q.setParameter(4, longitude);
        q.setParameter(5, date);

        q.executeUpdate();
        commit();
    }


    private static int getNumberSize(int number) {
        if (number == 0) return 0;
        return getNumberSize(number / 10) + 1;
    }
}
