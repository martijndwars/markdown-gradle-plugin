package nl.martijndwars.markdown;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkdownPluginFunctionalTest {
    public static String BASE_DIR = "src/test/resources/examples";

    @Test
    void testSimpleProject() {
        File projectDir = new File(BASE_DIR + "/simple");

        TaskOutcome taskOutcome = runGradleTask(projectDir, ":compileHtmlToPdf");

        assertEquals(SUCCESS, taskOutcome);
    }

    @Test
    void testCombinedProject() {
        File projectDir = new File(BASE_DIR + "/combined");

        TaskOutcome taskOutcome = runGradleTask(projectDir, ":compileMarkdownToPdf");

        assertEquals(SUCCESS, taskOutcome);
    }

    protected TaskOutcome runGradleTask(File projectDir, String task) {
        BuildResult buildResult = runGradle(projectDir, "clean", task);

        return buildResult.task(task).getOutcome();
    }

    protected BuildResult runGradle(File projectDir, String... args) {
        return GradleRunner.create()
                .withPluginClasspath()
                .withProjectDir(projectDir)
                .withArguments(args)
                .build();
    }
}
