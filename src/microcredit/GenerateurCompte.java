package microcredit;

import java.util.Random;

public class GenerateurCompte {

    public static String genererNumeroCompte() {
        Random random = new Random();
        long numero = 1000000000L +
                (long)(random.nextDouble() * 9000000000L);
        return "06-"+String.valueOf(numero)+"-DC";
    }
}