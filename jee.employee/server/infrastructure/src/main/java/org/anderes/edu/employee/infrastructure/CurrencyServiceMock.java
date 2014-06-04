/*
 * Copyright (c) 2013 VRSG | Verwaltungsrechenzentrum AG, St.Gallen
 * All Rights Reserved.
 */

package org.anderes.edu.employee.infrastructure;

import javax.enterprise.inject.Alternative;

/**
 * Hier Dummy-Implementation
 */
@Alternative
public class CurrencyServiceMock implements CurrencyService {

    public String getCurrency() {
        return "CHF";
    }
}
