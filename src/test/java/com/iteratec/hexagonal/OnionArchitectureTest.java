package com.iteratec.hexagonal;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "com.iteratec.hexagonal")
public class OnionArchitectureTest {

    @ArchTest
    static final ArchRule onion_architecture_is_respected = onionArchitecture()
            .domainModels("..domain..")
            .applicationServices("..application..")
            .adapter("rest", "..infrastructure.in.web..")
            .adapter("persistence", "..infrastructure.out.persistence..")
            .adapter("tshirt-printer", "..infrastructure.out.tshirt.printer..")
            .withOptionalLayers(true);
}
