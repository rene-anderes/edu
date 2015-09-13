package org.anderes.edu.appengine.cookbook;

import java.io.Closeable;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;

public class ObjectifyTestRule implements TestRule {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    
    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                helper.setUp();
                try (Closeable closeable = ObjectifyService.begin()) {
                    base.evaluate();
                } finally {
                    helper.tearDown();
                }
            }
        };
    }

}
