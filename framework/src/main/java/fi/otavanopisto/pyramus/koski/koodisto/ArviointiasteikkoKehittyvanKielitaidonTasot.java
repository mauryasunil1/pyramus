package fi.otavanopisto.pyramus.koski.koodisto;

import java.util.HashMap;
import java.util.Map;

import fi.otavanopisto.pyramus.koski.KoodistoEnum;

@KoodistoEnum("arviointiasteikkokehittyvankielitaidontasot")
public enum ArviointiasteikkoKehittyvanKielitaidonTasot {

  A1_1("A1.1"),
  A1_2("A1.2"),
  A1_3("A1.3"),
  A2_1("A2.1"),
  A2_2("A2.2"),
  B1_1("B1.1"),
  B1_2("B1.2"),
  B2_1("B2.1"),
  B2_2("B2.2"),
  C1_1("C1.1"),
  C1_2("C1.2"),
  C2_1("C2.1"),
  C2_2("C2.2"),
  alle_A1_1("alle_A1.1"),
  yli_C1_1("yli_C1.1");

  ArviointiasteikkoKehittyvanKielitaidonTasot(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
  
  public String getValue() {
    return value;
  }

  public static ArviointiasteikkoKehittyvanKielitaidonTasot get(String value) {
    return lookup.get(value);
  }
  
  /**
   * Returns enum based on the string value. Used in deserialization process.
   * 
   * @param value the string value
   * @return the enum
   */
  public static ArviointiasteikkoKehittyvanKielitaidonTasot reverseLookup(String value) {
    return get(value);
  }

  private String value;
  private static Map<String, ArviointiasteikkoKehittyvanKielitaidonTasot> lookup = new HashMap<>();

  static {
    for (ArviointiasteikkoKehittyvanKielitaidonTasot v : values())
      lookup.put(v.getValue(), v);
  }
  
}
