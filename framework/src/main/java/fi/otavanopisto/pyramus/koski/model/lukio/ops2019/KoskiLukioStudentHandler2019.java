package fi.otavanopisto.pyramus.koski.model.lukio.ops2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import fi.otavanopisto.pyramus.dao.students.StudentStudyPeriodDAO;
import fi.otavanopisto.pyramus.domainmodel.base.CourseOptionality;
import fi.otavanopisto.pyramus.domainmodel.base.EducationType;
import fi.otavanopisto.pyramus.domainmodel.base.Subject;
import fi.otavanopisto.pyramus.domainmodel.courses.Course;
import fi.otavanopisto.pyramus.domainmodel.grading.CourseAssessment;
import fi.otavanopisto.pyramus.domainmodel.grading.TransferCredit;
import fi.otavanopisto.pyramus.domainmodel.koski.KoskiPersonState;
import fi.otavanopisto.pyramus.domainmodel.students.Student;
import fi.otavanopisto.pyramus.domainmodel.students.StudentLodgingPeriod;
import fi.otavanopisto.pyramus.domainmodel.students.StudentStudyPeriod;
import fi.otavanopisto.pyramus.domainmodel.students.StudentStudyPeriodType;
import fi.otavanopisto.pyramus.koski.CreditStub;
import fi.otavanopisto.pyramus.koski.CreditStubCredit;
import fi.otavanopisto.pyramus.koski.KoskiConsts;
import fi.otavanopisto.pyramus.koski.KoskiStudentHandler;
import fi.otavanopisto.pyramus.koski.KoskiStudentId;
import fi.otavanopisto.pyramus.koski.KoskiStudyProgrammeHandler;
import fi.otavanopisto.pyramus.koski.OpiskelijanOPS;
import fi.otavanopisto.pyramus.koski.OppiaineenSuoritusWithSubject;
import fi.otavanopisto.pyramus.koski.StudentSubjectSelections;
import fi.otavanopisto.pyramus.koski.koodisto.ArviointiasteikkoYleissivistava;
import fi.otavanopisto.pyramus.koski.koodisto.Kieli;
import fi.otavanopisto.pyramus.koski.koodisto.Kielivalikoima;
import fi.otavanopisto.pyramus.koski.koodisto.KoskiOppiaineetYleissivistava;
import fi.otavanopisto.pyramus.koski.koodisto.LukionKurssinTyyppi;
import fi.otavanopisto.pyramus.koski.koodisto.LukionMuutOpinnot;
import fi.otavanopisto.pyramus.koski.koodisto.LukionOppimaara;
import fi.otavanopisto.pyramus.koski.koodisto.ModuuliKoodistoLOPS2021;
import fi.otavanopisto.pyramus.koski.koodisto.OpintojenRahoitus;
import fi.otavanopisto.pyramus.koski.koodisto.OppiaineAidinkieliJaKirjallisuus;
import fi.otavanopisto.pyramus.koski.koodisto.OppiaineMatematiikka;
import fi.otavanopisto.pyramus.koski.koodisto.SuorituksenTyyppi;
import fi.otavanopisto.pyramus.koski.model.KurssinArviointi;
import fi.otavanopisto.pyramus.koski.model.Laajuus;
import fi.otavanopisto.pyramus.koski.model.Majoitusjakso;
import fi.otavanopisto.pyramus.koski.model.Opiskeluoikeus;
import fi.otavanopisto.pyramus.koski.model.OrganisaationToimipiste;
import fi.otavanopisto.pyramus.koski.model.OrganisaationToimipisteOID;
import fi.otavanopisto.pyramus.koski.model.PaikallinenKoodi;
import fi.otavanopisto.pyramus.koski.model.lukio.LukionOpiskeluoikeudenLisatiedot;
import fi.otavanopisto.pyramus.koski.model.lukio.LukionOpiskeluoikeus;
import fi.otavanopisto.pyramus.koski.model.lukio.LukionOppiaineenArviointi;
import fi.otavanopisto.pyramus.koski.settings.StudyEndReasonMapping;

public class KoskiLukioStudentHandler2019 extends KoskiStudentHandler {

