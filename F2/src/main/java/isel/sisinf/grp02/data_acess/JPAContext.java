package isel.sisinf.grp02.data_acess;

import isel.sisinf.grp02.data_mappers.*;
import isel.sisinf.grp02.orm.*;
import isel.sisinf.grp02.repositories.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class JPAContext implements IContext {
    private EntityManagerFactory _emf;
    protected EntityManager _em;

    private EntityTransaction _tx;
    private int _txcount;

    private final IClienteRepository _clienteRepository;
    private final ICliente_ParticularRepository _clienteParticularRepository;
    private final ICliente_InstitucionalRepository _clienteInstitucionalRepository;
    private final IEquipamentoRepository _equipamentoRepository;
    private final IVeiculoRepository _veiculoRepository;
    private final ICondutorRepository _condutorRepository;
    private final IZonaVerdeRepository _zonaVerdeRepository;
    private final ICoordenadasRepository _coordenadasRepository;
    private final IBipRepository _bipRepository;
    private final IReadOnlyRepository _todosAlarmesRepository;


    private final IClienteMapper _clienteMapper;
    private final IClienteParticularMapper _clienteParticularMapper;
    private final IClienteInstitucionalMapper _clienteInstitucionalMapper;
    private final IEquipamentoMapper _equipamentoMapper;
    private final IVeiculoMapper _veiculoMapper;
    private final ICondutorMapper _condutorMapper;
    private final IZonaVerdeMapper _zonaVerdeMapper;
    private final ICoordenadasMapper _coordenadasMapper;
    private final IBipMapper _bipMapper;
    private final IReadOnlyMapper _todosAlarmesMapper;



    protected List helperQueryImpl(String jpql, Object... params) {
        Query q = _em.createQuery(jpql);

        for(int i = 0; i < params.length; ++i)
            q.setParameter(i+1, params[i]);

        return q.getResultList();
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
    public void close() {
        if(_tx != null)
            _tx.rollback();
        _em.close();
        _emf.close();
    }



    public JPAContext() {
        this(System.getenv("POSTGRES_PERSISTENCE_NAME"));
    }

    /** TODO: Does it make sense to build the whole object each time a call to a function is made or should it just
     * re-initialize _emf and _em
     */
    public JPAContext(String persistentCtx) {
        super();

        try {
            this._emf = Persistence.createEntityManagerFactory(persistentCtx);
            this._em = _emf.createEntityManager();
        } catch (PersistenceException exception) {
            System.out.println(exception.getMessage() + ".");
            System.out.println("Please check the name of the persistence in the Persistence.xml file located in resources/META-INF!");
            System.out.println("The name of the persistence in there should match the name for the Factory instance to run!");
            System.out.println("If that doesn't work, delete the target folder and rebuild the project!");
            System.exit(1);
        }

        Repositories repositories = new Repositories(this);
        this._clienteRepository = repositories.new ClienteRepository();
        this._clienteParticularRepository = repositories.new ClienteParticularRepository();
        this._clienteInstitucionalRepository = repositories.new ClienteInstitucionalRepository();
        this._equipamentoRepository = repositories.new EquipamentoRepository();
        this._veiculoRepository = repositories.new VeiculoRepository();
        this._condutorRepository = repositories.new CondutorRepository();
        this._zonaVerdeRepository = repositories.new ZonaVerdeRepository();
        this._coordenadasRepository = repositories.new CoordenadasRepository();
        this._bipRepository = repositories.new BipRepository();
        this._todosAlarmesRepository = repositories.new ReadOnlyRepository();

        Mappers mappers = new Mappers(this);
        this._clienteMapper = mappers.new ClienteMapper();
        this._clienteParticularMapper = mappers.new ClienteParticularMapper();
        this._clienteInstitucionalMapper = mappers.new ClienteInstitucionalMapper();
        this._equipamentoMapper = mappers.new EquipamentoMapper();
        this._veiculoMapper = mappers.new VeiculoMapper();
        this._condutorMapper = mappers.new CondutorMapper();
        this._zonaVerdeMapper = mappers.new ZonaVerdeMapper();
        this._coordenadasMapper = mappers.new CoordenadasMapper();
        this._bipMapper = mappers.new BipMapper();
        this._todosAlarmesMapper = mappers.new ReadOnlyMapper();
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

    public IReadOnlyRepository getTodosAlarmesRep() {return _todosAlarmesRepository;}


    /***                MAPPERS                ***/

    public IClienteMapper getCliente() {return _clienteMapper;}

    public IClienteParticularMapper getClienteParticular() {return _clienteParticularMapper;}

    public IEquipamentoMapper getEquipamento() {return _equipamentoMapper;}

    public IVeiculoMapper getVeiculo() {return _veiculoMapper;}

    public ICondutorMapper getCondutor() {return _condutorMapper;}

    public IZonaVerdeMapper getZonaVerdeMapper() {return _zonaVerdeMapper;}

    public ICoordenadasMapper getCoordenada() {return _coordenadasMapper;}

    public IBipMapper getBip() {return _bipMapper;}

    public IReadOnlyMapper getTodosAlarmes() {return _todosAlarmesMapper;}


    /***                CREATE                ***/


    public Integer createCliente(Cliente cliente) {
        return getCliente().create(cliente);
    }

    public int deleteCliente(Cliente cliente) {
        return getCliente().delete(cliente);
    }

    public Integer createClienteParticular(ClienteParticular cliente_particular) {
        return getClienteParticular().create(cliente_particular);
    }

    public String createVeiculo(Veiculo veiculo) {
        return getVeiculo().create(veiculo);
    }

    public long createBip(Bip bip) {
        return getBip().create(bip);
    }

    public String createTodosAlarmes(TodosAlarmes todosAlarmes) {
        return getTodosAlarmes().create(todosAlarmes);
    }


    /***                READ                ***/

    public Cliente readCliente(int nif) {
        return getCliente().read(nif);
    }

    public ClienteParticular readClienteParticular(int cc) {
        return getClienteParticular().read(cc);
    }

    public EquipamentoEletronico readEquipamentoEletronico(long id) {
        return getEquipamento().read(id);
    }

    public Condutor readCondutor(int cc) {
        return getCondutor().read(cc);
    }

    public Coordenadas readCoordenada(long id) {
        return getCoordenada().read(id);
    }

    public Bip readBip(long id) {
        return getBip().read(id);
    }

    public TodosAlarmes readTodosAlarmes(String matricula) {
        return getTodosAlarmes().read(matricula);
    }


    /***                UPDATE                ***/

    public int updateCliente(Cliente cliente) {
        return getCliente().update(cliente);
    }

    public int updateClienteParticular(ClienteParticular cliente_particular) {
        return getClienteParticular().update(cliente_particular);
    }


    /***                DELETE                ***/

    public int deleteClienteParticular(ClienteParticular cliente_particular) {
        return getClienteParticular().delete(cliente_particular);
    }


    /***                PROCEDURES                ***/

    public int procedure_getAlarmNumber(String registration, int year) {
        _em.getTransaction().begin();
        if (registration.length() != 6) throw new IllegalArgumentException("Invalid registration!");

        StoredProcedureQuery pQuery =
                _em.createNamedStoredProcedureQuery("alarm_number")
                        .setParameter(1, registration)
                        .setParameter(2, year);


        pQuery.execute();
        int numbAlarms = (int) pQuery.getOutputParameterValue(3);
        _em.getTransaction().commit();
        return numbAlarms;
    }

    public void procedure_fetchRequests(){
        beginTransaction();
        Query q = _em.createNativeQuery("call processRequests()");
        q.executeUpdate();
        commit();
    }

    public void procedure_createVehicle(String registration, int driver, int equip, int cliente) {
        if(registration.length() != 6) throw new IllegalArgumentException("Invalid registration!");
        if(getNumberSize(driver) < 9) throw new IllegalArgumentException("The driver's CC is not correct!");
        if(getNumberSize(cliente) < 9) throw new IllegalArgumentException("The NIF is not correct!");

        beginTransaction();
        Query q = _em.createNativeQuery("call createVehicle(?1, ?2, ?3, ?4)");
        q.setParameter(1, registration);
        q.setParameter(2, driver);
        q.setParameter(3, equip);
        q.setParameter(4, cliente);

        q.executeUpdate();
        commit();
    }

    public void procedure_createVehicle(String registration, int driver, int equip, int cliente, int raio, BigDecimal lat, BigDecimal log) {
        if (registration.length() != 6) throw new IllegalArgumentException("Invalid registration!");
        if(getNumberSize(driver) < 9) throw new IllegalArgumentException("The driver's CC is not correct!");
        if(getNumberSize(cliente) < 9) throw new IllegalArgumentException("The NIF is not correct!");

        beginTransaction();

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

    public List<ClienteParticular> buildClienteFromInput(int nif, String name, String residence, String phone, int refClient, int cc) {
        checkUserInput(nif, name, residence, phone, refClient, cc);

        Cliente c = new Cliente(nif, name, residence, phone, true);
        ClienteParticular cp = new ClienteParticular(cc, c);
        if(refClient != 0) {
            ClienteParticular ref = readClienteParticular(refClient);
            if(ref == null) throw new IllegalArgumentException("Referred client does not exist!");
            c.setRefCliente(ref);
        }
        c.setClienteParticular(cp);
        beginTransaction();
        createCliente(c);
        int clientId = createClienteParticular(cp);
        commit();
        beginTransaction();
        ClienteParticular insertedClient = readClienteParticular(clientId);
        commit();
        List<ClienteParticular> clientList = new LinkedList<>();
        clientList.add(insertedClient);
        return clientList;
    }

    public List<Cliente> updateClienteFromInput(int nif, String name, String residence, String phone, int refClient, int cc) {
        checkUserInput(nif, name, residence, phone, refClient, cc);
        if(readClienteParticular(cc) == null) throw new IllegalArgumentException("A client with such CC does not exist!");

        Cliente client = new Cliente(nif, name, residence, phone, true);
        if(refClient != 0) {
            ClienteParticular ref = readClienteParticular(refClient);
            if(ref == null) throw new IllegalArgumentException("Referred client does not exist!");
            client.setRefCliente(ref);
        }
        beginTransaction();
        int clientId = updateCliente(client);
        commit();
        beginTransaction();
        Cliente insertedClient = readCliente(clientId);
        commit();
        List<Cliente> clientList = new LinkedList<>();
        clientList.add(insertedClient);
        return clientList;
    }

    private void checkUserInput(int nif, String name, String residence, String phone, int refClient, int cc) {
        if(getNumberSize(nif) < 9) throw new IllegalArgumentException("The NIF is not correct!");
        if(getNumberSize(cc) < 9) throw new IllegalArgumentException("The CC is not correct!");
        if(getNumberSize(refClient) < 9 && refClient!=0) throw new IllegalArgumentException("The reference Client CC is not correct!");
        if(name.length() > 25) throw new IllegalArgumentException("The name is too big!");
        if(residence.length() > 150) throw new IllegalArgumentException("The residence name is too big!");
        if(phone.length() > 13) throw new IllegalArgumentException("The phone number is too big!");
    }

    public String[][] deleteClienteParticularFromInput(int nif) {
        if(getNumberSize(nif) < 9) throw new IllegalArgumentException("The NIF is not correct!");

        beginTransaction();
        Cliente clienteToDelete = readCliente(nif);
        commit();
        if(clienteToDelete.getClienteParticular() == null) throw new IllegalArgumentException("The client is not a Cliente Particular!");
        beginTransaction();
        deleteClienteParticular(clienteToDelete.getClienteParticular());
        commit();
        beginTransaction();
        int clienteId = deleteCliente(clienteToDelete);
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
        long bipId = createBip(bip);
        Bip insertBip = readBip(bipId);
        List<Bip> bipList = new LinkedList<>();
        bipList.add(insertBip);
        return bipList;
    }

    public void createVehicle(String registration, int driver, int equip, int cliente) {
        if(registration.length() != 6) throw new IllegalArgumentException("Invalid registration!");
        if(getNumberSize(driver) < 9) throw new IllegalArgumentException("The driver's CC is not correct!");
        if(getNumberSize(cliente) < 9) throw new IllegalArgumentException("The NIF is not correct!");

        beginTransaction();

        Query q = _em.createNativeQuery("call createVehicle(?1, ?2, ?3, ?4)");
        q.setParameter(1, registration);
        q.setParameter(2, driver);
        q.setParameter(3, equip);
        q.setParameter(4, cliente);

        q.executeUpdate();
        commit();
    }

    public void createVehicle(String registration, int driver, int equip, int cliente, int raio, BigDecimal lat, BigDecimal log) {
        if(registration.length() != 6) throw new IllegalArgumentException("Invalid registration!");
        if(getNumberSize(driver) < 9) throw new IllegalArgumentException("The driver's CC is not correct!");
        if(getNumberSize(cliente) < 9) throw new IllegalArgumentException("The NIF is not correct!");

        beginTransaction();

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

    public List<TodosAlarmes> createView() {
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

        beginTransaction();
        List <TodosAlarmes> list = getTodosAlarmesRep().findAll();
        commit();
        return list;
    }

    public List<TodosAlarmes> insertView(String registration, String driverName, int latitude, int longitude, Timestamp date) {
        if(registration.length() != 6) throw new IllegalArgumentException("Invalid registration!");
        if(driverName.length() > 20) throw new IllegalArgumentException("The driver's name is too big!");

        //beginTransaction();

        /*Query q = _em.createNativeQuery("INSERT INTO todos_alarmes VALUES(?1, ?2, ?3, ?4, ?5)");
        q.setParameter(1, registration);
        q.setParameter(2, driverName);
        q.setParameter(3, latitude);
        q.setParameter(4, longitude);
        q.setParameter(5, date);

        q.executeUpdate();*/
        TodosAlarmes viewAlarmes = new TodosAlarmes();
        viewAlarmes.setMatricula(registration);
        viewAlarmes.setNome(driverName);
        viewAlarmes.setLatitude(latitude);
        viewAlarmes.setLongitude(longitude);
        viewAlarmes.setMarcaTemporal(date);

        String matricula = createTodosAlarmes(viewAlarmes);
        beginTransaction();
        TodosAlarmes insertedAlarm = readTodosAlarmes(matricula);
        commit();
        List<TodosAlarmes> alarmsList = new LinkedList<>();
        alarmsList.add(insertedAlarm);
        return alarmsList;


        //commit();
    }

    private static int getNumberSize(int number) {
        if (number == 0) return 0;
        return getNumberSize(number / 10) + 1;
    }
}
