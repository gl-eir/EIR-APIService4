package com.ceir.CeirCode.util;

import com.ceir.CeirCode.configuration.PropertiesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Predicate;

@Component
public class HelperUtility {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private PropertiesReader propertiesReader;

    public <T> boolean isValid(T t) {
        Predicate<T> predicateForString = (x) -> Objects.nonNull(x) && x != "";
        return predicateForString.test(t);
    }
/*
    public <T> GenericSpecificationBuilder<T> buildSpecification(Map<?, ?> map) throws ParseException {
        GenericSpecificationBuilder<T> cmsb = new GenericSpecificationBuilder<>(propertiesReader.dialect);
        SearchOperation so = null;
        Datatype dt = null;
        for (Map.Entry<?, ?> m : map.entrySet()) {

            if (this.isValid(m.getValue())) {
                cmsb.with(new SearchCriteria(m.getKey().toString(), m.getValue(), so, dt));
            }
        }
        logger.info("cmsb [" + cmsb.toString() + "]");
        return cmsb;
    }
*/

}
