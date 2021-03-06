/*
MIT License

Copyright (c) 2022, Nuno Datia, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package isel.sisinf.grp02.data_acess;

import isel.sisinf.grp02.data_mappers.*;
import isel.sisinf.grp02.orm.*;
import isel.sisinf.grp02.repositories.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JPAContext implements IContext {
    private final String persistentCtx;
    private EntityManagerFactory _emf;
    protected EntityManager _em;

    private EntityTransaction _tx;
    private int _txcount;

    private final IClienteRepository _clienteRepository;
    private final IClienteParticularRepository _clienteParticularRepository;
    private final IClienteInstitucionalRepository _clienteInstitucionalRepository;
    private final IEquipamentoRepository _equipamentoRepository;
    private final IVeiculoRepository _veiculoRepository;
    private final ICondutorRepository _condutorRepository;
    private final IZonaVerdeRepository _zonaVerdeRepository;
    private final ICoordenadasRepository _coordenadasRepository;
    private final IBipRepository _bipRepository;
    private final IReadOnlyRepository _todosAlarmesRepository;
    private final IPedidoRepository _pedidoRepository;
    private final IPedidoInvalidoRepository _pedido_InvalidoRepository;


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
    private final IPedidoMapper _pedidoMapper;
    private final IPedidoInvalidoMapper _pedido_invalidoMapper;



    protected List helperQueryImpl(String jpql, Object... params) {
        Query q = _em.createQuery(jpql);

        for(int i = 0; i < params.length; ++i)
            q.setParameter(i+1, params[i]);

        return q.getResultList();
    }

    @Override
    public void beginTransaction() {
        if(_tx == null || !_tx.isActive()) {
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
        if(_tx != null && _tx.isActive()) _tx.rollback();
        if(_em != null && _em.isOpen()) _em.close();
        if(_emf != null && _emf.isOpen()) _emf.close();
    }

    public void connect() {
        try {
            if(_emf == null || !_emf.isOpen()) this._emf = Persistence.createEntityManagerFactory(persistentCtx);
            if(_em == null || !_em.isOpen()) this._em = _emf.createEntityManager();
        } catch (PersistenceException exception) {
            System.out.println(exception.getMessage() + ".");
            System.out.println("Please check the name of the persistence in the Persistence.xml file located in resources/META-INF!");
            System.out.println("The name of the persistence in there should match the name for the Factory instance to run!");
            System.out.println("If that doesn't work, delete the target folder and rebuild the project!");
            System.exit(1);
        }
    }



    public JPAContext() {
        this("t42dg2");
    }

    public JPAContext(String persistentCtx) {
        super();

        this.persistentCtx = persistentCtx;

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
        this._pedidoRepository = repositories.new PedidoRepository();
        this._pedido_InvalidoRepository = repositories.new PedidoInvalidoRepository();

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
        this._pedidoMapper = mappers.new PedidoMapper();
        this._pedido_invalidoMapper = mappers.new PedidoInvalidMapper();
    }


    /***                REPOSITORIES                ***/

    @Override
    public IClienteRepository getClientes() {return _clienteRepository;}

    @Override
    public IClienteParticularRepository getClientesParticulares(){return _clienteParticularRepository;}

    @Override
    public IClienteInstitucionalRepository getClientesInstitucionais(){return _clienteInstitucionalRepository;}

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

    @Override
    public IPedidoRepository getPedidos() {return _pedidoRepository;}

    @Override
    public IPedidoInvalidoRepository getInvalid_Pedidos() {return _pedido_InvalidoRepository;}

    public IReadOnlyRepository getTodosAlarmesRep() {return _todosAlarmesRepository;}


    /***                MAPPERS                ***/

    public IClienteMapper getCliente() {return _clienteMapper;}

    public IClienteParticularMapper getClienteParticular() {return _clienteParticularMapper;}

    public IEquipamentoMapper getEquipamento() {return _equipamentoMapper;}

    public IVeiculoMapper getVeiculo() {return _veiculoMapper;}

    public ICondutorMapper getCondutor() {return _condutorMapper;}

    public IZonaVerdeMapper getZonaVerde() {return _zonaVerdeMapper;}

    public ICoordenadasMapper getCoordenada() {return _coordenadasMapper;}

    public IBipMapper getBip() {return _bipMapper;}

    public IReadOnlyMapper getTodosAlarmes() {return _todosAlarmesMapper;}

    public IPedidoMapper getPedido() {return _pedidoMapper;}

    public IPedidoInvalidoMapper getPedido_Invalido() {return _pedido_invalidoMapper;}


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

    public Long createZonaVerde(ZonaVerde zonaVerde) {
        return getZonaVerde().create(zonaVerde);
    }

    public Long createCoordenada(Coordenadas coordenadas) {
        return getCoordenada().create(coordenadas);
    }

    public long createBip(Bip bip) {
        return getBip().create(bip);
    }

    public TodosAlarmesKey createTodosAlarmes(TodosAlarmes todosAlarmes) {
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

    public ZonaVerde readZonaVerde(long id) {
        return getZonaVerde().read(id);
    }

    public Coordenadas readCoordenada(long id) {
        return getCoordenada().read(id);
    }

    public Bip readBip(long id) {
        return getBip().read(id);
    }

    public Veiculo readVeiculo(String matricula) {
        return getVeiculo().read(matricula);
    }

    public TodosAlarmes readTodosAlarmes(TodosAlarmesKey key) {
        return getTodosAlarmes().read(key);
    }

    public Pedido readPedido(Long id) { return getPedido().read(id); }

    public PedidoInvalido readPedido_Invalido(Long id) { return getPedido_Invalido().read(id); }


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

    public Long deletePedido(Pedido pedido){
        return getPedido().delete(pedido);
    }

    public Long deletePedido_Invalid(PedidoInvalido pedido_invalido){
        return getPedido_Invalido().delete(pedido_invalido);
    }


    /***                PROCEDURES                ***/

    public int function_getAlarmNumber(String registration, int year) {
        beginTransaction();
        if (registration.length() != 6) throw new IllegalArgumentException("Invalid registration!");

        StoredProcedureQuery pQuery =
                _em.createNamedStoredProcedureQuery("alarm_number")
                        .setParameter(1, registration)
                        .setParameter(2, year);


        pQuery.execute();
        int numbAlarms = (int) pQuery.getOutputParameterValue(3);
        commit();

        return numbAlarms;
    }

    public int function_getAlarmNumber(int year) {
        beginTransaction();

        StoredProcedureQuery pQuery =
                _em.createNamedStoredProcedureQuery("alarm_number")
                        .setParameter(1, null)
                        .setParameter(2, year);


        pQuery.execute();
        int numbAlarms = (int) pQuery.getOutputParameterValue(3);
        commit();

        return numbAlarms;
    }

    public boolean procedure_fetchRequests(){
        beginTransaction();

        Query q = _em.createNativeQuery("CALL processRequests()");
        q.executeUpdate();
        commit();

        return true;
    }

    public boolean optimistic_fetchRequest(){
        beginTransaction();
        List<Pedido> pedidos = getPedidos().findAll();
        for (Pedido target : pedidos) {
            if (target.getEquipamento() == null || target.getMarcaTemporal() == null || target.getLat() == null || target.getLong() == null || (target.getEquipamento() != null && getEquipamento().read(target.getEquipamento()) == null)) {
                PedidoInvalido invalido = new PedidoInvalido();
                invalido.setEquipamento(target.getEquipamento());
                invalido.setMarcaTemporal(target.getMarcaTemporal());
                invalido.setLat(target.getLat());
                invalido.setLong(target.getLong());

                getPedido_Invalido().create(invalido);
                getPedido().delete(target);
                continue;
            }

            EquipamentoEletronico equip = getEquipamento().read(target.getEquipamento());

            Coordenadas cords = new Coordenadas();
            cords.setLatitude(target.getLat());
            cords.setLongitude(target.getLong());

            Bip valid = new Bip();
            valid.setCoordenadas(cords);
            valid.setEquipamento(equip);
            valid.setMarcaTemporal(valid.getMarcaTemporal());

            getBip().create(valid);
            getPedido().delete(target);
        }
        commit();
        return true;
    }

    public List<Veiculo> procedure_createVehicle(String registration, int driver, int equip, int cliente, Integer raio, BigDecimal lat, BigDecimal log) {
        beginTransaction();
        Query q = _em.createNativeQuery("call createVehicle(?1, ?2, ?3, ?4, ?5, ?6, ?7)")
            .setParameter(1, registration)
            .setParameter(2, driver)
            .setParameter(3, equip)
            .setParameter(4, cliente)
            .setParameter(5, raio)
            .setParameter(6, lat)
            .setParameter(7, log);

        q.executeUpdate();
        commit();

        beginTransaction();
        Veiculo insertedVeiculo = readVeiculo(registration);
        commit();

        return Collections.singletonList(insertedVeiculo);
    }


    public List<Veiculo> procedure_createVehicle(String registration, int driver, int equip, int cliente) {
        beginTransaction();
        Query q = _em.createNativeQuery("call createVehicle(?1, ?2, ?3, ?4)")
            .setParameter(1, registration)
            .setParameter(2, driver)
            .setParameter(3, equip)
            .setParameter(4, cliente);

        q.executeUpdate();
        commit();

        beginTransaction();
        Veiculo insertedVeiculo = readVeiculo(registration);
        commit();

        return Collections.singletonList(insertedVeiculo);
    }

    public boolean procedure_clearRequests(){
        beginTransaction();

        Query q = _em.createNativeQuery("CALL deleteinvalids()");

        q.executeUpdate();
        commit();

        return true;
    }


    /***                OTHERS                  ***/

    public List<ClienteParticular> buildClienteFromInput(int nif, String name, String residence, String phone, int refClient, int cc) {
        checkUserInput(nif, name, residence, phone, refClient, cc);

        Cliente c = new Cliente(nif, name, residence, phone, true);
        ClienteParticular cp = new ClienteParticular(cc, c);
        if(refClient != 0) {
            beginTransaction();
            ClienteParticular ref = readClienteParticular(refClient);
            commit();
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

        return Collections.singletonList(insertedClient);
    }

    public List<Cliente> updateClienteFromInput(int nif, String name, String residence, String phone, int refClient, int cc) {
        checkUserInput(nif, name, residence, phone, refClient, cc);
        if(readClienteParticular(cc) == null) throw new IllegalArgumentException("A client with such CC does not exist!");

        Cliente client = new Cliente(nif, name, residence, phone, true);
        if(refClient != 0) {
            beginTransaction();
            ClienteParticular ref = readClienteParticular(refClient);
            commit();
            if(ref == null) throw new IllegalArgumentException("Referred client does not exist!");
            client.setRefCliente(ref);
        }
        beginTransaction();
        Cliente existingCliente = readCliente(nif);
        commit();

        if(existingCliente.getClienteParticular().getCC() != cc) throw new IllegalArgumentException("This CC does not belong to this client!");

        beginTransaction();
        int clientId = updateCliente(client);
        commit();
        beginTransaction();
        Cliente insertedClient = readCliente(clientId);
        commit();

        return Collections.singletonList(insertedClient);
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

        return new String[][]{{Integer.toString(clienteId)}};
    }

    public String[][] deleteClienteFromInput(int nif) {
        if(getNumberSize(nif) < 9) throw new IllegalArgumentException("The NIF is not correct!");

        beginTransaction();
        Cliente clienteToDelete = readCliente(nif);
        commit();
        beginTransaction();
        int clienteId = deleteCliente(clienteToDelete);
        commit();

        return new String[][]{{Integer.toString(clienteId)}};
    }

    public int getAlarmNumber(String registration, int year) {
        if(registration!=null){
            return function_getAlarmNumber(registration, year);
        }else{
            return function_getAlarmNumber(year);
        }
    }

    public List<Veiculo> createVehicleWithProcedure(String mat, int cond, int eq, int c,Integer raio,BigDecimal lat,BigDecimal longit){

        if (mat.length() != 6) throw new IllegalArgumentException("Invalid registration!");
        if(getNumberSize(cond) < 9) throw new IllegalArgumentException("The driver's CC is not correct!");
        if(getNumberSize(c) < 9) throw new IllegalArgumentException("The NIF is not correct!");

        if(raio!=null || lat!=null || longit!=null){
            /***    ADDS ZONA VERDE     ***/
            return procedure_createVehicle(mat,cond,eq,c, raio, lat,longit);
        }else{
            return procedure_createVehicle(mat,cond,eq,c);
        }

    }

    public List<Veiculo> createVehicleWithoutProcedure(String matricula,int ccCondutor, int idEquip ,int nifCliente, Integer raio, BigDecimal lat, BigDecimal log){
        if (matricula.length() != 6) throw new IllegalArgumentException("Invalid registration!");
        if(getNumberSize(ccCondutor) < 9) throw new IllegalArgumentException("The driver's CC is not correct!");
        if(getNumberSize(nifCliente) < 9) throw new IllegalArgumentException("The NIF is not correct!");

        Condutor condutor = readCondutor(ccCondutor);
        if(condutor == null) throw new IllegalArgumentException("Driver not found");
        EquipamentoEletronico equip = readEquipamentoEletronico(idEquip);
        if(equip == null) throw new IllegalArgumentException("Equipment not found");
        Cliente cliente = readCliente(nifCliente);
        if(cliente == null) throw new IllegalArgumentException("Client not found");
        Veiculo v = new Veiculo(matricula, condutor, equip, cliente);
        createVeiculo(v);
        if(lat!=null && log != null && raio != null) {
            float latitude = lat.floatValue();
            float longitude = log.floatValue();
            try {
                Coordenadas coordInDb = getCoordenadas().findByLatLong(latitude, longitude);
                ZonaVerde zv = new ZonaVerde(coordInDb, v, raio);
                createZonaVerde(zv);
            } catch(Exception NoResultException){
                Coordenadas c = new Coordenadas(latitude, longitude);
                ZonaVerde zv = new ZonaVerde(c, v, raio);
                createCoordenada(c);
                createZonaVerde(zv);
            }

        }
        return Collections.singletonList(v);
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

        TodosAlarmes viewAlarmes = new TodosAlarmes(registration, driverName, latitude, longitude, date);

        TodosAlarmesKey key = createTodosAlarmes(viewAlarmes);
        beginTransaction();
        TodosAlarmes insertedAlarm = readTodosAlarmes(key);
        commit();

        return Collections.singletonList(insertedAlarm);
    }

    public List<EquipamentoEletronico> changeEquipmentStatus(long id, String estado) {
        if(!EquipamentoEletronico.EstadosValidos.isEstadoValido(estado)) throw new IllegalArgumentException("Estado is not valid!");

        EquipamentoEletronico equipamentoEletronico = new EquipamentoEletronico();
        equipamentoEletronico.setID(id);
        equipamentoEletronico.setEstado(estado);

        beginTransaction();
        long equipamentoID = getEquipamento().update(equipamentoEletronico);
        commit();
        beginTransaction();
        EquipamentoEletronico updatedEquipamento = getEquipamento().read(equipamentoID);
        commit();

        return Collections.singletonList(updatedEquipamento);
    }

    private static int getNumberSize(int number) {
        if (number == 0) return 0;
        return getNumberSize(number / 10) + 1;
    }
}
