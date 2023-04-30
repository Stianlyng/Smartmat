package ntnu.idatt2016.v233.SmartMat.util;

import java.util.*;

/**
 * Utility class for category
 * @author Pedro Pablo Cardona
 * @version 1.0
 * @since 21.04.2023
 */
public class CategoryUtil {

    private static final Map<String, Integer> CATEGORY_WEIGHTS = new HashMap<>();
    static {
        CATEGORY_WEIGHTS.put("meat, fish and chicken", 5);
        CATEGORY_WEIGHTS.put("baked goods and grains", 2);
        CATEGORY_WEIGHTS.put("dairy and egg", 3);
        CATEGORY_WEIGHTS.put("fruit and vegetables", 1);
    }

    private static final List<String> MEAT = List.of("kjøtt", "fisk", "kylling", "svinekjøtt", "oksekjøtt", "lam", "kalkun", "hval", "elg", "rådyr", "reinsdyr", "kveite", "torsk", "laks", "sjøkreps", "blåskjell", "østers", "krabbe", "hummer", "kamskjell", "breiflabb", "makrell", "sardiner", "tuna", "hvitfisk", "skjell", "røkt laks", "spekeskinke", "pinnekjøtt", "ribbe", "fenalår", "sylte", "leverpostei", "pølse", "hamburgere", "kjøttboller", "kjøttdeig", "røykt kjøtt", "sushi", "grillet kylling", "fiskekaker", "fiskepudding", "fiskegrateng", "stekt fisk", "fiskefilet", "lutefisk", "sashimi", "sursild", "rakfisk", "gravet laks", "fiskeboller", "fiskegryte", "fiskepinner", "reker", "kamskjell", "kreps", "blåskjellsuppe", "fiskesuppe", "bouillabaisse", "kjøttkaker", "fårikål", "stroganoff", "kebab", "grillspyd", "svinekoteletter", "lammebiff", "okseskank", "hvalkjøtt", "viltgryte", "reinsdyrstek", "lammelår", "kyllingbryst", "kalkunbryst", "kyllinglår", "kalkunlår", "kyllingvinger", "kalkunvinger", "kyllingfilet", "kalkunfilet", "kebabtallerken", "biffsnadder", "reinsdyrkjøtt", "viltkjøtt", "svinefilet", "oksefilet", "kalkunfilet", "krydderpølse", "fårepølse", "biff", "indrefilet av svin", "ytrefilet av svin", "svinestek", "lammerull", "kalkunpålegg", "kyllingpålegg", "røkelaks", "røkt svinekjøtt", "fiskeboller i hvit saus", "fiskepudding med bacon", "kyllingsalat");
    private static final List<String> BAKED_GOODS_AND_GRAINS = List.of("brød", "rundstykke", "croissant", "bagel", "brioche", "knekkebrød", "havrekjeks", "kjeks", "kanelbolle", "skillingsbolle", "sjokoladebolle", "muffins", "cupcakes", "kaker", "småkaker", "kransekake", "kokosmakroner", "sukkerkake", "sjokoladekake", "gulrotkake", "brownies", "eplekake", "bærpai", "eplepai", "sjokoladepai", "quiche", "pizza", "pizzasnurrer", "focaccia", "ciabatta", "baguette", "surdeigsbrød", "hvitløksbrød", "fylt brød", "grissini", "lefser", "rugbrød", "speltbrød", "havrebrød", "byggbrød", "vollkornbrød", "knäckebröd", "smörgås", "taco-skjell", "tortilla", "naan-brød", "pita-brød", "falafel", "humus", "baba ghanoush", "tabbouleh", "couscous", "ris", "pasta", "nudler", "risotto", "quinoa", "couscous-salat", "potetsalat", "brødstapper", "krutonger", "popcorn", "risboller", "havregrøt", "risgrøt", "bokhvetegrøt", "knekkebrød med ost", "scones", "syltetøy", "honning", "peanøttsmør", "nutella", "pålegg", "fruktsalat", "fruktkake", "fruktbarer", "nøtteblanding", "kornblanding", "granola", "müsli", "havremel", "hvetemel", "rugmel", "byggmel", "maismel", "potetmel", "bokhvetemel", "hirse", "bulgur", "linser", "kikerter", "erter", "solsikkefrø", "gresskarfrø", "sesamfrø", "chiafrø", "havrekli", "kruskakli");
    private static final List<String> DAIRY_AND_EGG = List.of("melk", "fløte", "rømme", "yoghurt", "kefir", "krem", "ost", "smør", "margarin", "egg", "eggehvite", "eggeplomme", "majones", "aioli", "hollandaisesaus", "bernaisesaus", "hvit saus", "béchamelsaus", "pudding", "vaniljesaus", "risgrøt", "lefse", "smørbrød", "frokostblanding", "havregrøt", "knekkebrød", "surdeigsbrød", "baguette", "croissant", "bolle", "kanelbolle", "skillingsbolle", "kringle", "kake", "muffins", "brownies", "sjokolade", "sjokoladekake", "sjokolademousse", "ostekake", "eplekake", "bringebærkake", "blåbærkake", "jordbærkake", "gulrotkake", "sjokoladetrøfler", "iskrem", "sorbet", "frozen yogurt", "karamellpudding", "flan", "cheesecake", "milkshake", "smoothie", "karbonadesmørbrød", "roastbeefsandwich", "skinke- og ostesandwich", "panert kyllingsandwich", "focaccia", "grilled cheese sandwich", "omelett", "eggerøre", "eggs benedict", "french toast", "pannekaker", "vafler", "eggerull", "quiche", "frittata", "scrambled eggs", "egg og bacon", "egg og pølse", "egg og skinke", "egg og avokado", "egg og tomat", "egg og sopp", "egg og løk", "egg og ost", "egg og spinat");
    private static final List<String> FRUIT_AND_VEGETABLES = List.of("augur", "ananas", "appelsin", "aprikos", "artisjokk", "asparges", "aubergine", "avocado", "banan", "blåbær", "brokkoli", "bønner", "cherrytomater", "chilipepper", "clementin", "drue", "eple", "fennikel", "fiken", "frukt", "grapefrukt", "granateple", "gresskar", "gulrot", "hodekål", "hvitløk", "ingefær", "jordbær", "kirsebær", "klementin", "kokosnøtt", "krutonger", "kål", "kålrot", "kantareller", "lime", "løk", "mais", "mandarin", "mango", "melk", "melon", "morchel", "nektarin", "nøtter", "oliven", "papaya", "paprika", "pære", "persille", "plomme", "poteter", "purre", "reddik", "rips", "rosenkål", "rødbeter", "rød paprika", "salat", "selleri", "sjampinjong", "solsikkefrø", "sopp", "soyabønner", "spinat", "squash", "stangselleri", "stikkelsbær", "sukkererter", "søtpotet", "tomat", "tyttebær", "valnøtter", "vannmelon", "vårløk", "yams", "østerssopp", "aronia", "blomkål", "bringebær", "bær", "bønnespirer", "champignon", "cranberry", "druer", "fikenkaktus", "friske urter", "gressløk", "gul paprika", "hvit asparges", "hvit paprika", "jordbærsaus", "kålpre", "kålrotstappe", "kirsebærtomat", "kjerner", "klementiner", "kålrotkrem", "limeblader", "løpstikke", "mangosaus", "mandelpoteter", "marengs", "mikrogreens", "mint", "multebær", "nektar", "nypoteter", "paprikapulver", "pecannøtter", "pitasalat", "rabarbra", "rødkål", "rød grapefrukt", "rød løk", "rødkålpuré", "rømme", "savoykål", "sikori", "sjalottløk", "soyamelk", "squashsalat", "stjernefrukt", "sukkerertpuré", "syltede grønnsaker", "syrnet melk", "tørkede tranebær", "urte");
    private static final List<String> HARAM = List.of(
            "svin", "bacon", "skinke", "pølse", "smult", "gelatin", "gelatin av storfekjøtt", "animalsk fett",
            "alkohol", "øl", "vin", "brennevin", "brennevin", "whiskey", "rom", "vodka", "gin", "tequila",
            "brandy", "cognac", "aperitiffer", "portvin", "vermut", "sake", "malt", "gjæret", "destillert",
            "grappa", "kirsebærbrannvin", "sherry", "sider", "mjød"
    );

