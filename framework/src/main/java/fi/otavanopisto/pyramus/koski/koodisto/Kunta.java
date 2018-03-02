package fi.otavanopisto.pyramus.koski.koodisto;

import java.util.HashMap;
import java.util.Map;

import fi.otavanopisto.pyramus.koski.KoodistoEnum;

@KoodistoEnum("kunta")
public enum Kunta {

  K001("001"),
  K002("002"),
  K003("003"),
  K004("004"),
  K005("005"),
  K006("006"),
  K007("007"),
  K008("008"),
  K009("009"),
  K010("010"),
  K011("011"),
  K012("012"),
  K013("013"),
  K014("014"),
  K015("015"),
  K016("016"),
  K017("017"),
  K018("018"),
  K019("019"),
  K020("020"),
  K032("032"),
  K033("033"),
  K034("034"),
  K035("035"),
  K039("039"),
  K040("040"),
  K043("043"),
  K044("044"),
  K045("045"),
  K046("046"),
  K047("047"),
  K048("048"),
  K049("049"),
  K050("050"),
  K051("051"),
  K052("052"),
  K060("060"),
  K061("061"),
  K062("062"),
  K065("065"),
  K068("068"),
  K069("069"),
  K070("070"),
  K071("071"),
  K072("072"),
  K073("073"),
  K074("074"),
  K075("075"),
  K076("076"),
  K077("077"),
  K078("078"),
  K079("079"),
  K080("080"),
  K081("081"),
  K082("082"),
  K083("083"),
  K084("084"),
  K085("085"),
  K086("086"),
  K087("087"),
  K088("088"),
  K089("089"),
  K090("090"),
  K091("091"),
  K092("092"),
  K093("093"),
  K094("094"),
  K095("095"),
  K096("096"),
  K097("097"),
  K098("098"),
  K099("099"),
  K100("100"),
  K101("101"),
  K102("102"),
  K103("103"),
  K104("104"),
  K105("105"),
  K106("106"),
  K107("107"),
  K108("108"),
  K109("109"),
  K110("110"),
  K111("111"),
  K139("139"),
  K140("140"),
  K141("141"),
  K142("142"),
  K143("143"),
  K144("144"),
  K145("145"),
  K146("146"),
  K147("147"),
  K148("148"),
  K149("149"),
  K150("150"),
  K151("151"),
  K152("152"),
  K153("153"),
  K162("162"),
  K163("163"),
  K164("164"),
  K165("165"),
  K166("166"),
  K167("167"),
  K168("168"),
  K169("169"),
  K170("170"),
  K171("171"),
  K172("172"),
  K173("173"),
  K174("174"),
  K175("175"),
  K176("176"),
  K177("177"),
  K178("178"),
  K179("179"),
  K180("180"),
  K181("181"),
  K182("182"),
  K183("183"),
  K184("184"),
  K185("185"),
  K186("186"),
  K200("200"),
  K201("201"),
  K202("202"),
  K203("203"),
  K204("204"),
  K205("205"),
  K206("206"),
  K207("207"),
  K208("208"),
  K209("209"),
  K210("210"),
  K211("211"),
  K212("212"),
  K213("213"),
  K214("214"),
  K215("215"),
  K216("216"),
  K217("217"),
  K218("218"),
  K219("219"),
  K220("220"),
  K221("221"),
  K222("222"),
  K223("223"),
  K224("224"),
  K225("225"),
  K226("226"),
  K227("227"),
  K228("228"),
  K229("229"),
  K230("230"),
  K231("231"),
  K232("232"),
  K233("233"),
  K234("234"),
  K235("235"),
  K236("236"),
  K237("237"),
  K238("238"),
  K239("239"),
  K240("240"),
  K241("241"),
  K242("242"),
  K243("243"),
  K244("244"),
  K245("245"),
  K246("246"),
  K247("247"),
  K248("248"),
  K249("249"),
  K250("250"),
  K251("251"),
  K252("252"),
  K253("253"),
  K254("254"),
  K255("255"),
  K256("256"),
  K257("257"),
  K258("258"),
  K259("259"),
  K260("260"),
  K261("261"),
  K262("262"),
  K263("263"),
  K264("264"),
  K265("265"),
  K266("266"),
  K267("267"),
  K268("268"),
  K269("269"),
  K270("270"),
  K271("271"),
  K272("272"),
  K273("273"),
  K274("274"),
  K275("275"),
  K276("276"),
  K277("277"),
  K278("278"),
  K279("279"),
  K280("280"),
  K281("281"),
  K282("282"),
  K283("283"),
  K284("284"),
  K285("285"),
  K286("286"),
  K287("287"),
  K288("288"),
  K289("289"),
  K290("290"),
  K291("291"),
  K292("292"),
  K293("293"),
  K294("294"),
  K295("295"),
  K296("296"),
  K297("297"),
  K298("298"),
  K299("299"),
  K300("300"),
  K301("301"),
  K302("302"),
  K303("303"),
  K304("304"),
  K305("305"),
  K306("306"),
  K307("307"),
  K308("308"),
  K309("309"),
  K310("310"),
  K311("311"),
  K312("312"),
  K313("313"),
  K314("314"),
  K315("315"),
  K316("316"),
  K317("317"),
  K318("318"),
  K319("319"),
  K320("320"),
  K321("321"),
  K322("322"),
  K397("397"),
  K398("398"),
  K399("399"),
  K400("400"),
  K401("401"),
  K402("402"),
  K403("403"),
  K404("404"),
  K405("405"),
  K406("406"),
  K407("407"),
  K408("408"),
  K409("409"),
  K410("410"),
  K411("411"),
  K412("412"),
  K413("413"),
  K414("414"),
  K415("415"),
  K416("416"),
  K417("417"),
  K418("418"),
  K419("419"),
  K420("420"),
  K421("421"),
  K422("422"),
  K423("423"),
  K424("424"),
  K425("425"),
  K426("426"),
  K427("427"),
  K428("428"),
  K429("429"),
  K430("430"),
  K431("431"),
  K432("432"),
  K433("433"),
  K434("434"),
  K435("435"),
  K436("436"),
  K437("437"),
  K438("438"),
  K439("439"),
  K440("440"),
  K441("441"),
  K442("442"),
  K443("443"),
  K444("444"),
  K445("445"),
  K475("475"),
  K476("476"),
  K477("477"),
  K478("478"),
  K479("479"),
  K480("480"),
  K481("481"),
  K482("482"),
  K483("483"),
  K484("484"),
  K485("485"),
  K486("486"),
  K487("487"),
  K488("488"),
  K489("489"),
  K490("490"),
  K491("491"),
  K492("492"),
  K493("493"),
  K494("494"),
  K495("495"),
  K496("496"),
  K497("497"),
  K498("498"),
  K499("499"),
  K500("500"),
  K501("501"),
  K502("502"),
  K503("503"),
  K504("504"),
  K505("505"),
  K506("506"),
  K507("507"),
  K508("508"),
  K529("529"),
  K530("530"),
  K531("531"),
  K532("532"),
  K533("533"),
  K534("534"),
  K535("535"),
  K536("536"),
  K537("537"),
  K538("538"),
  K539("539"),
  K540("540"),
  K541("541"),
  K542("542"),
  K543("543"),
  K544("544"),
  K545("545"),
  K559("559"),
  K560("560"),
  K561("561"),
  K562("562"),
  K563("563"),
  K564("564"),
  K565("565"),
  K566("566"),
  K567("567"),
  K573("573"),
  K574("574"),
  K575("575"),
  K576("576"),
  K577("577"),
  K578("578"),
  K579("579"),
  K580("580"),
  K581("581"),
  K582("582"),
  K583("583"),
  K584("584"),
  K585("585"),
  K586("586"),
  K587("587"),
  K588("588"),
  K589("589"),
  K590("590"),
  K591("591"),
  K592("592"),
  K593("593"),
  K594("594"),
  K595("595"),
  K596("596"),
  K597("597"),
  K598("598"),
  K599("599"),
  K600("600"),
  K601("601"),
  K602("602"),
  K603("603"),
  K604("604"),
  K605("605"),
  K606("606"),
  K607("607"),
  K608("608"),
  K609("609"),
  K610("610"),
  K611("611"),
  K612("612"),
  K613("613"),
  K614("614"),
  K615("615"),
  K616("616"),
  K617("617"),
  K618("618"),
  K619("619"),
  K620("620"),
  K621("621"),
  K622("622"),
  K623("623"),
  K624("624"),
  K625("625"),
  K626("626"),
  K627("627"),
  K628("628"),
  K629("629"),
  K630("630"),
  K631("631"),
  K632("632"),
  K633("633"),
  K634("634"),
  K635("635"),
  K636("636"),
  K637("637"),
  K638("638"),
  K640("640"),
  K678("678"),
  K679("679"),
  K680("680"),
  K681("681"),
  K682("682"),
  K683("683"),
  K684("684"),
  K685("685"),
  K686("686"),
  K687("687"),
  K688("688"),
  K689("689"),
  K690("690"),
  K691("691"),
  K692("692"),
  K693("693"),
  K694("694"),
  K695("695"),
  K696("696"),
  K697("697"),
  K698("698"),
  K699("699"),
  K700("700"),
  K701("701"),
  K702("702"),
  K703("703"),
  K704("704"),
  K705("705"),
  K706("706"),
  K707("707"),
  K708("708"),
  K710("710"),
  K728("728"),
  K729("729"),
  K730("730"),
  K731("731"),
  K732("732"),
  K733("733"),
  K734("734"),
  K735("735"),
  K736("736"),
  K737("737"),
  K738("738"),
  K739("739"),
  K740("740"),
  K741("741"),
  K742("742"),
  K743("743"),
  K744("744"),
  K745("745"),
  K746("746"),
  K747("747"),
  K748("748"),
  K749("749"),
  K750("750"),
  K751("751"),
  K752("752"),
  K753("753"),
  K754("754"),
  K755("755"),
  K756("756"),
  K757("757"),
  K758("758"),
  K759("759"),
  K760("760"),
  K761("761"),
  K762("762"),
  K763("763"),
  K764("764"),
  K765("765"),
  K766("766"),
  K767("767"),
  K768("768"),
  K769("769"),
  K770("770"),
  K771("771"),
  K772("772"),
  K773("773"),
  K774("774"),
  K775("775"),
  K776("776"),
  K777("777"),
  K778("778"),
  K779("779"),
  K780("780"),
  K781("781"),
  K782("782"),
  K783("783"),
  K784("784"),
  K785("785"),
  K786("786"),
  K787("787"),
  K788("788"),
  K789("789"),
  K790("790"),
  K791("791"),
  K831("831"),
  K832("832"),
  K833("833"),
  K834("834"),
  K835("835"),
  K836("836"),
  K837("837"),
  K838("838"),
  K839("839"),
  K840("840"),
  K841("841"),
  K842("842"),
  K843("843"),
  K844("844"),
  K845("845"),
  K846("846"),
  K847("847"),
  K848("848"),
  K849("849"),
  K850("850"),
  K851("851"),
  K852("852"),
  K853("853"),
  K854("854"),
  K855("855"),
  K856("856"),
  K857("857"),
  K858("858"),
  K859("859"),
  K860("860"),
  K861("861"),
  K862("862"),
  K863("863"),
  K864("864"),
  K885("885"),
  K886("886"),
  K887("887"),
  K888("888"),
  K889("889"),
  K890("890"),
  K891("891"),
  K892("892"),
  K893("893"),
  K894("894"),
  K895("895"),
  K896("896"),
  K897("897"),
  K905("905"),
  K906("906"),
  K907("907"),
  K908("908"),
  K909("909"),
  K910("910"),
  K911("911"),
  K912("912"),
  K913("913"),
  K914("914"),
  K915("915"),
  K916("916"),
  K917("917"),
  K918("918"),
  K919("919"),
  K920("920"),
  K921("921"),
  K922("922"),
  K923("923"),
  K924("924"),
  K925("925"),
  K926("926"),
  K927("927"),
  K928("928"),
  K929("929"),
  K930("930"),
  K931("931"),
  K932("932"),
  K933("933"),
  K934("934"),
  K935("935"),
  K936("936"),
  K937("937"),
  K938("938"),
  K939("939"),
  K940("940"),
  K941("941"),
  K942("942"),
  K943("943"),
  K944("944"),
  K945("945"),
  K946("946"),
  K971("971"),
  K972("972"),
  K973("973"),
  K974("974"),
  K975("975"),
  K976("976"),
  K977("977"),
  K978("978"),
  K979("979"),
  K980("980"),
  K981("981"),
  K988("988"),
  K989("989"),
  K990("990"),
  K991("991"),
  K992("992"),
  K993("993"),
  K994("994"),
  K997("997"),
  K999("999");
  
  private Kunta(String value) {
    this.value = value;
  }
  
  @Override
  public String toString() {
    return String.valueOf(value);
  }
  
  public String getValue() {
    return value;
  }
  
  public static Kunta reverseLookup(String value) {
    try {
      return lookup.get(value);
    } catch (Exception ex) {
    }
    return null;
  }

  private String value;
  private static Map<String, Kunta> lookup = new HashMap<>();

  static {
    for (Kunta v : values()) {
      lookup.put(v.getValue(), v);
    }
  }
}
