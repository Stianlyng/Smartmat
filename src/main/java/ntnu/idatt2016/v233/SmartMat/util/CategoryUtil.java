package ntnu.idatt2016.v233.SmartMat.util;

import java.util.Arrays;

/**
 * Utility class for category
 * @author Pedro Pablo Cardona
 * @version 1.0
 * @since 21.04.2023
 */
public class CategoryUtil {

    private final static String[] MEAT = {"kjøtt", "fisk", "kylling", "svinekjøtt", "oksekjøtt", "lam", "kalkun", "hval", "elg", "rådyr", "reinsdyr", "kveite", "torsk", "laks", "sjøkreps", "blåskjell", "østers", "krabbe", "hummer", "kamskjell", "breiflabb", "makrell", "sardiner", "tuna", "hvitfisk", "skjell", "røkt laks", "spekeskinke", "pinnekjøtt", "ribbe", "fenalår", "sylte", "leverpostei", "pølse", "hamburgere", "kjøttboller", "kjøttdeig", "røykt kjøtt", "sushi", "grillet kylling", "fiskekaker", "fiskepudding", "fiskegrateng", "stekt fisk", "fiskefilet", "lutefisk", "sashimi", "sursild", "rakfisk", "gravet laks", "fiskeboller", "fiskegryte", "fiskepinner", "reker", "kamskjell", "kreps", "blåskjellsuppe", "fiskesuppe", "bouillabaisse", "kjøttkaker", "fårikål", "stroganoff", "kebab", "grillspyd", "svinekoteletter", "lammebiff", "okseskank", "hvalkjøtt", "viltgryte", "reinsdyrstek", "lammelår", "kyllingbryst", "kalkunbryst", "kyllinglår", "kalkunlår", "kyllingvinger", "kalkunvinger", "kyllingfilet", "kalkunfilet", "kebabtallerken", "biffsnadder", "reinsdyrkjøtt", "viltkjøtt", "svinefilet", "oksefilet", "kalkunfilet", "krydderpølse", "fårepølse", "biff", "indrefilet av svin", "ytrefilet av svin", "svinestek", "lammerull", "kalkunpålegg", "kyllingpålegg", "røkelaks", "røkt svinekjøtt", "fiskeboller i hvit saus", "fiskepudding med bacon", "kyllingsalat"};
    private final  static String[] BAKED_GOODS_AND_GRAINS = {"brød", "rundstykke", "croissant", "bagel", "brioche", "knekkebrød", "havrekjeks", "kjeks", "kanelbolle", "skillingsbolle", "sjokoladebolle", "muffins", "cupcakes", "kaker", "småkaker", "kransekake", "kokosmakroner", "sukkerkake", "sjokoladekake", "gulrotkake", "brownies", "eplekake", "bærpai", "eplepai", "sjokoladepai", "quiche", "pizza", "pizzasnurrer", "focaccia", "ciabatta", "baguette", "surdeigsbrød", "hvitløksbrød", "fylt brød", "grissini", "lefser", "rugbrød", "speltbrød", "havrebrød", "byggbrød", "vollkornbrød", "knäckebröd", "smörgås", "taco-skjell", "tortilla", "naan-brød", "pita-brød", "falafel", "humus", "baba ghanoush", "tabbouleh", "couscous", "ris", "pasta", "nudler", "risotto", "quinoa", "couscous-salat", "potetsalat", "brødstapper", "krutonger", "popcorn", "risboller", "havregrøt", "risgrøt", "bokhvetegrøt", "knekkebrød med ost", "scones", "syltetøy", "honning", "peanøttsmør", "nutella", "pålegg", "fruktsalat", "fruktkake", "fruktbarer", "nøtteblanding", "kornblanding", "granola", "müsli", "havremel", "hvetemel", "rugmel", "byggmel", "maismel", "potetmel", "bokhvetemel", "hirse", "bulgur", "linser", "kikerter", "erter", "solsikkefrø", "gresskarfrø", "sesamfrø", "chiafrø", "havrekli", "kruskakli"};
    private final  static String[] DAIRY_AND_EGG = {"melk", "fløte", "rømme", "yoghurt", "kefir", "krem", "ost", "smør", "margarin", "egg", "eggehvite", "eggeplomme", "majones", "aioli", "hollandaisesaus", "bernaisesaus", "hvit saus", "béchamelsaus", "pudding", "vaniljesaus", "risgrøt", "lefse", "smørbrød", "frokostblanding", "havregrøt", "knekkebrød", "surdeigsbrød", "baguette", "croissant", "bolle", "kanelbolle", "skillingsbolle", "kringle", "kake", "muffins", "brownies", "sjokolade", "sjokoladekake", "sjokolademousse", "ostekake", "eplekake", "bringebærkake", "blåbærkake", "jordbærkake", "gulrotkake", "sjokoladetrøfler", "iskrem", "sorbet", "frozen yogurt", "karamellpudding", "flan", "cheesecake", "milkshake", "smoothie", "karbonadesmørbrød", "roastbeefsandwich", "skinke- og ostesandwich", "panert kyllingsandwich", "focaccia", "grilled cheese sandwich", "omelett", "eggerøre", "eggs benedict", "french toast", "pannekaker", "vafler", "eggerull", "quiche", "frittata", "scrambled eggs", "egg og bacon", "egg og pølse", "egg og skinke", "egg og avokado", "egg og tomat", "egg og sopp", "egg og løk", "egg og ost", "egg og spinat"};
    private final  static String[] FRUIT_AND_VEGETABLES = {"agurk", "ananas", "appelsin", "aprikos", "artisjokk", "asparges", "aubergine", "avocado", "banan", "blåbær", "brokkoli", "bønner", "cherrytomater", "chilipepper", "clementin", "drue", "eple", "fennikel", "fiken", "frukt", "grapefrukt", "granateple", "gresskar", "gulrot", "hodekål", "hvitløk", "ingefær", "jordbær", "kirsebær", "klementin", "kokosnøtt", "krutonger", "kål", "kålrot", "kantareller", "lime", "løk", "mais", "mandarin", "mango", "melk", "melon", "morchel", "nektarin", "nøtter", "oliven", "papaya", "paprika", "pære", "persille", "plomme", "poteter", "purre", "reddik", "rips", "rosenkål", "rødbeter", "rød paprika", "salat", "selleri", "sjampinjong", "solsikkefrø", "sopp", "soyabønner", "spinat", "squash", "stangselleri", "stikkelsbær", "sukkererter", "søtpotet", "tomat", "tyttebær", "valnøtter", "vannmelon", "vårløk", "yams", "østerssopp", "aronia", "blomkål", "bringebær", "bær", "bønnespirer", "champignon", "cranberry", "druer", "fikenkaktus", "friske urter", "gressløk", "gul paprika", "hvit asparges", "hvit paprika", "jordbærsaus", "kålpre", "kålrotstappe", "kirsebærtomat", "kjerner", "klementiner", "kålrotkrem", "limeblader", "løpstikke", "mangosaus", "mandelpoteter", "marengs", "mikrogreens", "mint", "multebær", "nektar", "nypoteter", "paprikapulver", "pecannøtter", "pitasalat", "rabarbra", "rødkål", "rød grapefrukt", "rød løk", "rødkålpuré", "rømme", "savoykål", "sikori", "sjalottløk", "soyamelk", "squashsalat", "stjernefrukt", "sukkerertpuré", "syltede grønnsaker", "syrnet melk", "tørkede tranebær", "urte"};

