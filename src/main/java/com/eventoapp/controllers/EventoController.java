package com.eventoapp.controllers;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class EventoController {

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    ConvidadoRepository convidadoRepository;


    @RequestMapping(value="/listarEventos", method = RequestMethod.POST)
    public String salvar(@Valid Evento evento,BindingResult bindingResult, RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            attributes.addFlashAttribute("mensagem","verifique os campos");
            return "redirect:/listarEventos";
        }
        attributes.addFlashAttribute("mensagem","Evento adicionado");

        eventoRepository.save(evento);
        return "redirect:/listarEventos";
    }
    @RequestMapping(value="/listarEventos", method = RequestMethod.GET)
    public String listar(Model model){
        Iterable<Evento> eventos = eventoRepository.findAll();
        model.addAttribute("eventos", eventos);
        return "evento/listarEventos";
    }
    @RequestMapping(value = "/detalhesEvento/{codigo}", method = RequestMethod.GET)
    public String detalhes(@PathVariable ("codigo") long codigo, Model model){
        Evento evento =eventoRepository.findByCodigo(codigo);
        model.addAttribute("evento", evento);
        List<Convidado> convidadoList = convidadoRepository.findAllByEventoCodigo(codigo);
        model.addAttribute("convidadoList", convidadoList);
        return "evento/detalheEvento";
    }
    @RequestMapping(value = "/detalhesEvento/{codigo}", method = RequestMethod.POST)
    public String adicionarConvidado(@PathVariable ("codigo") long codigo, @Valid Convidado convidado, BindingResult bindingResult, RedirectAttributes attributes){

        if(bindingResult.hasErrors()){
            attributes.addFlashAttribute("mensagem","verifique os campos");
            return "redirect:/detalhesEvento/{codigo}";
        }
        Evento evento = eventoRepository.findByCodigo(codigo);
        convidado.setEvento(evento);
        convidadoRepository.save(convidado);

        attributes.addFlashAttribute("mensagem","convidado adicionado");
        return "redirect:/detalhesEvento/{codigo}";
    }

    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo){
        Evento evento = eventoRepository.findByCodigo(codigo);
        eventoRepository.delete(evento);
        return "redirect:/listarEventos";
    }
    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg,long codigo){
        Convidado convidado = convidadoRepository.findByRg(rg);
        convidadoRepository.delete(convidado);
        return "redirect:/detalhesEvento/"+codigo;
    }

}
