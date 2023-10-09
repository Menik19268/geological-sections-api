package com.testing.geologicalsectionsapi.bootstrap;


import com.testing.geologicalsectionsapi.models.GeologicalClass;
import com.testing.geologicalsectionsapi.models.Section;
import com.testing.geologicalsectionsapi.repositories.GeologicalClassRepository;
import com.testing.geologicalsectionsapi.repositories.SectionRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Arrays;

//@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final SectionRepository sectionRepository;
    private final GeologicalClassRepository geologicalClassRepository;


    public DevBootstrap(SectionRepository sectionRepository, GeologicalClassRepository geologicalClassRepository) {
        this.sectionRepository = sectionRepository;
        this.geologicalClassRepository = geologicalClassRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    private void initData() {
        // Create and save Section objects
        Section section1 = new Section();
        section1.setName("Section 1");

        Section section2 = new Section();
        section2.setName("Section 2");

        Section section3 = new Section();
        section3.setName("Section 3");

        Section sectionN = new Section();
        sectionN.setName("Section N");

        // Create and save GeologicalClass objects
        GeologicalClass geoClass11 = new GeologicalClass("Geo Class 11", "GC11");
        GeologicalClass geoClass12 = new GeologicalClass("Geo Class 12", "GC12");
        GeologicalClass geoClass1M = new GeologicalClass("Geo Class 1M", "GC1M");

        GeologicalClass geoClass21 = new GeologicalClass("Geo Class 21", "GC21");
        GeologicalClass geoClass22 = new GeologicalClass("Geo Class 22", "GC22");

        GeologicalClass geoClass31 = new GeologicalClass("Geo Class 31", "GC31");
        GeologicalClass geoClass3M = new GeologicalClass("Geo Class 3M", "GC3M");

        GeologicalClass geoClassN1 = new GeologicalClass("Geo Class N1", "GCN1");
        GeologicalClass geoClassN2 = new GeologicalClass("Geo Class N2", "GCN2");
        GeologicalClass geoClassNM = new GeologicalClass("Geo Class NM", "GCNM");

        // Set the Section reference for each GeologicalClass
        geoClass11.setSection(section1);
        geoClass12.setSection(section1);
        geoClass1M.setSection(section1);

        geoClass21.setSection(section2);
        geoClass22.setSection(section2);

        geoClass31.setSection(section3);
        geoClass3M.setSection(section3);

        geoClassN1.setSection(sectionN);
        geoClassN2.setSection(sectionN);
        geoClassNM.setSection(sectionN);

        // Set the GeologicalClass list for the Section
        section1.setGeologicalClasses(Arrays.asList(geoClass11, geoClass12, geoClass1M));
        section2.setGeologicalClasses(Arrays.asList(geoClass21, geoClass22));
        section3.setGeologicalClasses(Arrays.asList(geoClass31, geoClass3M));
        sectionN.setGeologicalClasses(Arrays.asList(geoClassN1, geoClassN2, geoClassNM));

        // Save the Section and GeologicalClass objects
        sectionRepository.save(section1);
        sectionRepository.save(section2);
        sectionRepository.save(section3);
        sectionRepository.save(sectionN);

        // Save the GeologicalClass objects
        geologicalClassRepository.save(geoClass11);
        geologicalClassRepository.save(geoClass12);
        geologicalClassRepository.save(geoClass1M);

        geologicalClassRepository.save(geoClass21);
        geologicalClassRepository.save(geoClass22);

        geologicalClassRepository.save(geoClass31);
        geologicalClassRepository.save(geoClass3M);

        geologicalClassRepository.save(geoClassN1);
        geologicalClassRepository.save(geoClassN2);
        geologicalClassRepository.save(geoClassNM);
    }
}




