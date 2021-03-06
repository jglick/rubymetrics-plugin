package hudson.plugins.rubyMetrics.flog;

import hudson.model.AbstractProject;
import hudson.model.Job;
import hudson.plugins.rubyMetrics.AbstractRubyMetricsProjectAction;

public class FlogProjectAction<FlogBuildAction> extends AbstractRubyMetricsProjectAction {

    public FlogProjectAction(Job<?, ?> job) {
        super(job);
    }

    @Deprecated
    public FlogProjectAction(AbstractProject<?, ?> project) {
        super(project);
    }

    @Override
    protected Class getBuildActionClass() {
        return hudson.plugins.rubyMetrics.flog.FlogBuildAction.class;
    }

    public String getDisplayName() {
        return "Flog report";
    }

    public String getUrlName() {
        return "flog";
    }

}