  public static final String USERVARIABLE_UNDER18START = KoskiConsts.UserVariables.STARTED_UNDER18;
  public static final String USERVARIABLE_UNDER18STARTREASON = KoskiConsts.UserVariables.UNDER18_STARTREASON;
  private static final KoskiStudyProgrammeHandler HANDLER_TYPE = KoskiStudyProgrammeHandler.lukio;

  @Inject
  private Logger logger;

  @Inject
  private StudentStudyPeriodDAO studentStudyPeriodDAO;
  
  @Deprecated // common
  public Opiskeluoikeus studentToModel(Student student, String academyIdentifier, KoskiStudyProgrammeHandler handler) {
    if (handler != HANDLER_TYPE) {
      logger.log(Level.SEVERE, String.format("Wrong handler type %s, expected %s w/person %d.", handler, HANDLER_TYPE, student.getPerson().getId()));
      return null;
    }
    
    StudentSubjectSelections studentSubjects = loadStudentSubjectSelections(student, getDefaultSubjectSelections());
    String studyOid = userVariableDAO.findByUserAndKey(student, KOSKI_STUDYPERMISSION_ID);

    // Skip student if it is archived and the studyoid is blank
    if (Boolean.TRUE.equals(student.getArchived()) && StringUtils.isBlank(studyOid)) {
      return null;
    }
    
    OpiskelijanOPS ops = resolveOPS(student);
    if (ops == null) {
      koskiPersonLogDAO.create(student.getPerson(), student, KoskiPersonState.NO_CURRICULUM, new Date());
      return null;
    }
    
    if (student.getStudyStartDate() == null) {
      koskiPersonLogDAO.create(student.getPerson(), student, KoskiPersonState.NO_STUDYSTARTDATE, new Date());
      return null;
    }
    
    LukionOpiskeluoikeus opiskeluoikeus = new LukionOpiskeluoikeus();
    opiskeluoikeus.setLahdejarjestelmanId(getLahdeJarjestelmaID(handler, student.getId()));
    opiskeluoikeus.setAlkamispaiva(student.getStudyStartDate());
    opiskeluoikeus.setPaattymispaiva(student.getStudyEndDate());
    if (StringUtils.isNotBlank(studyOid)) {
      opiskeluoikeus.setOid(studyOid);
    }

    opiskeluoikeus.setLisatiedot(getLisatiedot(student));

    OpintojenRahoitus opintojenRahoitus = opintojenRahoitus(student);
    StudyEndReasonMapping lopetusSyy = opiskelujaksot(student, opiskeluoikeus.getTila(), opintojenRahoitus);
    boolean laskeKeskiarvot = lopetusSyy != null ? lopetusSyy.getLaskeAinekeskiarvot() : false;
    boolean sisällytäVahvistus = lopetusSyy != null ? lopetusSyy.getSisällytäVahvistaja() : false;

    String departmentIdentifier = settings.getToimipisteOID(student.getStudyProgramme().getId(), academyIdentifier);
    
    OrganisaationToimipiste toimipiste = new OrganisaationToimipisteOID(departmentIdentifier);
    EducationType studentEducationType = student.getStudyProgramme() != null && student.getStudyProgramme().getCategory() != null ? 
        student.getStudyProgramme().getCategory().getEducationType() : null;
    
    // NON-COMMON
    
    Set<LukionOsasuoritus2019> oppiaineet = assessmentsToModel(handler, ops, student, studentEducationType, studentSubjects, laskeKeskiarvot);

    LukionOppimaaranSuoritus2019 suoritus = new LukionOppimaaranSuoritus2019(
        LukionOppimaara.aikuistenops, Kieli.FI, toimipiste);
    suoritus.getKoulutusmoduuli().setPerusteenDiaarinumero(getDiaarinumero(student));
    suoritus.setTodistuksellaNakyvatLisatiedot(getTodistuksellaNakyvatLisatiedot(student));
    if (sisällytäVahvistus) {
      suoritus.setVahvistus(getVahvistus(student, departmentIdentifier));
    }
    opiskeluoikeus.addSuoritus(suoritus);
    
    oppiaineet.forEach(oppiaine -> suoritus.addOsasuoritus(oppiaine));
    
    return opiskeluoikeus;
  }
  
