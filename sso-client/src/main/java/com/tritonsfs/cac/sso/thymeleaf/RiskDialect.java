package com.tritonsfs.cac.sso.thymeleaf;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Time 2018/5/7
 * @Author zlian
 */
public class RiskDialect extends AbstractProcessorDialect {

    private static final String NAME = "Risk";
    private static final String PREFIX = "risk";

    public RiskDialect() {
        super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        return createStandardProcessorsSet(dialectPrefix);
    }

    private Set<IProcessor> createStandardProcessorsSet(String dialectPrefix) {
        LinkedHashSet<IProcessor> processors = new LinkedHashSet<IProcessor>();
        processors.add(new SansitiveEncryptProcessor(dialectPrefix));
        return processors;
    }
}
