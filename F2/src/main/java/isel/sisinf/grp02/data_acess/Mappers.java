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

    protected class ClienteParticularMapper implements ICliente_ParticularMapper {

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

    protected class ClienteInstitucionalMapper implements ICliente_InstitucionalMapper {

        @Override
        public Integer create(ClienteInstitucional entity) {
            return null;
        }

        @Override
        public ClienteInstitucional read(Integer id) {
            return null;
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
            return null;
        }

        @Override
        public Long delete(EquipamentoEletronico entity) {
            return null;
        }
    }

    protected class VeiculoMapper implements IVeiculoMapper {
        @Override
        public Long create(Veiculo entity) {
            return null;
        }

        @Override
        public Veiculo read(Long id) {
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
        public Condutor read(Long id) {
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

    protected class ZonaVerdeMapper implements IZonaVerdeMapper {
        @Override
        public Long create(ZonaVerde entity) {
            return null;
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
            return null;
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
}
