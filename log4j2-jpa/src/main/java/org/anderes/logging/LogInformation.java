package org.anderes.logging;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.logging.log4j.ThreadContext.ContextStack;
import org.apache.logging.log4j.core.appender.db.jpa.converter.ContextMapJsonAttributeConverter;
import org.apache.logging.log4j.core.appender.db.jpa.converter.ContextStackJsonAttributeConverter;

/**
 * JPA Entität um Log-Informationen mittels JPA auszulesen
 * 
 * @author René Anderes
 *
 */
@Entity
@Table(name = "APPLICATION_LOG")
@NamedQueries({
                @NamedQuery(name = "LogInformation.All", query = "Select l from LogInformation l"),
})
public class LogInformation {

    @Id
    private Long id;

    @Convert(converter = ContextMapJsonAttributeConverter.class)
    private Map<String, String> contextMap = Collections.emptyMap();

    @Convert(converter = ContextStackJsonAttributeConverter.class)
    private ContextStack contextStack;

    private String level;

    private String message;

    private String source;

    private String threadname;

    private String thrown;

    private Long timeMillis = Long.MIN_VALUE;

    public Long getId() {
        return id;
    }

    public Map<String, String> getContextMap() {
        return Collections.unmodifiableMap(contextMap);
    }

    public Optional<ContextStack> getContextStack() {
        return Optional.ofNullable(contextStack);
    }

    public Optional<String> getLevel() {
        return Optional.ofNullable(level);
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }

    public Optional<String> getSource() {
        return Optional.ofNullable(source);
    }

    public Optional<String> getThreadname() {
        return Optional.ofNullable(threadname);
    }

    public Optional<String> getThrown() {
        return Optional.ofNullable(thrown);
    }

    public Optional<LocalDateTime> getLogDate() {
        if (timeMillis == Long.MIN_VALUE) {
            return Optional.empty();
        }
        return Optional.of(LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMillis), ZoneId.systemDefault()));
    }
}