    /**
     * Static method that define the category of the product.
     * @param name name of the product.
     * @param description description of the product
     * @return String A possible category.
     */
    public static String defineCategory(String name, String description){

        String response =  name +" " + description;

        if(Arrays.stream(MEAT).anyMatch(response::contains)) return "meat, fish and chicken";
        if(Arrays.stream(BAKED_GOODS_AND_GRAINS).anyMatch(response::contains)) return "baked goods and grains";
        if(Arrays.stream(DAIRY_AND_EGG).anyMatch(response::contains)) return "dairy and egg ";
        if(Arrays.stream(FRUIT_AND_VEGETABLES).anyMatch(response::contains)) return "fruit and vegetables";
        return "other";
    }

    /**
     * Returns the category name corresponding to the given category number.
     *
     * @param categoryNumber the category number
     * @return the category name
     * @throws IllegalArgumentException if the given category number is invalid
     */
    public static String getCategoryName(int categoryNumber) {
        return switch (categoryNumber) {
            case 1 -> "meat, fish and chicken";
            case 2 -> "baked goods and grains";
            case 3 -> "dairy and egg";
            case 4 -> "other";
            case 5 -> "fruit and vegetables";
            default -> throw new IllegalArgumentException("Invalid category number: " + categoryNumber);
        };
    }
}