    private static List<String> getCategoryKeywords(String category) {
        return switch (category) {
            case "meat, fish and chicken" -> MEAT;
            case "baked goods and grains" -> BAKED_GOODS_AND_GRAINS;
            case "dairy and egg" -> DAIRY_AND_EGG;
            case "fruit and vegetables" -> FRUIT_AND_VEGETABLES;
            default -> Collections.emptyList();
        };
    }

    /**
     * Static method that define the category of the product.
     * @param name name of the product.
     * @param description description of the product
     * @return String A possible category.
     */
    public static String defineCategory(String name, String description) {

        Map<String, Integer> weights = new HashMap<>();
        int totalWeight = 0;

        for (String category : CATEGORY_WEIGHTS.keySet()) {
            int categoryWeight = CATEGORY_WEIGHTS.get(category);
            int categoryTotal = 0;

            List<String> categoryKeywords = getCategoryKeywords(category);
            for (String keyword : categoryKeywords) {
                if (name.toLowerCase().contains(keyword) || description.toLowerCase().contains(keyword)) {
                    categoryTotal += categoryWeight;
                    totalWeight += categoryWeight;
                }
            }

            if (categoryTotal > 0) {
                weights.put(category, categoryTotal);
            }
        }

        if (totalWeight == 0 || totalWeight > 20) {
            weights.put("other", 1);
        }

        String highestWeightCategory = "";
        int highestWeight = 0;

        for (Map.Entry<String, Integer> entry : weights.entrySet()) {
            String category = entry.getKey();
            int weight = entry.getValue();

            if (weight > highestWeight) {
                highestWeightCategory = category;
                highestWeight = weight;
            }
        }

        return highestWeightCategory;
    }

