package controllers;

import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;

public class DefaultController extends Controller {
	
	protected static final int DESKTOP = 0;
	protected static final int TABLET = 1;
	protected static final int MOBILE = 2;
	
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
	
	
	protected static void home(String url) {
		if(getDevice()==TABLET){
			Application.list(url);
		}else{
			redirect(url+"/index");
		}
	}
	
	
	protected static int getDevice() {
		/*
			0 = DESKTOP
			1 = TABLET
			2 = MOBILE
		*/
		
        String tpl = Cache.get(session.getId()+"-tpl", String.class);

        if (tpl == null) {
			
            Http.Header header = Http.Request.current().headers.get("user-agent");
            String mobile = header.toString().toUpperCase();
            
            if (mobile.contains("IPHONE")) {
                return MOBILE;
            }
            if (mobile.contains("IPAD")) {
                return TABLET;
            }

        }else{
            
            if (tpl.toUpperCase().equals("MOBILE")) {
                return MOBILE;
            }
            if (tpl.toUpperCase().equals("TABLET")) {
                return TABLET;
            }
        }
		
		return DESKTOP;	
	}

    protected static String getTemplateMultiView() {

        String prefix = "";
		
		if(getDevice()==MOBILE){
			prefix = ".mob";
		}
		
		if(getDevice()==TABLET){
			prefix = ".tab";
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
