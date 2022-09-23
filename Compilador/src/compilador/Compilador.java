/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import utils.OsUtils;

/**
 *
 * @author tarci
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    private static final String WIN_PATH = "Compilador\\src\\compilador\\codigo.txt";
    private static final String LINUX_PATH = "Compilador/src/compilador/codigo.txt";

    private static Lexico lexico;

    private static final OsUtils osUtils = new OsUtils();

    private static String getPath() {
        if(osUtils.isWindows()) {
            return WIN_PATH;
        } else {
            return LINUX_PATH;
        }
    }

    public static void analiseLexica() {
        Token t = null;
        while((t = lexico.nextToken()) != null){
            System.out.println(t.toString());
        }
    }

    public static void main(String[] args) {
        lexico = new Lexico(getPath());
        analiseLexica();
    }
    
}
