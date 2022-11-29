/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import utils.OsUtils;

import java.util.ArrayList;
import java.util.List;

public class Compilador {
    private static final String WIN_PATH = "Compilador\\src\\compilador\\codigoTeste.txt";
    private static final String LINUX_PATH = "Compilador/src/compilador/codigoTeste.txt";

    private static Lexico lexico;
    private static Sintatica sintatica;

    private static final OsUtils osUtils = new OsUtils();

    private static String getPath() {
        if (osUtils.isWindows()) {
            return WIN_PATH;
        } else {
            return LINUX_PATH;
        }
    }
    public static void main(String[] args) {
//        lexico = new Lexico(getPath());
//        lexico.start();
        sintatica = new Sintatica(getPath());
        sintatica.start();
    }

}
