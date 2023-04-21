package ntnu.idatt2016.v233.SmartMat.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
public class CategoryUtil {

    private final static String[] meat = {"kjøtt", "fisk", "kylling", "svinekjøtt", "oksekjøtt", "lam", "kalkun", "hval", "elg", "rådyr", "reinsdyr", "kveite", "torsk", "laks", "sjøkreps", "blåskjell", "østers", "krabbe", "hummer", "kamskjell", "breiflabb", "makrell", "sardiner", "tuna", "hvitfisk", "skjell", "røkt laks", "spekeskinke", "pinnekjøtt", "ribbe", "fenalår", "sylte", "leverpostei", "pølse", "hamburgere", "kjøttboller", "kjøttdeig", "røykt kjøtt", "sushi", "grillet kylling", "fiskekaker", "fiskepudding", "fiskegrateng", "stekt fisk", "fiskefilet", "lutefisk", "sashimi", "sursild", "rakfisk", "gravet laks", "fiskeboller", "fiskegryte", "fiskepinner", "reker", "kamskjell", "kreps", "blåskjellsuppe", "fiskesuppe", "bouillabaisse", "kjøttkaker", "fårikål", "stroganoff", "kebab", "grillspyd", "svinekoteletter", "lammebiff", "okseskank", "hvalkjøtt", "viltgryte", "reinsdyrstek", "lammelår", "kyllingbryst", "kalkunbryst", "kyllinglår", "kalkunlår", "kyllingvinger", "kalkunvinger", "kyllingfilet", "kalkunfilet", "kebabtallerken", "biffsnadder", "reinsdyrkjøtt", "viltkjøtt", "svinefilet", "oksefilet", "kalkunfilet", "krydderpølse", "fårepølse", "biff", "indrefilet av svin", "ytrefilet av svin", "svinestek", "lammerull", "kalkunpålegg", "kyllingpålegg", "røkelaks", "røkt svinekjøtt", "fiskeboller i hvit saus", "fiskepudding med bacon", "kyllingsalat"};
    private final  static String[] bakedgoodsandgrains = {"brød", "rundstykke", "croissant", "bagel", "brioche", "knekkebrød", "havrekjeks", "kjeks", "kanelbolle", "skillingsbolle", "sjokoladebolle", "muffins", "cupcakes", "kaker", "småkaker", "kransekake", "kokosmakroner", "sukkerkake", "sjokoladekake", "gulrotkake", "brownies", "eplekake", "bærpai", "eplepai", "sjokoladepai", "quiche", "pizza", "pizzasnurrer", "focaccia", "ciabatta", "baguette", "surdeigsbrød", "hvitløksbrød", "fylt brød", "grissini", "lefser", "rugbrød", "speltbrød", "havrebrød", "byggbrød", "vollkornbrød", "knäckebröd", "smörgås", "taco-skjell", "tortilla", "naan-brød", "pita-brød", "falafel", "humus", "baba ghanoush", "tabbouleh", "couscous", "ris", "pasta", "nudler", "risotto", "quinoa", "couscous-salat", "potetsalat", "brødstapper", "krutonger", "popcorn", "risboller", "havregrøt", "risgrøt", "bokhvetegrøt", "knekkebrød med ost", "scones", "syltetøy", "honning", "peanøttsmør", "nutella", "pålegg", "fruktsalat", "fruktkake", "fruktbarer", "nøtteblanding", "kornblanding", "granola", "müsli", "havremel", "hvetemel", "rugmel", "byggmel", "maismel", "potetmel", "bokhvetemel", "hirse", "bulgur", "linser", "kikerter", "erter", "solsikkefrø", "gresskarfrø", "sesamfrø", "chiafrø", "havrekli", "kruskakli"};
    private final  static String[] dairyandegg = {"melk", "fløte", "rømme", "yoghurt", "kefir", "krem", "ost", "smør", "margarin", "egg", "eggehvite", "eggeplomme", "majones", "aioli", "hollandaisesaus", "bernaisesaus", "hvit saus", "béchamelsaus", "pudding", "vaniljesaus", "risgrøt", "lefse", "smørbrød", "frokostblanding", "havregrøt", "knekkebrød", "surdeigsbrød", "baguette", "croissant", "bolle", "kanelbolle", "skillingsbolle", "kringle", "kake", "muffins", "brownies", "sjokolade", "sjokoladekake", "sjokolademousse", "ostekake", "eplekake", "bringebærkake", "blåbærkake", "jordbærkake", "gulrotkake", "sjokoladetrøfler", "iskrem", "sorbet", "frozen yogurt", "karamellpudding", "flan", "cheesecake", "milkshake", "smoothie", "karbonadesmørbrød", "roastbeefsandwich", "skinke- og ostesandwich", "panert kyllingsandwich", "focaccia", "grilled cheese sandwich", "omelett", "eggerøre", "eggs benedict", "french toast", "pannekaker", "vafler", "eggerull", "quiche", "frittata", "scrambled eggs", "egg og bacon", "egg og pølse", "egg og skinke", "egg og avokado", "egg og tomat", "egg og sopp", "egg og løk", "egg og ost", "egg og spinat"};
    private final  static String[] fruitandvegetables = {"agurk", "ananas", "appelsin", "aprikos", "artisjokk", "asparges", "aubergine", "avocado", "banan", "blåbær", "brokkoli", "bønner", "cherrytomater", "chilipepper", "clementin", "drue", "eple", "fennikel", "fiken", "frukt", "grapefrukt", "granateple", "gresskar", "gulrot", "hodekål", "hvitløk", "ingefær", "jordbær", "kirsebær", "klementin", "kokosnøtt", "krutonger", "kål", "kålrot", "kantareller", "lime", "løk", "mais", "mandarin", "mango", "melk", "melon", "morchel", "nektarin", "nøtter", "oliven", "papaya", "paprika", "pære", "persille", "plomme", "poteter", "purre", "reddik", "rips", "rosenkål", "rødbeter", "rød paprika", "salat", "selleri", "sjampinjong", "solsikkefrø", "sopp", "soyabønner", "spinat", "squash", "stangselleri", "stikkelsbær", "sukkererter", "søtpotet", "tomat", "tyttebær", "valnøtter", "vannmelon", "vårløk", "yams", "østerssopp", "aronia", "blomkål", "bringebær", "bær", "bønnespirer", "champignon", "cranberry", "druer", "fikenkaktus", "friske urter", "gressløk", "gul paprika", "hvit asparges", "hvit paprika", "jordbærsaus", "kålpre", "kålrotstappe", "kirsebærtomat", "kjerner", "klementiner", "kålrotkrem", "limeblader", "løpstikke", "mangosaus", "mandelpoteter", "marengs", "mikrogreens", "mint", "multebær", "nektar", "nypoteter", "paprikapulver", "pecannøtter", "pitasalat", "rabarbra", "rødkål", "rød grapefrukt", "rød løk", "rødkålpuré", "rømme", "savoykål", "sikori", "sjalottløk", "soyamelk", "squashsalat", "stjernefrukt", "sukkerertpuré", "syltede grønnsaker", "syrnet melk", "tørkede tranebær", "urte"};

    public static String defineCategory(Long ean){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer MM4a9dkytHAMlx2APMALVgjCAWH8zzK4gRx94Pay");

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String url = "https://kassal.app/api/v1/products/ean/" + ean;
        ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.GET,entity, String.class);

        if(Arrays.stream(meat).anyMatch(response.toString()::contains)) return "meat, fish and chicken";
        if(Arrays.stream(bakedgoodsandgrains).anyMatch(response.toString()::contains)) return "baked goods and grains";
        if(Arrays.stream(dairyandegg).anyMatch(response.toString()::contains)) return "dairy and egg ";
        if(Arrays.stream(fruitandvegetables).anyMatch(response.toString()::contains)) return "fruit and vegetables";
        return "other";
    }
}