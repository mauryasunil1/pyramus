package fi.otavanopisto.pyramus.koski.aikuistenperusopetus;

import java.util.Date;

import fi.otavanopisto.pyramus.koski.koodisto.Kieli;
import fi.otavanopisto.pyramus.koski.koodisto.OpiskeluoikeudenTila;
import fi.otavanopisto.pyramus.koski.koodisto.OppiaineAidinkieliJaKirjallisuus;
import fi.otavanopisto.pyramus.koski.koodisto.PerusopetuksenSuoritusTapa;
import fi.otavanopisto.pyramus.koski.koodisto.SuorituksenTila;
import fi.otavanopisto.pyramus.koski.model.HenkiloUusi;
import fi.otavanopisto.pyramus.koski.model.OpiskeluoikeusJakso;
import fi.otavanopisto.pyramus.koski.model.Oppija;
import fi.otavanopisto.pyramus.koski.model.OrganisaationToimipiste;
import fi.otavanopisto.pyramus.koski.model.OrganisaationToimipisteOID;
import fi.otavanopisto.pyramus.koski.model.aikuistenperusopetus.AikuistenPerusopetuksenOpiskeluoikeus;
import fi.otavanopisto.pyramus.koski.model.aikuistenperusopetus.AikuistenPerusopetuksenOppiaineenSuoritus;
import fi.otavanopisto.pyramus.koski.model.aikuistenperusopetus.AikuistenPerusopetuksenOppiaineenSuoritusAidinkieli;
import fi.otavanopisto.pyramus.koski.model.aikuistenperusopetus.AikuistenPerusopetuksenOppiaineenTunniste;
import fi.otavanopisto.pyramus.koski.model.aikuistenperusopetus.PerusopetuksenOppiaineenOppimaaranSuoritus;

public class APOOppiaineenOppimaaraData {

  public static Oppija getTestStudentMinimal() {
    Oppija oppija = new Oppija();
    oppija.setHenkilo(new HenkiloUusi("111111A111C", "Sally", "Student", "Sally"));
    
    AikuistenPerusopetuksenOpiskeluoikeus opiskeluoikeus = new AikuistenPerusopetuksenOpiskeluoikeus();
    oppija.addOpiskeluoikeus(opiskeluoikeus);
    
    OpiskeluoikeusJakso jakso = new OpiskeluoikeusJakso(new Date(), OpiskeluoikeudenTila.lasna);
    opiskeluoikeus.getTila().addOpiskeluoikeusJakso(jakso );
    
    OrganisaationToimipiste toimipiste = new OrganisaationToimipisteOID("1.2.246.562.10.17749422402");
    AikuistenPerusopetuksenOppiaineenTunniste koulutusmoduuli = new AikuistenPerusopetuksenOppiaineenSuoritusAidinkieli(
        OppiaineAidinkieliJaKirjallisuus.AI1, false);
    AikuistenPerusopetuksenOppiaineenSuoritus oppiaine = new AikuistenPerusopetuksenOppiaineenSuoritus(koulutusmoduuli);
    PerusopetuksenOppiaineenOppimaaranSuoritus suoritus = new PerusopetuksenOppiaineenOppimaaranSuoritus(
        PerusopetuksenSuoritusTapa.koulutus, Kieli.FI, toimipiste, SuorituksenTila.KESKEN, oppiaine );
    opiskeluoikeus.addSuoritus(suoritus);
    
    return oppija;
  }
  
  
}
