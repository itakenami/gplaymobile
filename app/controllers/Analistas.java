package controllers;

import java.util.List;
import models.Analista;
import models.Cargo;

public class Analistas extends DefaultController {

    public static void index() {
        List<Analista> analistas = Analista.findAll();
        renderTemplate(getTemplateMultiView(), analistas);
    }

    public static void view(Long id) {
        if (id != null) {
            Analista analista = Analista.findById(id);
            if (analista != null) {
                renderTemplate(getTemplateMultiView(), analista);
            } else {
                flash.error("Analista não encontrado.");
                index();
            }
        } else {
            flash.error("É necessário informar um analista.");
            index();
        }
    }

    public static void delete(Long id) {
        try {
            Analista analista = Analista.findById(id);
            analista.delete();
            flash.success("Registro apagado com sucesso.");
        } catch (Exception ex) {
            flash.error("Erro ao apagar registro.");
        }
        index();
    }

    public static void form(Long id) {

        List<Cargo> cargos = Cargo.findAll();
        if (id != null) {
            Analista analista = Analista.findById(id);
            if (analista != null) {
                renderTemplate(getTemplateMultiView(), analista, cargos);
            } else {
                flash.error("Registro não encontrado.");
                index();
            }
        } else {
            renderTemplate(getTemplateMultiView(), cargos);
        }
    }

    public static void save(Long id, Analista analistaVO) {

        Analista analista;
        if (id == null) {
            analista = analistaVO;
            analistaVO = null;
        } else {
            analista = Analista.findById(id);
            if (analista != null) {
                analista.especialidade = analistaVO.especialidade;
                analista.nome = analistaVO.nome;
                analista.cargo = analistaVO.cargo;
            } else {
                flash.error("Registro não encontrado.");
                index();
            }
        }
        validation.valid(analista);
        if (validation.hasErrors()) {
            List<Cargo> cargos = Cargo.findAll();
            renderTemplate(getTemplateMultiView(), "@form", analista, cargos);
        }
        analista.save();
        flash.success("Registro salvo com sucesso.");
        index();
    }
}
