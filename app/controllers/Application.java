package controllers;

public class Application extends DefaultController {

    //Menu da Aplicação
    public static void index(){
        renderTemplate(getTemplateMultiView());
    }
    
    //Tela de confirmacao
    public static void excluir(String url, String msg){
        if(msg == null){
            msg = "Deseja realmente excluir?";
        }
        renderTemplate(getTemplateMultiView(),url,msg);
    }
    
}