  private StudentSubjectSelections getDefaultSubjectSelections() {
    StudentSubjectSelections studentSubjects = new StudentSubjectSelections();
    studentSubjects.setMath("MAB");
    studentSubjects.setPrimaryLanguage("ÄI");
    studentSubjects.setReligion("UE");
    return studentSubjects;
  }

  @Deprecated // common
  private LukionOpiskeluoikeudenLisatiedot getLisatiedot(Student student) {
    List<StudentStudyPeriod> studyPeriods = studentStudyPeriodDAO.listByStudent(student);
    boolean pidennettyPaattymispaiva = studyPeriods.stream().anyMatch(studyPeriod -> studyPeriod.getPeriodType() == StudentStudyPeriodType.PROLONGED_STUDYENDDATE);
    boolean ulkomainenVaihtoopiskelija = false;
    boolean yksityisopiskelija = settings.isYksityisopiskelija(student.getStudyProgramme().getId());
    boolean oikeusMaksuttomaanAsuntolapaikkaan = settings.isFreeLodging(student.getStudyProgramme().getId());
    LukionOpiskeluoikeudenLisatiedot lisatiedot = new LukionOpiskeluoikeudenLisatiedot(
        pidennettyPaattymispaiva, ulkomainenVaihtoopiskelija, yksityisopiskelija, oikeusMaksuttomaanAsuntolapaikkaan);

    if (StringUtils.equals(userVariableDAO.findByUserAndKey(student, USERVARIABLE_UNDER18START), "1")) {
      String under18startReason = userVariableDAO.findByUserAndKey(student, USERVARIABLE_UNDER18STARTREASON);
      if (StringUtils.isNotBlank(under18startReason)) {
        lisatiedot.setAlle18vuotiaanAikuistenLukiokoulutuksenAloittamisenSyy(kuvaus(under18startReason));
      }
    }
    
    List<StudentLodgingPeriod> lodgingPeriods = lodgingPeriodDAO.listByStudent(student);
    for (StudentLodgingPeriod lodgingPeriod : lodgingPeriods) {
      Majoitusjakso jakso = new Majoitusjakso(lodgingPeriod.getBegin(), lodgingPeriod.getEnd());
      lisatiedot.addSisaoppilaitosmainenMajoitus(jakso);
    }
    
    return lisatiedot;
  }

  private Set<LukionOsasuoritus2019> assessmentsToModel(KoskiStudyProgrammeHandler handler, OpiskelijanOPS ops, Student student, EducationType studentEducationType, StudentSubjectSelections studentSubjects, boolean calculateMeanGrades) {
    Collection<CreditStub> credits = listCredits(student, true, true, ops, credit -> matchingCurriculumFilter(student, credit));
    Set<LukionOsasuoritus2019> results = new HashSet<>();
    
    Map<String, OppiaineenSuoritusWithSubject<LukionOsasuoritus2019>> map = new HashMap<>();
    Set<OppiaineenSuoritusWithSubject<LukionOsasuoritus2019>> accomplished = new HashSet<>();
    
    for (CreditStub credit : credits) {
      OppiaineenSuoritusWithSubject<LukionOsasuoritus2019> oppiaineenSuoritusWSubject = getSubject(student, studentEducationType, credit.getSubject(), studentSubjects, map);
      collectAccomplishedMarks(credit.getSubject(), oppiaineenSuoritusWSubject, studentSubjects, accomplished);
      
      if (settings.isReportedCredit(credit) && oppiaineenSuoritusWSubject != null) {
        LukionOpintojaksonSuoritus2019 kurssiSuoritus = createKurssiSuoritus(student, studentSubjects, ops, credit);
        if (kurssiSuoritus != null) {
          oppiaineenSuoritusWSubject.getOppiaineenSuoritus().addOsasuoritus(kurssiSuoritus);
        } else {
          logger.warning(String.format("Course %s not reported for student %d due to unresolvable credit.", credit.getCourseCode(), student.getId()));
          koskiPersonLogDAO.create(student.getPerson(), student, KoskiPersonState.UNREPORTED_CREDIT, new Date(), credit.getCourseCode());
        }
      }
    }
    
    for (OppiaineenSuoritusWithSubject<LukionOsasuoritus2019> lukionOppiaineenSuoritusWSubject : map.values()) {
      LukionOsasuoritus2019 lukionOsaSuoritus = lukionOppiaineenSuoritusWSubject.getOppiaineenSuoritus();
      if (CollectionUtils.isEmpty(lukionOsaSuoritus.getOsasuoritukset())) {
        // Skip empty subjects
        continue;
      }
      
      // Valmiille oppiaineelle on rustattava kokonaisarviointi
      if (calculateMeanGrades && (lukionOsaSuoritus instanceof LukionOppiaineenSuoritus2019)) {
        LukionOppiaineenSuoritus2019 lukionOppiaineenSuoritus = (LukionOppiaineenSuoritus2019) lukionOsaSuoritus;
        ArviointiasteikkoYleissivistava aineKeskiarvo = accomplished.contains(lukionOppiaineenSuoritusWSubject) ? 
            ArviointiasteikkoYleissivistava.GRADE_S : getSubjectMeanGrade(student, lukionOppiaineenSuoritusWSubject.getSubject(), lukionOsaSuoritus);
        
        if (aineKeskiarvo != null) {
          LukionOppiaineenArviointi arviointi = new LukionOppiaineenArviointi(aineKeskiarvo, student.getStudyEndDate());
          lukionOppiaineenSuoritus.addArviointi(arviointi);
        } else {
          logger.warning(String.format("Unresolved mean grade for person %d.", student.getPerson().getId()));
        }
      }
      
      results.add(lukionOsaSuoritus);
    }
    
    return results;
  }

