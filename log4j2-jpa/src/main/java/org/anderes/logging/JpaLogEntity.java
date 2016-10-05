package org.anderes.logging;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.db.jpa.BasicLogEventEntity;
import org.apache.logging.log4j.core.appender.db.jpa.converter.ContextMapJsonAttributeConverter;
import org.apache.logging.log4j.core.appender.db.jpa.converter.ContextStackJsonAttributeConverter;

/**
 * JPA Entität zum Schreiben der Log-Daten mittels Appender
 * 
 * @author René Anderes
 *
 */
@Entity
@Table(name = "APPLICATION_LOG")
public class JpaLogEntity extends BasicLogEventEntity {

    private static final long serialVersionUID = 1L;
    private long id;

    public JpaLogEntity() {
        super();
    }

    public JpaLogEntity(LogEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    @Convert(converter = ContextMapJsonAttributeConverter.class)
    public Map<String, String> getContextMap() {
        return this.getWrappedEvent().getContextMap();
    }

    @Override
    @Convert(converter = ContextStackJsonAttributeConverter.class)
    public ThreadContext.ContextStack getContextStack() {
        return this.getWrappedEvent().getContextStack();
    }
}
