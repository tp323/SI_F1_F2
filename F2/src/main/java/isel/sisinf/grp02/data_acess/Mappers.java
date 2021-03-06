package isel.sisinf.grp02.data_acess;

import isel.sisinf.grp02.data_mappers.*;
import isel.sisinf.grp02.orm.*;
import jakarta.persistence.LockModeType;

class Mappers {

    private final JPAContext context;

    public Mappers(JPAContext context) {
        this.context = context;
    }

    protected class ClienteMapper implements IClienteMapper {

        @Override
        public Integer create(Cliente entity) {
            context.beginTransaction();
            context._em.persist(entity);
            context.commit();
            return entity.getNif();
        }

        @Override
        public Cliente read(Integer id) {
            return context._em.find(Cliente.class, id);
        }

        @Override
        public Integer update(Cliente entity) {
            context.beginTransaction();
            Cliente c = context._em.find(Cliente.class, entity.getNif(), LockModeType.PESSIMISTIC_WRITE);
            if(c == null) {
                System.out.println("Cliente not found.");
                return null;
            }
            c.setNome(entity.getNome());
            c.setMorada(entity.getMorada());
            c.setTelefone(entity.getTelefone());
            c.setRefCliente(entity.getRefCliente());
            context.commit();
            return entity.getNif();
        }

        @Override
        public Integer delete(Cliente entity) {
            context.beginTransaction();
            Cliente c = context._em.find(Cliente.class, entity.getNif(), LockModeType.PESSIMISTIC_WRITE);
            if(c == null) {
                System.out.println("Cliente not found.");
                return null;
            }
            context._em.remove(c);
            context.commit();
            return c.getNif();
        }
    }

    protected class ClienteParticularMapper implements IClienteParticularMapper {