  private ArviointiasteikkoYleissivistava getSubjectMeanGrade(Student student, Subject subject, LukionOsasuoritus2019 oppiaineenSuoritus) {
    // Jos aineesta on annettu korotettu arvosana, käytetään automaattisesti sitä
    ArviointiasteikkoYleissivistava korotettuArvosana = getSubjectGrade(student, subject);
    if (korotettuArvosana != null) {
      return korotettuArvosana;
    } else {
      List<ArviointiasteikkoYleissivistava> kurssiarvosanat = new ArrayList<>();
      for (LukionOpintojaksonSuoritus2019 kurssinSuoritus : oppiaineenSuoritus.getOsasuoritukset()) {
        List<KurssinArviointi> arvioinnit = kurssinSuoritus.getArviointi();
        Set<ArviointiasteikkoYleissivistava> arvosanat = arvioinnit.stream().map(arviointi -> arviointi.getArvosana().getValue()).collect(Collectors.toSet());
        
        kurssiarvosanat.add(ArviointiasteikkoYleissivistava.bestGrade(arvosanat));
      }
      
      return ArviointiasteikkoYleissivistava.meanGrade(kurssiarvosanat);
    }
  }

  private boolean isMathSubject(String subjectCode) {
    return StringUtils.equals(subjectCode, "MAA") || StringUtils.equals(subjectCode, "MAB") || StringUtils.equals(subjectCode, "MAY");
  }
  
