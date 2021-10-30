package nl.martijndwars.markdown;

import com.itextpdf.text.DocumentException;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CompileHtmlToPdfTask extends DefaultTask {
    protected final RegularFileProperty inputFile;
    protected final RegularFileProperty outputFile;

    public CompileHtmlToPdfTask() {
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
    public void run() throws IOException, DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(inputFile.getAsFile().get());
        renderer.layout();

        try (OutputStream outputStream = new FileOutputStream(outputFile.get().getAsFile())) {
            renderer.createPDF(outputStream);
        }
    }
}