    /**
     * Determines whether a food product is vegan, based on its name and description.
     * A product is not vegan if it contains any meat or dairy/egg-related keywords.
     *
     * @param name the name of the food product
     * @param description the description of the food product
     * @return true if the food product is vegan, false otherwise
     */
    public static boolean isVegan(String name, String description) {
        for(String keywords: MEAT){
            if(name.toLowerCase().contains(keywords)) return false;
            if(description.toLowerCase().contains(keywords)) return false;
        }
        for(String keywords: DAIRY_AND_EGG){
            if(name.toLowerCase().contains(keywords)) return false;
            if(description.toLowerCase().contains(keywords)) return false;
        }
        return true;
    }

    /**
     * Determines whether a food product is vegetarian, based on its name and description,
     * and whether it is already determined to be vegan or not.
     * A product is not vegetarian if it contains any dairy/egg-related keywords.
     *
     * @param name the name of the food product
     * @param description the description of the food product
     * @param isVegan true if the product is already determined to be vegan, false otherwise
     * @return true if the food product is vegetarian, false otherwise
     */
    public static boolean isVegetarian(String name, String description, boolean isVegan) {
        if(isVegan) return true;
        for(String keywords: DAIRY_AND_EGG){
            if(name.toLowerCase().contains(keywords)) return false;
            if(description.toLowerCase().contains(keywords)) return false;
        }
        return true;
    }

    /**
     * Determines whether a food product is halal, based on its name and description,
     * and whether it is already determined to be vegetarian or vegan.
     * A product is not halal if it contains any haram-related keywords.
     *
     * @param name the name of the food product
     * @param description the description of the food product
     * @param isVegetarian true if the product is already determined to be vegetarian, false otherwise
     * @return true if the food product is halal, false otherwise
     */
    public static boolean isHalal(String name, String description, boolean isVegetarian) {
        if(isVegetarian) return true;
        for (String keywords: HARAM){
            if(name.toLowerCase().contains(keywords)) return false;
            if(description.toLowerCase().contains(keywords)) return false;
        }
        return true;
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