  private OppiaineenSuoritusWithSubject<LukionOsasuoritus2019> getSubject(Student student, EducationType studentEducationType,
      Subject subject, StudentSubjectSelections studentSubjects, Map<String, OppiaineenSuoritusWithSubject<LukionOsasuoritus2019>> map) {
    String subjectCode = subjectCode(subject);

    if (map.containsKey(subjectCode))
      return map.get(subjectCode);
    
    boolean matchingEducationType = studentEducationType != null && subject.getEducationType() != null && 
        studentEducationType.getId().equals(subject.getEducationType().getId());
    
    // Aineet, joista lukiodiplomit voi suorittaa
    List<String> lukioDiplomit = Arrays.asList(new String[] { "KOLD", "KULD", "KÄLD", "LILD", "MELD", "MULD", "TALD", "TELD" });

    if (lukioDiplomit.contains(subjectCode)) {
      if (map.containsKey("LD")) {
        return map.get("LD");
      }

      LukionMuidenOpintojenTunniste2019 tunniste = new LukionMuidenOpintojenTunniste2019(LukionMuutOpinnot.LD);
      LukionOsasuoritus2019 mo = new MuidenLukioOpintojenSuoritus2019(tunniste);
      OppiaineenSuoritusWithSubject<LukionOsasuoritus2019> os = new OppiaineenSuoritusWithSubject<>(subject, mo);
      map.put("LD", os);
      return os;
    }
    
    // MATHEMATICS
    if (matchingEducationType && isMathSubject(subjectCode)) {
      if (StringUtils.equals(subjectCode, "MAY") && isMathSubject(studentSubjects.getMath())) {
        // MAY is mapped to either MAB/MAA unless neither is specified
        subjectCode = studentSubjects.getMath();
        if (map.containsKey(subjectCode)) {
          return map.get(subjectCode);
        }
      }
      
      if (StringUtils.equals(subjectCode, studentSubjects.getMath())) {
        LukionOppiaineenTunniste2019 tunniste = new LukionOppiaineenSuoritusMatematiikka2019(
            OppiaineMatematiikka.valueOf(subjectCode), isPakollinenOppiaine(student, KoskiOppiaineetYleissivistava.MA));
        return mapSubject(subject, subjectCode, tunniste, map);
      } else
        return null;
    }
    
    if (matchingEducationType && StringUtils.equals(subjectCode, "ÄI")) {
      if (StringUtils.equals(subjectCode, studentSubjects.getPrimaryLanguage())) {
        LukionOppiaineenTunniste2019 tunniste = new LukionOppiaineenSuoritusAidinkieli2019(
            OppiaineAidinkieliJaKirjallisuus.AI1, isPakollinenOppiaine(student, KoskiOppiaineetYleissivistava.AI));
        return mapSubject(subject, subjectCode, tunniste, map);
      } else
        return null;
    }
    if (matchingEducationType && StringUtils.equals(subjectCode, "S2")) {
      if (StringUtils.equals(subjectCode, studentSubjects.getPrimaryLanguage())) {
        LukionOppiaineenTunniste2019 tunniste = new LukionOppiaineenSuoritusAidinkieli2019(
            OppiaineAidinkieliJaKirjallisuus.AI7, isPakollinenOppiaine(student, KoskiOppiaineetYleissivistava.AI));
        return mapSubject(subject, subjectCode, tunniste, map);
      } else
        return null;
    }
    
    if (matchingEducationType && studentSubjects.isAdditionalLanguage(subjectCode)) {
      if (subjectCode.length() > 2) {
        String langCode = settings.getSubjectToLanguageMapping(subjectCode.substring(0, 2).toUpperCase());
        Kielivalikoima kieli = Kielivalikoima.valueOf(langCode);
        
        if (kieli != null) {
          KoskiOppiaineetYleissivistava valinta = studentSubjects.koskiKoodi(subjectCode);          
          LukionOppiaineenTunniste2019 tunniste = new LukionOppiaineenSuoritusVierasKieli2019(valinta, kieli, 
              isPakollinenOppiaine(student, valinta));
          return mapSubject(subject, subjectCode, tunniste, map);
        } else {
          logger.log(Level.SEVERE, String.format("Koski: Language code %s could not be converted to an enum.", langCode));
          koskiPersonLogDAO.create(student.getPerson(), student, KoskiPersonState.UNKNOWN_LANGUAGE, new Date(), langCode);
          return null;
        }
      }
    }

    String[] religionSubjects = new String[] { "UE", "UO" };
    
    if (matchingEducationType && ArrayUtils.contains(religionSubjects, subjectCode)) {
      // Only the religion that student has selected is reported
      if (StringUtils.equals(subjectCode, studentSubjects.getReligion())) {
        if (map.containsKey("KT"))
          return map.get("KT");
        
        KoskiOppiaineetYleissivistava kansallinenAine = KoskiOppiaineetYleissivistava.KT;
        LukionOppiaineenTunniste2019 tunniste = new LukionOppiaineenSuoritusMuuValtakunnallinen2019(kansallinenAine, isPakollinenOppiaine(student, kansallinenAine));
        return mapSubject(subject, "KT", tunniste, map);
      } else
        return null;
    }
    
    if (matchingEducationType && EnumUtils.isValidEnum(KoskiOppiaineetYleissivistava.class, StringUtils.upperCase(subjectCode))) {
      // Common national subject

      // TODO: kaikkia aineita ei tunneta 2019
      KoskiOppiaineetYleissivistava kansallinenAine = KoskiOppiaineetYleissivistava.valueOf(StringUtils.upperCase(subjectCode));
      LukionOppiaineenTunniste2019 tunniste = new LukionOppiaineenSuoritusMuuValtakunnallinen2019(kansallinenAine, isPakollinenOppiaine(student, kansallinenAine));
      return mapSubject(subject, subjectCode, tunniste, map);
    } else {
      // Other local subject
      
      PaikallinenKoodi paikallinenKoodi = new PaikallinenKoodi(subjectCode, kuvaus(subject.getName()));
      LukionOppiaineenSuoritusPaikallinen2019 tunniste = new LukionOppiaineenSuoritusPaikallinen2019(paikallinenKoodi, false, kuvaus(subject.getName()));
      return mapSubject(subject, subjectCode, tunniste, map);
    }
  }

