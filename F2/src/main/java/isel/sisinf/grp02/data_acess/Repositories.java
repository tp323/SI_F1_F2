package isel.sisinf.grp02.data_acess;

import isel.sisinf.grp02.orm.*;
import isel.sisinf.grp02.repositories.*;

import java.util.Collection;
import java.util.List;

class Repositories {
    private final JPAContext context;

    Repositories(JPAContext context) {
        this.context = context;
    }

    protected class ClienteRepository implements IClienteRepository {

        @Override
        public Cliente findByKey(Integer key) {
            return context._em.createNamedQuery("Cliente.findByKey",Cliente.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<Cliente> find(String jpql, Object... params) {
            return context.helperQueryImpl( jpql, params);
        }

        @Override
        public List<Cliente> findAll() {
            return context._em.createNamedQuery("Cliente.findAll",Cliente.class)
                    .getResultList();
        }

    }

    protected class ClienteParticularRepository implements ICliente_ParticularRepository {

        @Override
        public ClienteParticular findByKey(Integer key) {
            return context._em.createNamedQuery("Cliente_Particular.findByKey",ClienteParticular.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<ClienteParticular> find(String jpql, Object... params) {return context.helperQueryImpl( jpql, params);}

        @Override
        public List<ClienteParticular> findAll() {
            return context._em.createNamedQuery("Cliente_Particular.findAll", ClienteParticular.class)
                    .getResultList();
        }
    }

    protected class ClienteInstitucionalRepository implements ICliente_InstitucionalRepository {

        @Override
        public ClienteInstitucional findByKey(Integer key) {
            return context._em.createNamedQuery("Cliente_Institucional.findByKey",ClienteInstitucional.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<ClienteInstitucional> find(String jpql, Object... params) {return context.helperQueryImpl( jpql, params);}

        @Override
        public List<ClienteInstitucional> findAll() {
            return context._em.createNamedQuery("Cliente_Institucional.findAll", ClienteInstitucional.class)
                    .getResultList();
        }
    }

    protected class EquipamentoRepository implements IEquipamentoRepository {

        @Override
        public EquipamentoEletronico findByKey(Long key) {
            return context._em.createNamedQuery("Equipamento_Eletronico.findByKey",EquipamentoEletronico.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<EquipamentoEletronico> find(String jpql, Object... params) {
            return context.helperQueryImpl( jpql, params);
        }

        @Override
        public List<EquipamentoEletronico> findAll() {
            return context._em.createNamedQuery("Equipamento_Eletronico.findAll",EquipamentoEletronico.class)
                    .getResultList();
        }
    }

    protected class VeiculoRepository implements IVeiculoRepository {

        @Override
        public Veiculo findByKey(String key) {
            return context._em.createNamedQuery("Veiculo.findByKey",Veiculo.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Collection<Veiculo> find(String jpql, Object... params) {
            return context.helperQueryImpl( jpql, params);
        }

        @Override
        public List<Veiculo> findAll() {
            return context._em.createNamedQuery("Veiculo.findAll",Veiculo.class)
                    .getResultList();
        }
    }

    protected class CondutorRepository implements ICondutorRepository {

        @Override
        public Condutor findByKey(Integer key) {
            return context._em.createNamedQuery("Condutor.findByKey",Condutor.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<Condutor> find(String jpql, Object... params) {
            return context.helperQueryImpl( jpql, params);
        }

        @Override
        public List<Condutor> findAll() {
            return context._em.createNamedQuery("Condutor.findAll",Condutor.class)
                    .getResultList();
        }
    }

    protected class ZonaVerdeRepository implements IZonaVerdeRepository {

        @Override
        public ZonaVerde findByKey(Long key) {
            return context._em.createNamedQuery("Zona_Verde.findByKey",ZonaVerde.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<ZonaVerde> find(String jpql, Object... params) {
            return context.helperQueryImpl( jpql, params);
        }

        @Override
        public List<ZonaVerde> findAll() {
            return context._em.createNamedQuery("Zona_Verde.findAll",ZonaVerde.class)
                    .getResultList();
        }

        /*@Override
        public ZonaVerde findByLatLongVehicleRaio(Float lat, Float log, String vei, Integer r) {
            return context._em.createNamedQuery("Zona_Verde.findByLatLongVehicleRaio",ZonaVerde.class)
                    .setParameter("lat", lat)
                    .setParameter("log", log)
                    .setParameter("vei", vei)
                    .setParameter("r", r)
                    .getSingleResult();
        }*/
    }

    protected class CoordenadasRepository implements ICoordenadasRepository {

        @Override
        public Coordenadas findByKey(Long key) {
            return context._em.createNamedQuery("Coordenadas.findByKey",Coordenadas.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<Coordenadas> find(String jpql, Object... params) {
            return context.helperQueryImpl( jpql, params);
        }

        @Override
        public List<Coordenadas> findAll() {
            return context._em.createNamedQuery("Coordenadas.findAll",Coordenadas.class)
                    .getResultList();
        }

        @Override
        public Coordenadas findByLatLong(Float lat, Float log) {
            return context._em.createNamedQuery("Coordenadas.findByLatLong",Coordenadas.class)
                    .setParameter("lat", lat)
                    .setParameter("log", log)
                    .getSingleResult();
        }

        @Override
        public Coordenadas findLast() {
            return context._em.createNamedQuery("Coordenadas.findLast",Coordenadas.class)
                    .getSingleResult();
        }
    }

    protected class BipRepository implements IBipRepository {

        @Override
        public Bip findByKey(Long key) {
            return context._em.createNamedQuery("Bip.findByKey",Bip.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<Bip> find(String jpql, Object... params) {
            return context.helperQueryImpl( jpql, params);
        }

        @Override
        public List<Bip> findAll() {
            return context._em.createNamedQuery("Bip.findAll",Bip.class)
                    .getResultList();
        }
    }

    protected class ReadOnlyRepository implements IReadOnlyRepository {

        @Override
        public TodosAlarmes findByKey(String key) {
            return context._em.createNamedQuery("todos_alarmes.findByKey", TodosAlarmes.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }


        @SuppressWarnings("unchecked")
        @Override
        public Collection<TodosAlarmes> find(String jpql, Object... params) {
            return context.helperQueryImpl( jpql, params);
        }

        @Override
        public List<TodosAlarmes> findAll() {
            return context._em.createNamedQuery("todos_alarmes.findAll", TodosAlarmes.class)
                    .getResultList();
        }
    }
}
