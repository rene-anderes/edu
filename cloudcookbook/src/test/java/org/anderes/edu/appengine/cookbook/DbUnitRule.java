package org.anderes.edu.appengine.cookbook;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class DbUnitRule implements TestRule {
    
    @Retention(RUNTIME)
    @Target({METHOD, TYPE})
    public static @interface UsingDataSet {
        String[] value();
    }

    @Retention(RUNTIME)
    @Target({METHOD})
    public static @interface ShouldMatchDataSet {
        String[] value();
        String[] excludeColumns() default { };
        String[] orderBy() default { }; 
    }
    
    @Retention(RUNTIME)
    @Target({METHOD, TYPE})
    public static @interface CleanupUsingScript {
        String[] value();
    }
    
    @Retention(RUNTIME)
    @Target({METHOD, TYPE})
    public @interface UsingDataSetScript {
        String[] value();
    }

    
    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before(description);
                base.evaluate();
                after(description);
            }
        };
    }
    
    private void before(final Description description) throws Exception {
        final UsingDataSet usingDataSet = extractUsingDataSet(description);
        final CleanupUsingScript cleanupUsingScript = extractCleanupUsingScript(description);
        final UsingDataSetScript usingDataSetScript = extractUsingDataSetScript(description);
        if (cleanupUsingScript != null) {
            processCleanupScripts(cleanupUsingScript);
        }
        if (usingDataSet != null) {
//            processUsingDataSet(usingDataSet, null);
        } else if (usingDataSetScript != null) {
            processUsingDataSetScript(usingDataSetScript);
        }
    }

    private UsingDataSetScript extractUsingDataSetScript(final Description description) {
        UsingDataSetScript usingDataSetScript = description.getAnnotation(UsingDataSetScript.class);
        if (usingDataSetScript == null) {
            usingDataSetScript = description.getTestClass().getAnnotation(UsingDataSetScript.class);
        }
        return usingDataSetScript;
    }

    private CleanupUsingScript extractCleanupUsingScript(final Description description) {
        CleanupUsingScript cleanupUsingScript = description.getAnnotation(CleanupUsingScript.class);
        if (cleanupUsingScript == null) {
            cleanupUsingScript = description.getTestClass().getAnnotation(CleanupUsingScript.class);
        }
        return cleanupUsingScript;
    }

    private UsingDataSet extractUsingDataSet(final Description description) {
        UsingDataSet usingDataSet = description.getAnnotation(UsingDataSet.class);
        if (usingDataSet == null) {
            usingDataSet = description.getTestClass().getAnnotation(UsingDataSet.class);
        }
        return usingDataSet;
    }

    
    private void processCleanupScripts(final CleanupUsingScript cleanupUsingScript) throws Exception {
        final String[] cleanupFiles = cleanupUsingScript.value();
        
    }


    private void processUsingDataSetScript(final UsingDataSetScript usingDataSetScript) throws Exception {
        final String[] usingDataSetScriptFiles = usingDataSetScript.value();
        
    }

    
    private void after(final Description description) throws Exception {
        final ShouldMatchDataSet annotation = description.getAnnotation(ShouldMatchDataSet.class);
        if (annotation == null) {
            return;
        }
        
    }

}