  private OppiaineenSuoritusWithSubject<LukionOsasuoritus2019> mapSubject(Subject subject, String subjectCode, LukionOppiaineenTunniste2019 tunniste, Map<String, OppiaineenSuoritusWithSubject<LukionOsasuoritus2019>> map) {
    // TODO
    boolean suoritettuErityisenäTutkintona = false;
    LukionOppiaineenSuoritus2019 lukionOppiaineenSuoritus = new LukionOppiaineenSuoritus2019(tunniste, suoritettuErityisenäTutkintona);
    OppiaineenSuoritusWithSubject<LukionOsasuoritus2019> os = new OppiaineenSuoritusWithSubject<>(subject, lukionOppiaineenSuoritus);
    map.put(subjectCode, os);
    return os;
  }
  
  protected LukionOpintojaksonSuoritus2019 createKurssiSuoritus(Student student, StudentSubjectSelections studentSubjects, OpiskelijanOPS ops, CreditStub courseCredit) {
    String kurssiKoodi = courseCredit.getCourseCode();
    LukionOpintojaksonTunniste2019 tunniste;

    String subjectCode = subjectCode(courseCredit.getSubject());

    Laajuus laajuus = kurssinLaajuus(student, courseCredit);
    SuorituksenTyyppi suorituksenTyyppi;

    if (EnumUtils.isValidEnum(ModuuliKoodistoLOPS2021.class, kurssiKoodi)) {
      /**
       * Tässä yhteydessä:
       * * pakollinen => valtakunnallinen pakollinen
       * * syventava => valtakunnallinen valinnainen
       */
      LukionKurssinTyyppi kurssinTyyppi = findCourseType(student, courseCredit, true, LukionKurssinTyyppi.pakollinen, LukionKurssinTyyppi.syventava);
      boolean pakollinen = kurssinTyyppi == LukionKurssinTyyppi.pakollinen;

      suorituksenTyyppi = SuorituksenTyyppi.lukionvaltakunnallinenmoduuli;
      ModuuliKoodistoLOPS2021 kurssi = ModuuliKoodistoLOPS2021.valueOf(kurssiKoodi);

      if (studentSubjects.isAdditionalLanguage(subjectCode)) {
        tunniste = new LukionOpintojaksonTunnisteVierasKieli2019(kurssi, laajuus, pakollinen);
      } else {
        tunniste = new LukionOpintojaksonTunnisteMuuModuuli2019(kurssi, laajuus, pakollinen);
      }
    } else {
      /**
       * Tässä yhteydessä:
       * * syventava => paikallinen pakollinen
       * * soveltava => paikallinen valinnainen
       */
      LukionKurssinTyyppi kurssinTyyppi = findCourseType(student, courseCredit, false, LukionKurssinTyyppi.syventava, LukionKurssinTyyppi.soveltava);
      boolean pakollinen = kurssinTyyppi == LukionKurssinTyyppi.pakollinen;

      suorituksenTyyppi = SuorituksenTyyppi.lukionpaikallinenopintojakso;
      PaikallinenKoodi paikallinenKoodi = new PaikallinenKoodi(kurssiKoodi, kuvaus(courseCredit.getSubject().getName()));
      tunniste = new LukionOpintojaksonTunnistePaikallinen2019(paikallinenKoodi, laajuus, pakollinen, kuvaus(courseCredit.getCourseName()));
    }
      
    LukionOpintojaksonSuoritus2019 suoritus = new LukionOpintojaksonSuoritus2019(tunniste, suorituksenTyyppi);

    return luoKurssiSuoritus(suoritus, courseCredit);
  }

