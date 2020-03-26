package com.eventoapp.repository;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConvidadoRepository extends CrudRepository<Convidado,String> {
    Convidado findByRg(String rg);
    List<Convidado> findAllByEventoCodigo(long codigo);
    Convidado findByRgAndEventoCodigo(long rg, long codigo);
}
