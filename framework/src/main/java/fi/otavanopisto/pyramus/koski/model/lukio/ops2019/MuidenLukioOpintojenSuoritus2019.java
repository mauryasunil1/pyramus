package fi.otavanopisto.pyramus.koski.model.lukio.ops2019;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import fi.otavanopisto.pyramus.koski.KoodistoViite;
import fi.otavanopisto.pyramus.koski.koodisto.SuorituksenTyyppi;

/**
 * Kategoria opintojaksoille, jotka eivät liity suoraan mihinkään yksittäiseen oppiaineeseen. Esim. lukiodiplomit tai temaattiset opinnot.
 * @see <a href="https://koski.opintopolku.fi/koski/dokumentaatio/koski-oppija-schema.html?entity=lukionopiskeluoikeus#muidenlukioopintojensuoritus2019">Koski JSON Schema</a>
 */
@JsonDeserialize(using = JsonDeserializer.None.class)
public class MuidenLukioOpintojenSuoritus2019 extends LukionOsasuoritus2019 {

  public MuidenLukioOpintojenSuoritus2019() {
  }
  
  public MuidenLukioOpintojenSuoritus2019(LukionMuidenOpintojenTunniste2019 koulutusmoduuli) {
    this.koulutusmoduuli = koulutusmoduuli;
  }
  
  public LukionMuidenOpintojenTunniste2019 getKoulutusmoduuli() {
    return koulutusmoduuli;
  }
  
  public KoodistoViite<SuorituksenTyyppi> getTyyppi() {
    return tyyppi;
  }
  
  public void setKoulutusmoduuli(LukionMuidenOpintojenTunniste2019 koulutusmoduuli) {
    this.koulutusmoduuli = koulutusmoduuli;
  }

  private final KoodistoViite<SuorituksenTyyppi> tyyppi = new KoodistoViite<>(SuorituksenTyyppi.lukionmuuopinto);
  private LukionMuidenOpintojenTunniste2019 koulutusmoduuli;
}
