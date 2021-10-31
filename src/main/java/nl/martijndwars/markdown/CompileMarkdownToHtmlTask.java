package nl.martijndwars.markdown;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CompileMarkdownToHtmlTask extends DefaultTask {
    protected final RegularFileProperty inputFile;
    protected final RegularFileProperty outputFile;

    public CompileMarkdownToHtmlTask() {
        inputFile = getProject().getObjects().fileProperty();
        outputFile = getProject().getObjects().fileProperty();
    }

    @InputFile
    public RegularFileProperty getInputFile() {
        return inputFile;
    }

    @OutputFile
    public RegularFileProperty getOutputFile() {
        return outputFile;
    }

    @TaskAction
    public void run() throws IOException {
        String input = getInputString();
        String output = markdownToHtml(input);
        writeOutput(output);
    }

    @Internal
    protected String getInputString() throws IOException {
        Path inputPath = inputFile.get().getAsFile().toPath();

        return new String(Files.readAllBytes(inputPath));
    }

    protected String markdownToHtml(String input) {
        Node document = getOrCreateParser().parse(input);
        HtmlRenderer renderer = getOrCreateHtmlRenderer();
        String html = renderer.render(document);

        return Jsoup.parse(html).outerHtml();
    }

    protected void writeOutput(String output) throws IOException {
        Path outputPath = outputFile.get().getAsFile().toPath();
        Files.write(outputPath, output.getBytes());
    }

    @Internal
    protected Parser getOrCreateParser() {
        return Parser.builder()
                .extensions(getCommonMarkExtensions())
                .build();
    }

    @Internal
    protected HtmlRenderer getOrCreateHtmlRenderer() {
        return HtmlRenderer.builder()
                .extensions(getCommonMarkExtensions())
                .build();
    }

    @Internal
    protected List<Extension> getCommonMarkExtensions() {
        return Arrays.asList(TablesExtension.create());
    }
}
