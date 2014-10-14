package org.anderes.edu.beanvalidation.groups;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({ Minimal.class, Default.class })
public interface OrderedChecks {

}
