package nl.martijndwars.markdown;

import org.gradle.api.NonNullApi;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.PluginManager;

@NonNullApi
public class MarkdownPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        PluginManager pluginManager = project.getPluginManager();
        pluginManager.apply(BasePlugin.class);

        project.getTasks().register("compileMarkdownToHtml", CompileMarkdownToHtmlTask.class);
        project.getTasks().register("compileHtmlToPdf", CompileHtmlToPdfTask.class);
    }
}
