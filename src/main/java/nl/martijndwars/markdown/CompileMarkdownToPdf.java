package nl.martijndwars.markdown;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class CompileMarkdownToPdf extends DefaultTask {
    protected final RegularFileProperty inputFile;
    protected final RegularFileProperty outputFile;

    public CompileMarkdownToPdf() throws IOException {
        Project project = getProject();
        inputFile = project.getObjects().fileProperty();
        outputFile = project.getObjects().fileProperty();

        TaskContainer tasks = project.getTasks();
        CompileMarkdownToHtmlTask compileMarkdownToHtml = (CompileMarkdownToHtmlTask) tasks.getByName("compileMarkdownToHtml");
        compileMarkdownToHtml.inputFile.set(inputFile);
        compileMarkdownToHtml.outputFile.set(getTemporaryFile());

        CompileHtmlToPdfTask compileHtmlToPdfTask = (CompileHtmlToPdfTask) tasks.getByName("compileHtmlToPdf");
        compileHtmlToPdfTask.inputFile.set(compileMarkdownToHtml.outputFile);
        compileHtmlToPdfTask.outputFile.set(outputFile);

        dependsOn(compileHtmlToPdfTask);
    }

    @Internal
    protected File getTemporaryFile() throws IOException {
        Path temporaryDir = getTemporaryDir().toPath();
        Path temporaryFile = temporaryDir.resolve("temporaryFile.html");
        File file = temporaryFile.toFile();
        file.createNewFile();

        return file;
    }

    @InputFile
    public RegularFileProperty getInputFile() {
        return inputFile;
    }

    @OutputFile
    public RegularFileProperty getOutputFile() {
        return outputFile;
    }
}
