package br.com.dev42.queridocarro.extra;

/**
 * Created by sossai on 13/04/17.
 */

public class CorSigla {

    public static String escolheCor(String nomeOficina){
        String colorSring = "";
        String letra = nomeOficina.substring(0,1).toUpperCase();

        switch(letra){

            case "A":
                colorSring = "#213782";
                break;
            case "B":
                colorSring = "#346fbd";
                break;
            case "C":
                colorSring = "#5ea0f4";
                break;
            case "D":
                colorSring = "#f1d142";
                break;
            case "E":
                colorSring = "#f4dd79";
                break;
            case "F":
                colorSring = "#e7bd03";
                break;
            case "G":
                colorSring = "#bdc0c5";
                break;
            case "H":
                colorSring = "#a3a6ab";
                break;
            case "I":
                colorSring = "#f13030";
                break;
            case "J":
                colorSring = "#f45c5c";
                break;
            case "K":
                colorSring = "#a21111";
                break;
            case "L":
                colorSring = "#44a211";
                break;
            case "M":
                colorSring = "#5bcd1c";
                break;
            case "N":
                colorSring = "#2b6e07";
                break;
            case "O":
                colorSring = "#a013f1";
                break;
            case "P":
                colorSring = "#c071ed";
                break;
            case "Q":
                colorSring = "#eba346";
                break;
            case "R":
                colorSring = "#e28e23";
                break;
            case "S":
                colorSring = "#7698b0";
                break;
            case "T":
                colorSring = "#98ce7b";
                break;
            case "U":
                colorSring = "#21edf4";
                break;
            case "V":
                colorSring = "#947144";
                break;
            case "X":
                colorSring = "#938470";
                break;
            case "Y":
                colorSring = "#83ac6d";
                break;
            case "Z":
                colorSring = "#676099";
                break;
            case "W":
                colorSring = "#7fa8d6";
                break;
            default:
                colorSring = "#213782";
                break;
        }
        return colorSring;
    }
}