        @Override
        public Integer create(ClienteParticular entity) {
            try {
                context.beginTransaction();
                context._em.persist(entity);
                context.commit();
                return entity.getCC();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public ClienteParticular read(Integer id) {
            return context._em.find(ClienteParticular.class, id);
        }

        @Override
        public Integer update(ClienteParticular entity) {
            context.beginTransaction();
            ClienteParticular cp = context._em.find(ClienteParticular.class, entity.getCC(), LockModeType.PESSIMISTIC_WRITE);
            if(cp == null) {
                System.out.println("Cliente Particular not found.");
                return null;
            }
            cp.setCliente(entity.getCliente());
            cp.setReferred(entity.getReferred());
            context.commit();
            return entity.getCC();
        }

        @Override
        public Integer delete(ClienteParticular entity) {
            context.beginTransaction();
            ClienteParticular c = context._em.find(ClienteParticular.class, entity.getCC(), LockModeType.PESSIMISTIC_WRITE);
            if(c == null) {
                System.out.println("Cliente Particular not found.");
                return null;
            }
            context._em.remove(c);
            context.commit();
            return c.getCC();
        }
    }

    protected class ClienteInstitucionalMapper implements IClienteInstitucionalMapper {

        @Override
        public Integer create(ClienteInstitucional entity) {
            return null;
        }

        @Override
        public ClienteInstitucional read(Integer nif) {
            return context._em.find(ClienteInstitucional.class, nif);
        }

        @Override
        public Integer update(ClienteInstitucional entity) {
            return null;
        }

        @Override
        public Integer delete(ClienteInstitucional entity) {
            return null;
        }
    }

    protected class EquipamentoMapper implements IEquipamentoMapper {
        @Override
        public Long create(EquipamentoEletronico entity) {
            return null;
        }

        @Override
        public EquipamentoEletronico read(Long id) {
            return context._em.find(EquipamentoEletronico.class, id);
        }

        @Override
        public Long update(EquipamentoEletronico entity) {
            context.beginTransaction();
            EquipamentoEletronico e = context._em.find(EquipamentoEletronico.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if(e == null) {
                System.out.println("Equipamento Eletronico not found.");
                return null;
            }
            e.setEstado(entity.getEstado());
            context.commit();
            return e.getId();
        }

        @Override
        public Long delete(EquipamentoEletronico entity) {
            return null;
        }
    }

    protected class VeiculoMapper implements IVeiculoMapper {
        @Override
        public String create(Veiculo entity) {
            context.beginTransaction();
            context._em.persist(entity);
            context.commit();
            return entity.getMatricula();
        }

        @Override
        public Veiculo read(String matricula) {
            return context._em.find(Veiculo.class, matricula);
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
            context.beginTransaction();
            context._em.persist(entity);
            context.commit();
            return entity.getCC();
        }

        @Override
        public Condutor read(Integer cc) {
            return context._em.find(Condutor.class, cc);
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
        public Long create(ZonaVerde entity) {
            context.beginTransaction();
            context._em.persist(entity);
            context.commit();
            return entity.getID();
        }

        @Override
        public ZonaVerde read(Long id) {
            return null;
        }

        @Override
        public Long update(ZonaVerde entity) {
            return null;
        }

        @Override
        public Long delete(ZonaVerde entity) {
            return null;
        }
    }

    protected class CoordenadasMapper implements ICoordenadasMapper {
        @Override
        public Long create(Coordenadas entity) {
            context.beginTransaction();
            context._em.persist(entity);
            context.commit();
            return entity.getId();
        }

        @Override
        public Coordenadas read(Long id) {
            return context._em.find(Coordenadas.class, id);
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
            context.beginTransaction();
            context._em.persist(entity);
            context.commit();
            return entity.getID();
        }

        @Override
        public Bip read(Long id) {
            return context._em.find(Bip.class, id);
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

    protected class ReadOnlyMapper implements IReadOnlyMapper {
        @Override
        public TodosAlarmesKey create(TodosAlarmes entity) {
            context.beginTransaction();
            context._em.persist(entity);
            context.commit();
            return new TodosAlarmesKey(entity.getMatricula(), entity.getNome(), entity.getLatitude(), entity.getLongitude(), entity.getMarcaTemporal());
        }

        @Override
        public TodosAlarmes read(TodosAlarmesKey id) {
            return context._em.find(TodosAlarmes.class, id);
        }

        @Override
        public TodosAlarmesKey update(TodosAlarmes entity) {
            return null;
        }

        @Override
        public TodosAlarmesKey delete(TodosAlarmes entity) {
            return null;
        }
    }

    protected class PedidoMapper implements IPedidoMapper{

        @Override
        public Long create(Pedido entity) {
            context.beginTransaction();
            context._em.persist(entity);
            context.commit();
            return entity.getID();
        }

        @Override
        public Pedido read(Long id) {
            return context._em.find(Pedido.class, id, LockModeType.OPTIMISTIC);
        }

        @Override
        public Long update(Pedido entity) {
            return null;
        }

        @Override
        public Long delete(Pedido entity) {
            context.beginTransaction();
            Pedido p = context._em.find(Pedido.class, entity.getID(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            if (p == null){
                System.out.println("Pedido not found.");
                return null;
            }
            context._em.remove(p);
            context.commit();
            return p.getID();
        }
    }

    protected class PedidoInvalidMapper implements IPedidoInvalidoMapper {

        @Override
        public Long create(PedidoInvalido entity) {
            context.beginTransaction();
            context._em.persist(entity);
            context.commit();
            return entity.getID();
        }

        @Override
        public PedidoInvalido read(Long id) { return context._em.find(PedidoInvalido.class, id, LockModeType.OPTIMISTIC); }

        @Override
        public Long update(PedidoInvalido entity) {
            return null;
        }

        @Override
        public Long delete(PedidoInvalido entity) {
            context.beginTransaction();
            PedidoInvalido p = context._em.find(PedidoInvalido.class, entity.getID(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            if (p == null){
                System.out.println("Pedido not found.");
                return null;
            }
            context._em.remove(p);
            context.commit();
            return p.getID();
        }
    }
}