  private LukionKurssinTyyppi findCourseType(Student student, CreditStub courseCredit, boolean national, LukionKurssinTyyppi ... allowedValues) {
    Set<LukionKurssinTyyppi> resolvedTypes = new HashSet<>();
    
    for (CreditStubCredit credit : courseCredit.getCredits()) {
      if (credit.getCredit() instanceof CourseAssessment) {
        CourseAssessment courseAssessment = (CourseAssessment) credit.getCredit();
        if (courseAssessment.getCourseStudent() != null && courseAssessment.getCourseStudent().getCourse() != null) {
          Course course = courseAssessment.getCourseStudent().getCourse();
          Set<Long> educationSubTypeIds = course.getCourseEducationTypes().stream().flatMap(
              educationType -> educationType.getCourseEducationSubtypes().stream().map(subType -> subType.getEducationSubtype().getId())).collect(Collectors.toSet());
          for (Long educationSubTypeId : educationSubTypeIds) {
            String mappedValue = settings.getCourseTypeMapping2019(educationSubTypeId);
            if (mappedValue != null && EnumUtils.isValidEnum(LukionKurssinTyyppi.class, mappedValue)) {
              resolvedTypes.add(LukionKurssinTyyppi.valueOf(mappedValue));
            }
          }
        } else {
          logger.warning(String.format("CourseAssessment %d has no courseStudent or Course", courseAssessment.getId()));
        }
      } else if (credit.getCredit() instanceof TransferCredit) {
        TransferCredit transferCredit = (TransferCredit) credit.getCredit();
        if (national && transferCredit.getOptionality() == CourseOptionality.MANDATORY) {
          resolvedTypes.add(LukionKurssinTyyppi.pakollinen);
        } else {
          // TODO Is this correct?
          resolvedTypes.add(LukionKurssinTyyppi.syventava);
        }
      } else {
        logger.warning(String.format("Unknown credit type %s", credit.getClass().getSimpleName()));
      }
    }
    
    Set<LukionKurssinTyyppi> allowedSet = new HashSet<>(Arrays.asList(allowedValues));
    allowedSet.removeIf(element -> !resolvedTypes.contains(element));
    
    if (allowedSet.size() == 0) {
      logger.warning(String.format("Course %s has no feasible subtypes.", courseCredit.getCourseCode()));
      koskiPersonLogDAO.create(student.getPerson(), student, KoskiPersonState.UNRESOLVABLE_SUBTYPES, new Date(), courseCredit.getCourseCode());
      return allowedValues[0];
    } else if (allowedSet.size() == 1) {
      return allowedSet.iterator().next();
    } else {
      for (LukionKurssinTyyppi type : allowedValues) {
        if (allowedSet.contains(type)) {
          logger.warning(String.format("Course %s has several matching subtypes.", courseCredit.getCourseCode()));
          koskiPersonLogDAO.create(student.getPerson(), student, KoskiPersonState.UNRESOLVABLE_SUBTYPES, new Date(), courseCredit.getCourseCode());
          return type;
        }
      }
    }
    
    return allowedValues[0];
  }

  @Override
  public void saveOrValidateOid(KoskiStudyProgrammeHandler handler, Student student, String oid) {
    if (handler == HANDLER_TYPE) {
      saveOrValidateOid(student, oid);
    } else {
      logger.severe(String.format("saveOrValidateOid called with wrong handler %s, expected %s ", handler, HANDLER_TYPE));
    }
  }

  @Override
  public void removeOid(KoskiStudyProgrammeHandler handler, Student student, String oid) {
    if (handler == HANDLER_TYPE) {
      removeOid(student, oid);
    } else {
      logger.severe(String.format("removeOid called with wrong handler %s, expected %s ", handler, HANDLER_TYPE));
    }
  }
  
  @Override
  public Set<KoskiStudentId> listOids(Student student) {
    String oid = userVariableDAO.findByUserAndKey(student, KoskiConsts.VariableNames.KOSKI_STUDYPERMISSION_ID);
    if (StringUtils.isNotBlank(oid)) {
      return new HashSet<>(Arrays.asList(new KoskiStudentId(getStudentIdentifier(HANDLER_TYPE, student.getId()), oid)));
    } else {
      return new HashSet<>();
    }
  }
  
}
