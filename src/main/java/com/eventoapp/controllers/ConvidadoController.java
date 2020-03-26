package com.eventoapp.controllers;


import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ConvidadoController {

    @Autowired
    ConvidadoRepository convidadoRepository;

    @RequestMapping(value="/cadastrarConvidado", method = RequestMethod.GET)
    public String form(){
        return "convidado/formConvidado";
    }

    @RequestMapping(value="/cadastrarConvidado", method = RequestMethod.POST)
    public String salvar(Convidado convidado){
        convidadoRepository.save(convidado);
        return "redirect:/listarConvidados";
    }
    @RequestMapping(value="/listarConvidados", method = RequestMethod.GET)
    public String listar(Model model){
        Iterable<Convidado> convidados = convidadoRepository.findAll();
        model.addAttribute("convidados", convidados);
        return "convidado/listarConvidados";
    }
    @RequestMapping(value = "/detalhesConvidado/{codigo}", method = RequestMethod.GET)
    public String detalhes(@PathVariable ("codigo") String rg, Model model){
        Convidado convidado =convidadoRepository.findByRg(rg);
        model.addAttribute("convidado", convidado);
        return "convidado/detalheConvidado";
    }

}
