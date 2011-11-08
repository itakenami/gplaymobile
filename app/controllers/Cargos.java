package controllers;

import java.util.List;
import models.Cargo;

public class Cargos extends DefaultController {

    public static void index() {
        List<Cargo> cargos = Cargo.findAll();
        renderTemplate(getTemplateMultiView(), cargos);
    }

    public static void view(Long id) {
        if (id != null) {
            Cargo cargo = Cargo.findById(id);
            if (cargo != null) {
                renderTemplate(getTemplateMultiView(), cargo);
            } else {
                flash.error("Cargo não encontrado.");
                index();
            }
        } else {
            flash.error("É necessário informar um cargo.");
            index();
        }
    }

    public static void delete(Long id) {
        try {
            Cargo cargo = Cargo.findById(id);
            cargo.delete();
            flash.success("Registro apagado com sucesso.");
        } catch (Exception ex) {
            flash.error("Erro ao apagar registro.");
        }
        index();
    }

    public static void form(Long id) {

        if (id != null) {
            Cargo cargo = Cargo.findById(id);
            if (cargo != null) {
                renderTemplate(getTemplateMultiView(), cargo);
            } else {
                flash.error("Registro não encontrado.");
                index();
            }
        } else {
            renderTemplate(getTemplateMultiView());
        }
    }

    public static void save(Long id, Cargo cargoVO) {

        Cargo cargo;
        if (id == null) {
            cargo = cargoVO;
            cargoVO = null;
        } else {
            cargo = Cargo.findById(id);
            if (cargo != null) {
                cargo.nome = cargoVO.nome;
            } else {
                flash.error("Registro não encontrado.");
                index();
            }
        }
        validation.valid(cargo);
        if (validation.hasErrors()) {
            renderTemplate(getTemplateMultiView(), "@form", cargo);
        }
        cargo.save();
        flash.success("Registro salvo com sucesso.");
        index();
    }
}
