package controllers;

import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;

public class DefaultController extends Controller {

    @Before
    public static void setTema() {
        String tema = Cache.get(session.getId()+"-tema", String.class);
        String jqmtema = Cache.get(session.getId()+"-jqmtema", String.class);
        if (tema == null) {
            tema = "redmond";
            Cache.set(session.getId()+"-tema", tema, "30mn");
        }
        if (jqmtema == null) {
            jqmtema = "b";
            Cache.set(session.getId()+"-jqmtema", jqmtema, "30mn");
        }
        renderArgs.put("tema", tema);
        renderArgs.put("jqmtema", jqmtema);
    }

    public static void changeTema(String tema) {
        Cache.set(session.getId()+"-tema", tema, "30mn");
        redirect("/");
    }
    
    public static void changeJqmTema(String jqmtema) {
        Cache.set(session.getId()+"-jqmtema", jqmtema, "30mn");
        redirect("/");
    }

    public static void setView(int valor) {
        String type = "DESKTOP";
        switch(valor){
            case 1:
                type = "MOBILE";
                break;
            case 2:
                type = "TABLET";
                break;
        }
        Cache.set(session.getId()+"-tpl", type, "30mn");
        redirect("/");
    }

    protected static String getTemplateMultiView() {

        String tpl = Cache.get(session.getId()+"-tpl", String.class);
        String prefix = "";

        if (tpl == null) {
            Http.Header header = Http.Request.current().headers.get("user-agent");
            String mobile = header.toString().toUpperCase();
            
            if (mobile.contains("IPHONE")) {
                prefix = ".mob";
            }
            if (mobile.contains("IPAD")) {
                prefix = ".tab";
            }

        }else{
            
            if (tpl.toUpperCase().equals("MOBILE")) {
                prefix = ".mob";
            }
            if (tpl.toUpperCase().equals("TABLET")) {
                prefix = ".tab";
            }
        }
        
        //Teste para carregar o desktop
        if(prefix.equals("")){
            if(!templateExists(template())){
                prefix = ".tab";
                if(!templateExists(template().replace(".html", prefix + ".html"))){
                    prefix = ".mob";
                }
            }
        }

        return template().replace(".html", prefix + ".html");

    }
    
    public static void browser(){
         Http.Header header = Http.Request.current().headers.get("user-agent");
         String mobile = header.toString().toUpperCase();
         renderText(mobile);
    }
}
