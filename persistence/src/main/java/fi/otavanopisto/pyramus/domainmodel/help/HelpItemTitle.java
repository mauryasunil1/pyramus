package fi.otavanopisto.pyramus.domainmodel.help;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

import fi.otavanopisto.pyramus.domainmodel.users.User;

@Entity
@Indexed
public class HelpItemTitle {

  public Long getId() {
    return id;
  }
  
  public Locale getLocale() {
    return locale;
  }
  
  public void setLocale(Locale locale) {
    this.locale = locale;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public HelpItem getItem() {
    return item;
  }
  
  protected void setItem(HelpItem item) {
    this.item = item;
  }
  
  public Date getCreated() {
    return created;
  }
  
  public void setCreated(Date created) {
    this.created = created;
  }
  
  public User getCreator() {
    return creator;
  }
  
  public void setCreator(User creator) {
    this.creator = creator;
  }
  
  public Date getLastModified() {
    return lastModified;
  }
  
  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }
  
  public User getLastModifier() {
    return lastModifier;
  }
  
  public void setLastModifier(User lastModifier) {
    this.lastModifier = lastModifier;
  }

  @Id
  @GeneratedValue(strategy=GenerationType.TABLE, generator="HelpItemTitle")  
  @TableGenerator(name="HelpItemTitle", allocationSize=1, table = "hibernate_sequences", pkColumnName = "sequence_name", valueColumnName = "sequence_next_hi_value")
  @DocumentId
  private Long id;
  
  @ManyToOne 
  @JoinColumn (name="item")
  private HelpItem item;
  
  @Column (nullable=false)
  @NotNull
  private Locale locale;
  
  @Column (nullable=false)
  @NotNull
  @NotEmpty  
  @Field
  private String title;
  
  @Column (nullable=false)
  @NotNull
  @Temporal (TemporalType.TIMESTAMP)
  @Field (analyze = Analyze.NO, store=Store.YES)
  @DateBridge (resolution=Resolution.MILLISECOND)
  private Date lastModified;
  
  @Column (nullable=false)
  @Temporal (TemporalType.TIMESTAMP)
  @org.hibernate.search.annotations.Field (analyze = Analyze.NO, store=Store.YES)
  @DateBridge (resolution=Resolution.MILLISECOND)
  private Date created;

  @ManyToOne
  @JoinColumn (name="creator")
  private User creator;
  
  @ManyToOne
  @JoinColumn (name="lastModifier")
  private User lastModifier;
}
