package com.sirket.qa.gaugeapi;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.ExecutionContext;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;

import java.util.List;
import java.util.UUID;

public class AllureRaporu {

    private static String uuid;

    @BeforeScenario
    public void senaryoBaslat(ExecutionContext ctx) {
        String specAdi = ctx.getCurrentSpecification().getName();
        uuid = UUID.randomUUID().toString();

        TestResult sonuc = new TestResult()
                .setUuid(uuid)
                .setName(ctx.getCurrentScenario().getName())
                .setLabels(List.of(
                        new Label().setName("suite").setValue(specAdi),
                        new Label().setName("feature").setValue(specAdi)));

        Allure.getLifecycle().scheduleTestCase(sonuc);
        Allure.getLifecycle().startTestCase(uuid);
    }

    @AfterScenario
    public void senaryoBitir(ExecutionContext ctx) {
        boolean basarisiz = ctx.getCurrentScenario().getIsFailing();

        Allure.getLifecycle().updateTestCase(uuid,
                r -> r.setStatus(basarisiz ? Status.FAILED : Status.PASSED));
        Allure.getLifecycle().stopTestCase(uuid);
        Allure.getLifecycle().writeTestCase(uuid);
    }
}
